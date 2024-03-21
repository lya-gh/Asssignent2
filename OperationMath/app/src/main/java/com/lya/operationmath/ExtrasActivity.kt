package com.lya.operationmath

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import com.lya.operationmath.R


private val PICK_IMAGE_REQUEST = 1

class ExtrasActivity : AppCompatActivity() {
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extras)


        val mapsButton = findViewById<ImageButton>(R.id.mapsImageButton)
        val galleryButton = findViewById<ImageButton>(R.id.galleryImageButton)
        val messageButton = findViewById<ImageButton>(R.id.messageImageButton)
        val browserButton = findViewById<ImageButton>(R.id.browserImageButton)
        val phoneButton = findViewById<ImageButton>(R.id.phoneImageButton)

        //Open maps using OnClick Event
        /*mapsButton.setOnClickListener {
            val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0"))
            startActivity(mapIntent)
        }*/

        //Open maps using OnTouchEvent
        mapsButton.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0"))
                startActivity(mapIntent)
                true
            } else {
                false
            }
        }

        //Open gallery using OnClick Event
        galleryButton.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
            galleryIntent.type = "image/*"
            startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST)
        }

        //Open messaging app using OnClick Event
        messageButton.setOnClickListener {
            val smsUri = Uri.parse("smsto:")
            val smsIntent = Intent(Intent.ACTION_VIEW, smsUri)
            startActivity(smsIntent)
        }

        //Open browser using OnClick Event
        browserButton.setOnClickListener {
            val url = "https://www.google.com"

            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        }

        //Open phone using OnClick Event
        phoneButton.setOnClickListener {
            val dialIntent = Intent(Intent.ACTION_VIEW, Uri.parse("tel:"))
            startActivity(dialIntent)
        }
    }

}
