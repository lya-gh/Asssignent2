package com.lya.operationmath


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    private lateinit var startApp: Button
    private lateinit var extraStuff: Button
    private lateinit var profileButton: Button
    private lateinit var loginButton: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth = FirebaseAuth.getInstance()

        startApp = findViewById(R.id.beginButton)
        extraStuff = findViewById(R.id.extrasButton)
        loginButton = findViewById(R.id.loginHomeButton)
        profileButton = findViewById(R.id.profileHomeButton)

        checkUserLoggedIn()

        // Go to main page
        startApp.setOnClickListener {
            val mainIntent = Intent(this@HomeActivity, MainActivity::class.java)
            startActivity(mainIntent)
        }

        // Go to extras page
        extraStuff.setOnClickListener {
            val mainIntent = Intent(this@HomeActivity, ExtrasActivity::class.java)
            startActivity(mainIntent)
        }

        // Go to Login page
        loginButton.setOnClickListener {
            val loginIntent = Intent(this@HomeActivity, LoginActivity::class.java)
            startActivity(loginIntent)
        }

        profileButton.setOnClickListener {
            val profileIntent = Intent(this@HomeActivity, ProfileActivity::class.java)
            startActivity(profileIntent)
        }

    }

    private fun checkUserLoggedIn(){
        val currentUser = auth.currentUser
        if (currentUser != null)
        {
            profileButton.visibility= View.VISIBLE
            profileButton.isEnabled=true
            loginButton.visibility= View.INVISIBLE
            loginButton.isEnabled=false
        }
        else
        {
            profileButton.visibility= View.INVISIBLE
            profileButton.isEnabled=false
            loginButton.visibility= View.VISIBLE
            loginButton.isEnabled=true

        }
    }
}