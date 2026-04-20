package com.example.hardwarelab

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private val REQUEST_CAMERA = 100
    private val CHANNEL_ID = "teacher_channel"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificationChannel()

        findViewById<Button>(R.id.btnAttendance).setOnClickListener {
            markAttendance()
        }

        findViewById<Button>(R.id.btnReminder).setOnClickListener {
            sendReminderNotification()
        }

        findViewById<Button>(R.id.btnPhotoBoard).setOnClickListener {
            checkCameraPermission()
        }
    }

    // УЧЁТ ПОСЕЩАЕМОСТИ
    private fun markAttendance() {
        // Имитация отметки посещаемости
        Toast.makeText(this, "Посещаемость отмечена ✓", Toast.LENGTH_LONG).show()
        vibrateDevice() // вибрация при успешной отметке
    }

    //УВЕДОМЛЕНИЕ
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Напоминания преподавателю",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Напоминания о парах"
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    private fun sendReminderNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 101)
                return
            }
        }

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setContentTitle("Напоминание о паре")
            .setContentText("Через 10 минут начинается пара 'Мобильная разработка'")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        NotificationManagerCompat.from(this).notify(2, builder.build())
        Toast.makeText(this, "Напоминание отправлено", Toast.LENGTH_SHORT).show()
    }

    // ВИБРАЦИЯ
    private fun vibrateDevice() {
        @Suppress("DEPRECATION")
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        if (vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createWaveform(longArrayOf(0, 300, 200, 300, 200), -1))
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(longArrayOf(0, 300, 200, 300, 200), -1)
            }
            Toast.makeText(this, "Вибрация запущена", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Вибрация не поддерживается на этом устройстве", Toast.LENGTH_SHORT).show()
        }
    }

    //КАМЕРА
    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED) {
            openCamera()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_CAMERA
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CAMERA) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else {
                Toast.makeText(this, "Доступ к камере запрещён", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun openCamera() {
        val intent = Intent(this, CameraActivity::class.java)
        startActivity(intent)
    }
}