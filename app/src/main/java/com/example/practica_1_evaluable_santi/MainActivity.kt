package com.example.practica_1_evaluable_santi

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.AlarmClock
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class MainActivity :AppCompatActivity() {

    private lateinit var tvWelcome: TextView
    private lateinit var btnBackConfig: Button
    private lateinit var btnCall: ImageButton
    private lateinit var btnWeb: ImageButton
    private lateinit var btnAlarm: ImageButton
    private lateinit var btnExtra: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        showData()
        initEvent()
    }

    private fun initView() {
        tvWelcome = findViewById(R.id.tv_welcome)
        btnBackConfig = findViewById(R.id.btn_back_config)
        btnCall = findViewById(R.id.btn_call)
        btnWeb = findViewById(R.id.btn_web)
        btnAlarm = findViewById(R.id.btn_alarm)
        btnExtra = findViewById(R.id.btn_extra)
    }

    // Recogemos el dato que nos pasó ConfigActivity con putExtra
    private fun showData() {
        val name = getIntent().getStringExtra("name") ?: ""
        tvWelcome.text = getString(R.string.main_welcome, name)
    }

    private fun initEvent() {
        btnBackConfig.setOnClickListener {
            val intent = Intent(this, ConfigActivity::class.java)
            startActivity(intent)
        }

        btnCall.setOnClickListener {
            abrirCallActivity()
        }

        btnWeb.setOnClickListener {
            abrirWeb()
        }

        btnAlarm.setOnClickListener {
            setAlarm()
        }

        btnExtra.setOnClickListener {
            abrirCalculator()
        }
    }

    private fun abrirCallActivity() {
        // El teléfono lo guardó ConfigActivity
        val prefs = getSharedPreferences("config", MODE_PRIVATE)
        val phone = prefs.getString("phone", "") ?: ""

        val intent = Intent(this, CallActivity::class.java).apply {
            putExtra("phone", phone)
        }
        startActivity(intent)
    }

    private fun abrirWeb() {
        val url = getString(R.string.default_url)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            println("URL no disponible")
        }
    }

    private fun setAlarm() {

        val calendar = Calendar.getInstance().apply { add(Calendar.MINUTE, 2) }
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, getString(R.string.alarm_subject))
            putExtra(AlarmClock.EXTRA_HOUR, hour)
            putExtra(AlarmClock.EXTRA_MINUTES, minute)
            putExtra(AlarmClock.EXTRA_VIBRATE, true)
        }
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            println("Este dispositivo no tiene la aplicacion de reloj")
        }
    }

    private fun abrirCalculator() {
        val intent = Intent.makeMainSelectorActivity(
            Intent.ACTION_MAIN,
            Intent.CATEGORY_APP_CALCULATOR
        )
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            println("Este dispositivo no tiene la aplicacion de calculadora")
        }
    }
}