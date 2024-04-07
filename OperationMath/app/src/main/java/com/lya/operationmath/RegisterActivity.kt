package com.lya.operationmath

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var register : Button
    private lateinit var email : EditText
    private lateinit var password : EditText
    private lateinit var displayName : EditText

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        register = findViewById<Button>(R.id.registerButton)
        email = findViewById<EditText>(R.id.registerEmail)
        password = findViewById<EditText>(R.id.registerPassword)
        displayName = findViewById<EditText>(R.id.registerDisplayName)

        register.setOnClickListener() {
            var email_input = email.getText().toString()
            var password_input = password.getText().toString()
            var displayName_input = displayName.getText().toString()

            if (TextUtils.isEmpty(email_input) || TextUtils.isEmpty(password_input) || TextUtils.isEmpty(
                    displayName_input
                )
            ) {
                Toast.makeText(this@RegisterActivity, "Empty credentials", Toast.LENGTH_SHORT)
                    .show()
            } else if (password_input.length < 6) {
                Toast.makeText(this@RegisterActivity, "Password too short", Toast.LENGTH_SHORT)
                    .show()
            } else {
                registerUser(email_input, password_input, displayName_input)
            }
        }
    }

    private fun registerUser(email: String, password: String,displayName: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this@RegisterActivity, OnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@RegisterActivity, "Registration successful", Toast.LENGTH_SHORT).show()
                    val user = auth.currentUser
                    if (user != null) {
                        storeUserDataToFireStore(user.uid, email, displayName)
                    }
                    val homePageIntent = Intent(this@RegisterActivity, HomeActivity::class.java)
                    startActivity(homePageIntent)
                } else {
                    Toast.makeText(this@RegisterActivity, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

    private fun storeUserDataToFireStore(uid:String, email:String, displayName:String){

        val firestore = FirebaseFirestore.getInstance()
        val userRef = firestore.collection("users").document(uid)
        val userData = hashMapOf(
            "email" to email,
            "displayName" to displayName
        )

        userRef.set(userData)
            .addOnSuccessListener {
            // Data successfully stored
            }
            .addOnFailureListener { e ->
            // Handle failure
            }
    }


}