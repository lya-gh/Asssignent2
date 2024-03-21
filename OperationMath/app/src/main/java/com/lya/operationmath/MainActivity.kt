package com.lya.operationmath


import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {

    private lateinit var randomNumber1: TextView
    private lateinit var randomNumber2: TextView
    private lateinit var operation: TextView
    private lateinit var userAnswer: EditText
    private lateinit var submitButton: Button
    private lateinit var restartButton: Button
    private lateinit var homeButton: Button
    private lateinit var statusText: TextView
    private lateinit var timerText:TextView
    private lateinit var countDownTimer: CountDownTimer
    private var isTimerRunning = false
    private var score:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        randomNumber1 = findViewById<TextView>(R.id.randomNumGen1)
        randomNumber2 = findViewById<TextView>(R.id.randomNumGen2)
        operation = findViewById<TextView>(R.id.operationText)
        userAnswer = findViewById<EditText>(R.id.answerInput)
        submitButton = findViewById<Button>(R.id.submitButton)
        restartButton = findViewById<Button>(R.id.restartButton)
        homeButton = findViewById<Button>(R.id.homeButton)
        statusText = findViewById<TextView>(R.id.statusText)
        timerText = findViewById<TextView>(R.id.timerView)

        restartButton.isEnabled=false
        restartButton.visibility=View.INVISIBLE

        // Timer initialization
        countDownTimer = object : CountDownTimer(11000,1000){

            // Function to display timer per second during countdown
            override fun onTick(millisUntilFinished: Long) {
                timerText.text = (millisUntilFinished/1000).toString()
                timerText.setVisibility(View.VISIBLE)
            }

            // Function when timer finishes
            override fun onFinish(){
                statusText.setText("Time's up! \n You got $score right!")
                timerText.visibility=View.INVISIBLE
                userAnswer.isEnabled=false
                userAnswer.visibility=View.INVISIBLE
                submitButton.isEnabled=false
                submitButton.visibility=View.INVISIBLE
                restartButton.isEnabled = true
                restartButton.visibility=View.VISIBLE
            }
        }

        // Game starts here
        startGame()

        //Call function to check answer when submit button is pressed
        submitButton.setOnClickListener(){
            checkAnswer()
        }

        //Call function to restart game when try again button is pressed
        restartButton.setOnClickListener(){
            startGame()
            submitButton.visibility = View.VISIBLE
            restartButton.isEnabled = true
            restartButton.visibility = View.INVISIBLE
        }

        // Go to home page when home button is long pressed
        homeButton.setOnLongClickListener(){
            val homeIntent = Intent(this@MainActivity, HomeActivity::class.java)
            startActivity(homeIntent)
            return@setOnLongClickListener true;
        }

    }

    // Function to start game
    private fun startGame(){
        generateRandomNumbers()
        score = 0
        userAnswer.setText("")
        userAnswer.visibility=View.VISIBLE
        statusText.setText("")
        userAnswer.hint=("Answer")
        if(!isTimerRunning){
            countDownTimer.start()
            isTimerRunning = true
        }
        else{
            countDownTimer.cancel()
            countDownTimer.start()
        }
        submitButton.isEnabled = true
        userAnswer.isEnabled=true

    }

    // Function to generate random numbers
    private fun generateRandomNumbers(){
        val operationArray = arrayOf("+","-","x","÷")
        operation.text = (operationArray).random()

        when(operation.text){
            "+" -> {
                randomNumber1.text = (0..10).random().toString()
                randomNumber2.text = (0..10).random().toString()
            }
            "-" -> {
                randomNumber1.text = (0..20).random().toString()
                do {
                    randomNumber2.text = (0..10).random().toString()
                }
                while(randomNumber2.text.toString().toInt() > randomNumber1.text.toString().toInt())
            }
            "x" -> {
                randomNumber1.text = (0..10).random().toString()
                randomNumber2.text = (0..10).random().toString()
            }
            "÷" -> {
                val dividend = intArrayOf(0,1,2,3,4,5,6,7,8,9,10,12,14,15,16,18,20,21,24,25,27,28,30,32,
                    35,36,40,42,45,48,49,50,54,56,60,63,64,70,72,80,81,90,100)
                randomNumber1.text = (dividend).random().toString()
                do {
                    randomNumber2.text = (1..10).random().toString()
                }
                while((randomNumber1.text.toString().toInt() % randomNumber2.text.toString().toInt()) != 0)
            }
        }
    }

    // Function to check if answer is right or wrong
    private fun checkAnswer(){
        try{
            val userAnswerValue = userAnswer.text.toString().toInt()
            val sum = randomNumber1.text.toString().toInt() + randomNumber2.text.toString().toInt()
            val difference = randomNumber1.text.toString().toInt() - randomNumber2.text.toString().toInt()
            val product = randomNumber1.text.toString().toInt() * randomNumber2.text.toString().toInt()
            val divisor = randomNumber2.text.toString().toInt()

            if ((operation.text=="+" && userAnswerValue == sum) ||
                (operation.text=="-" && userAnswerValue == difference) ||
                (operation.text=="x" && userAnswerValue == product)||
                (operation.text=="÷" && userAnswerValue == randomNumber1.text.toString().toInt()/divisor && divisor!=0))
            {
                confirmCorrect()
            }
            else
            {
                statusText.setText("Try again!")
            }
        }catch(e: NumberFormatException){
            statusText.setText("That's not a number!")
        }
    }

    // Function to display message if answer is correct
    private fun confirmCorrect(){
        statusText.setText("Correct!")
        Handler(Looper.getMainLooper()).postDelayed({
            score++
            generateRandomNumbers()
            userAnswer.setText("")
            statusText.setText("")
            countDownTimer.start()
        }, 1000)
    }

}