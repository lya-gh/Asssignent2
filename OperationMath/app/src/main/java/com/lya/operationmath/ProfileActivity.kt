package com.lya.operationmath

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileActivity : AppCompatActivity() {

    private lateinit var logoutButton:Button
    private lateinit var updateButton:Button
    private lateinit var emailText: TextView
    private lateinit var displayNameText: EditText

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        logoutButton = findViewById(R.id.logoutButton)
        updateButton = findViewById(R.id.updateButton)
        emailText = findViewById(R.id.profileEmail)
        displayNameText = findViewById(R.id.profileDisplayName)

        displayUserDetails()

        logoutButton.setOnClickListener(){
            FirebaseAuth.getInstance().signOut()
            val homePageIntent = Intent(this@ProfileActivity, HomeActivity::class.java)
            startActivity(homePageIntent)
        }

       updateButton.setOnClickListener(){
            var newDisplayName = displayNameText.getText().toString()
            updateDisplayName(newDisplayName)
        }

    }

    private fun displayUserDetails(){

        val currentUser = auth.currentUser
        val userEmail = currentUser?.email

        if (userEmail != null) {
            firestore.collection("users")
                .whereEqualTo("email", userEmail)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    if (!querySnapshot.isEmpty) {
                        val document = querySnapshot.documents[0]
                        val userEmailData = document.getString("email")
                        val displayNameData = document.getString("displayName")

                        // Set retrieved data to EditText fields
                        emailText.setText(userEmailData)
                        displayNameText.setText(displayNameData)
                    } else {}
                }
                .addOnFailureListener { e ->
                }
        } else {
            // Current user's email is null
            // Handle the case where user email is null (should not happen in normal flow)
        }
    }

    fun updateDisplayName(newDisplayName: String) {
        val auth = FirebaseAuth.getInstance()
        val firestore = FirebaseFirestore.getInstance()

        val currentUser = auth.currentUser

        // Check if user is authenticated
        currentUser?.let { user ->
            val uid = user.uid
            val userRef = firestore.collection("users").document(uid)

            // Update displayName field in Firestore document
            userRef.update("displayName", newDisplayName)
                .addOnSuccessListener {
                    // Display a toast message indicating successful update
                    Toast.makeText(this@ProfileActivity, "Display name updated successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    // Display a toast message indicating failure with error details
                    Toast.makeText(this@ProfileActivity, "Error updating display name: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } ?: run {
            // User is not authenticated, display a toast message
            Toast.makeText(this@ProfileActivity, "User not authenticated", Toast.LENGTH_SHORT).show()
            println("User not authenticated")
        }
    }

}