package com.example.practica_1_evaluable_santi

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ConfigActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etPhone: EditText
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)
        initView()
        loadSavedData()
        initEvent()
    }

    private fun initView() {
        etName = findViewById(R.id.et_name)
        etPhone = findViewById(R.id.et_phone)
        btnSave = findViewById(R.id.btn_save)
    }

    private fun loadSavedData() {
        val prefs = getSharedPreferences("config", MODE_PRIVATE)
        etName.setText(prefs.getString("name", ""))
        etPhone.setText(prefs.getString("phone", ""))
    }

    private fun initEvent() {
        btnSave.setOnClickListener {
            val name = etName.text.toString().trim()
            val phone = etPhone.text.toString().trim()

            if (name.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, getString(R.string.msg_fill_fields), Toast.LENGTH_LONG).show()
            } else {
                val prefs = getSharedPreferences("config", MODE_PRIVATE)
                prefs.edit()
                    .putString("name", name)
                    .putString("phone", phone)
                    .apply()

                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("name", name)
                }
                startActivity(intent)
            }
        }
    }
}