package com.example.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    lateinit var myDao: MyDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        myDao = MyDatabase.getDatabase(this).getMyDao()

        CoroutineScope(Dispatchers.IO).launch{
            with(myDao){
                insertStudent(Student(1, "james"))
                insertStudent(Student(2, "john"))
                insertClass(ClassInfo(1, "c-lang", "Mon 9:00", "E301", 1))
                insertClass(ClassInfo(2, "android prog", "Tue 9:00", "E302", 1))
                insertEnrollment(Enrollment(1, 1))
                insertEnrollment(Enrollment(1, 2))
            }
        }
        val allStudents = myDao.getAllStudents()
        allStudents.observe(this){
            val str = java.lang.StringBuilder().apply{
                for((id, name) in it) {
                    append(id)
                    append("-")
                    append(name)
                    append("\n")
                }
            }.toString()
            val textView = findViewById<TextView>(R.id.text_student_list)
            textView.text= str
        }


        findViewById<Button>(R.id.query_student).setOnClickListener{
            val id = findViewById<EditText>(R.id.edit_student_id).text.toString()
            CoroutineScope(Dispatchers.IO).launch{
                val results = myDao.getStudentsWithEnrollment(id.toInt())
                //val queryStudent = findViewById<TextView>(R.id.query_student)
                val textQueryStudent = findViewById<TextView>(R.id.text_query_student)
                if (results.isNotEmpty()) {
                    val str = StringBuilder().apply{
                        append(results[0].student.id)
                        append("-")
                        append(results[0].student.name)
                        append(":")
                        for (c in results[0].enrollments) {
                            append(c.cid)
                            val cls_result = myDao.getClassInfo(c.cid)
                            if (cls_result.isNotEmpty())
                                append("(${cls_result[0].name})")
                            append(",")
                        }
                    }
                    withContext(Dispatchers.Main){
                        textQueryStudent.text= str
                    }
                } else {
                    withContext(Dispatchers.Main){
                        textQueryStudent.text= ""
                    }
                }
            }
        }
        findViewById<Button>(R.id.add_student).setOnClickListener{
            val id = findViewById<EditText>(R.id.edit_student_id).text.toString().toInt()
            val name = findViewById<EditText>(R.id.edit_student_name).text.toString()
            if (id > 0 && name.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch{
                    myDao.insertStudent(Student(id, name))
                }
            }
        }

        findViewById<Button>(R.id.del_student).setOnClickListener {
            val id = findViewById<EditText>(R.id.edit_student_id).text.toString().toInt()
            if (id > 0) {
                CoroutineScope(Dispatchers.IO).launch {
                    myDao.deleteStudent(Student(id, ""))
                }
            }
        }

        findViewById<Button>(R.id.enroll).setOnClickListener {
            val id = findViewById<EditText>(R.id.edit_student_id).text.toString().toInt()
            if (id > 0) {
                CoroutineScope(Dispatchers.IO).launch {
                    myDao.insertEnrollment(Enrollment(id, 1))
                }
            }
        }



    }
}