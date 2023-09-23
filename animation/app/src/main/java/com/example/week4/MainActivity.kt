package com.example.week4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.transition.Fade
import androidx.transition.Scene
import androidx.transition.TransitionManager

class MyViewModel : ViewModel() {

    var studentName:String = ""
    var studentAddress:String = ""
    var studentGrade: Int = 0
    var staffName:String = ""
    var staffAddress:String = ""

}


class MainActivity : AppCompatActivity() {
    private lateinit var scene1: Scene
    private lateinit var scene2: Scene
    private lateinit var viewModel: MyViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[MyViewModel::class.java]

        val sceneRoot = findViewById<FrameLayout>(R.id.scene_root)
        scene1 = Scene.getSceneForLayout(sceneRoot, R.layout.student_layout, this)
        scene2 = Scene.getSceneForLayout(sceneRoot, R.layout.staff_layout, this)

        val studentBtn = findViewById<RadioButton>(R.id.radioStudent)
        studentBtn.setOnClickListener {
            saveStaffData()
            TransitionManager.go(scene1, Fade())
            restoreStudentData()
        }

        val staffBtn = findViewById<RadioButton>(R.id.radioStaff)
        staffBtn.setOnClickListener {
            saveStudentData()
            TransitionManager.go(scene2, Fade())
            restoreStaffData()
        }
    }
    private fun saveStudentData() {
        viewModel.studentName = findViewById<EditText>(R.id.editTextName).text.toString()
        viewModel.studentAddress = findViewById<EditText>(R.id.editTextAddr).text.toString()
        viewModel.studentGrade = findViewById<RadioGroup>(R.id.radioGrade).checkedRadioButtonId
    }

    private fun saveStaffData() {
        viewModel.staffName = findViewById<EditText>(R.id.editTextName).text.toString()
        viewModel.staffAddress = findViewById<EditText>(R.id.editTextAddr).text.toString()
    }

    private fun restoreStudentData() {
        findViewById<EditText>(R.id.editTextName).setText(viewModel.studentName)
        findViewById<EditText>(R.id.editTextAddr).setText(viewModel.studentAddress)
        findViewById<RadioGroup>(R.id.radioGrade).check(viewModel.studentGrade)
    }

    private fun restoreStaffData() {
        findViewById<EditText>(R.id.editTextName).setText(viewModel.staffName)
        findViewById<EditText>(R.id.editTextAddr).setText(viewModel.staffAddress)
    }

}