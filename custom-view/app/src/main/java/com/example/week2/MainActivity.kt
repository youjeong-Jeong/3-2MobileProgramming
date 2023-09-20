package com.example.week2

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val recBtn = findViewById<RadioButton>(R.id.radioRect)
        val cirBtn = findViewById<RadioButton>(R.id.radioCircle)

        val myView = findViewById<MyView>(R.id.view) // <MyView>로 안해서 오류

        radioGroup.check(recBtn.id)


        recBtn.setOnClickListener {
            //radioGroup.check(recBtn.id)
            myView.setShape("rect")
        }

        cirBtn.setOnClickListener {
            //radioGroup.check(cirBtn.id)
            myView.setShape("cir")
        }

    }
}