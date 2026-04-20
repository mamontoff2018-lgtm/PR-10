package com.example.hardwarelab

import android.os.VibrationEffect
import android.os.Vibrator
import android.hardware.Camera
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException

@Suppress("DEPRECATION")
class CameraActivity : AppCompatActivity(), SurfaceHolder.Callback {

    private var camera: Camera? = null
    private lateinit var surfaceView: SurfaceView
    private lateinit var surfaceHolder: SurfaceHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        surfaceView = findViewById(R.id.surfaceView)
        surfaceHolder = surfaceView.holder
        surfaceHolder.addCallback(this)

        findViewById<Button>(R.id.btnCloseCamera).setOnClickListener {
            finish()
        }
    }
    override fun surfaceCreated(holder: SurfaceHolder) {
        try {
            camera = Camera.open()
            camera?.setPreviewDisplay(holder)         // Привязываем превью
            camera?.startPreview()
        } catch (e: IOException) {
            Toast.makeText(this, "Ошибка открытия камеры: ${e.message}", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        } catch (e: Exception) {
            Toast.makeText(this, "Камера недоступна или занята", Toast.LENGTH_LONG).show()
        }
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        camera?.stopPreview()
        camera?.startPreview()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        if (camera != null) {
            camera?.stopPreview()
            camera?.release()      // Освобождаем камеру
            camera = null
        }
    }

    //ПАУЗА
    override fun onPause() {
        super.onPause()
        if (camera != null) {
            camera?.stopPreview()
            camera?.release()
            camera = null
        }
    }
}