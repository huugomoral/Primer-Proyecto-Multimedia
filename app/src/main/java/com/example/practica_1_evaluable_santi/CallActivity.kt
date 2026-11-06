package com.example.practica_1_evaluable_santi

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class CallActivity : AppCompatActivity() {

    private lateinit var etCallNumber: EditText
    private lateinit var btnCallPhone: ImageButton
    private var phone: String = ""

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            call()
        } else {
            println("Necesita permisos")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call)
        initView()
        showData()
        initEvent()
    }

    private fun initView() {
        etCallNumber = findViewById(R.id.et_call_number)
        btnCallPhone = findViewById(R.id.btn_call_phone)
    }

    // Rellenamos el campo con el teléfono guardado en configuración (llegó con putExtra).
    // El usuario puede borrarlo y escribir otro.
    private fun showData() {
        val savedPhone = getIntent().getStringExtra("phone") ?: ""
        etCallNumber.setText(savedPhone)
    }

    private fun initEvent() {
        btnCallPhone.setOnClickListener {
            phone = etCallNumber.text.toString().trim().replace(" ", "")

            if (phone.isEmpty()) {
                println("Todo Correcto")
            } else {
                requestPermissions()
            }
        }
    }

    private fun requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // API 23 o superior
            if (PermissionPhone()) {
                call()
            } else {
                requestPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
            }
        } else {
            call() // API < 23: basta con el permiso del manifest
        }
    }

    private fun PermissionPhone(): Boolean =
        ContextCompat.checkSelfPermission(
            this, Manifest.permission.CALL_PHONE
        ) == PackageManager.PERMISSION_GRANTED

    private fun call() {
        val intent = Intent(Intent.ACTION_CALL).apply {
            data = Uri.parse("tel:$phone")
        }
        startActivity(intent)
    }
}