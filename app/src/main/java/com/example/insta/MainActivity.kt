package com.example.insta

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
       // window.statusBarColor= Color.TRANSPARENT //Here we making the splash screen's status bar color transparent but seems like there is no need for that

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this,SignUpScreen::class.java))
            finish() //here we are finishing the splash screen so that we cannot come back to it after pressing back button and we will get exit from the app
        },3000)


    }
}