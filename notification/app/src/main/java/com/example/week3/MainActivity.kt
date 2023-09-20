package com.example.week3

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings.ACTION_APP_NOTIFICATION_SETTINGS
import android.provider.Settings.EXTRA_APP_PACKAGE
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestSinglePermission(Manifest.permission.POST_NOTIFICATIONS)

        createNotificationChannel()


        val btn1 = findViewById<Button>(R.id.notify1)
        btn1.setOnClickListener {
            btnFlag = 1
            showNotification()
            n++
        }

        val btn2 = findViewById<Button>(R.id.notify2)
        btn2.setOnClickListener {
            btnFlag = 2
            val editText = findViewById<EditText>(R.id.editTextNotification)
            text = editText.text.toString()
            showNotification()
        }

        val btn3 = findViewById<Button>(R.id.settings)
        btn3.setOnClickListener {
            val intent = Intent(ACTION_APP_NOTIFICATION_SETTINGS)
            intent.putExtra(EXTRA_APP_PACKAGE, packageName);
            startActivity(intent)

        }

    }

    private var n = 1;
    private var btnFlag = 0;
    private var text = ""
    private val channelID1 = "default"
    private val channelID2 = "ad"
    private var myNotificationID = 1
    private var editTextNotificationID = 2

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel( channelID1, "default channel",
                NotificationManager.IMPORTANCE_DEFAULT )
            channel.description = "description text of this channel."
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

            val channel2 = NotificationChannel( channelID2, "ad channel",
                NotificationManager.IMPORTANCE_DEFAULT )
            channel2.description = "description text of this channel."
            val notificationManager2 = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager2.createNotificationChannel(channel2)
        }
    }

    private fun showNotification() {
        if(btnFlag == 1){

            val builder = NotificationCompat.Builder(this, channelID1)
            builder.setSmallIcon(R.mipmap.ic_launcher)
            builder.setContentTitle("NOTIFICATION LAB.")
            builder.setContentText("Notification #${n}")
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT)

            val notification = builder.build()
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                NotificationManagerCompat.from(this).notify(myNotificationID, notification)
            }
        }
        else if(btnFlag == 2) {
            val builder = NotificationCompat.Builder(this, channelID2)
            builder.setSmallIcon(R.mipmap.ic_launcher)
            builder.setContentTitle("NOTIFICATION LAB2.")
            builder.setContentText(text)
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT)

            val notification = builder.build()
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                NotificationManagerCompat.from(this).notify(editTextNotificationID, notification)
            }
        }
    }


    @SuppressLint("StringFormatInvalid")
    private fun requestSinglePermission(permission: String) {
        if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED)
            return
        val requestPermLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it == false) { // permission is not granted!
                AlertDialog.Builder(this).apply {
                    setTitle("Warning")
                    setMessage(getString(R.string.no_permission, permission))
                }.show()
            }
        }
        if (shouldShowRequestPermissionRationale(permission)) {
            // you should explain the reason why this app needs the permission.
            AlertDialog.Builder(this).apply {
                setTitle("Reason")
                setMessage(getString(R.string.req_permission_reason, permission))
                setPositiveButton("Allow") { _, _ -> requestPermLauncher.launch(permission) }
                setNegativeButton("Deny") { _, _ -> }
            }.show()
        } else {
            // should be called in onCreate()
            requestPermLauncher.launch(permission)
        }
    }
}