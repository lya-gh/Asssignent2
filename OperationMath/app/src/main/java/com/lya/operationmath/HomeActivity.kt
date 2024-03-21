package com.lya.operationmath


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val startApp = findViewById<Button>(R.id.beginButton)
        val extraStuff = findViewById<Button>(R.id.extrasButton)

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
    }
}