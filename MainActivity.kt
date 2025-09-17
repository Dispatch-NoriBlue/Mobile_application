@file:Suppress("DEPRECATION")

package com.example.myapplication____5

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var imageButton1: ImageButton
    lateinit var imageButton2: ImageButton
    lateinit var imageButton3: ImageButton
    lateinit var imageButton4: ImageButton
    lateinit var spinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        // ✅ โหลดภาษาที่เคยเลือกไว้ (แต่ไม่เรียก setLocale() เพื่อป้องกันการเด้งซ้ำ)
        loadLocale()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        imageButton1 = findViewById(R.id.imageButton1)
        imageButton2 = findViewById(R.id.imageButton2)
        imageButton3 = findViewById(R.id.imageButton3)
        imageButton4 = findViewById(R.id.imageButton4)
        spinner = findViewById(R.id.spinner1)

        val languages = arrayOf("English", "ไทย")
        val locales = arrayOf("en", "th")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter


        val sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE)
        val savedLanguage = sharedPreferences.getString("My_Lang", "en") ?: "en"


        val selectedIndex = locales.indexOf(savedLanguage)
        if (selectedIndex != -1) {
            spinner.setSelection(selectedIndex)
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedLanguage = locales[position]
                if (selectedLanguage != savedLanguage) {
                    setLocale(selectedLanguage)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }


        imageButton1.setOnClickListener { startActivity(Intent(this, Next_activity::class.java)) }
        imageButton2.setOnClickListener { startActivity(Intent(this, NextActivity2::class.java)) }
        imageButton3.setOnClickListener { startActivity(Intent(this, NextActivity3::class.java)) }
        imageButton4.setOnClickListener { startActivity(Intent(this, NextActivity4::class.java)) }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // ✅ ฟังก์ชันเปลี่ยนภาษา + บันทึกค่าภาษา
    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)

        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        // ✅ บันทึกค่าภาษา
        val editor: SharedPreferences.Editor = getSharedPreferences("Settings", MODE_PRIVATE).edit()
        editor.putString("My_Lang", languageCode)
        editor.apply()

        // ✅ รีสตาร์ท Activity (แต่ต้องกันไม่ให้เรียกซ้ำตอนเปิดแอป)
        finish()
        startActivity(intent)
    }

    // ✅ โหลดค่าภาษาที่บันทึกไว้ แต่ **ไม่เรียก setLocale() ตรงนี้**
    private fun loadLocale() {
        val prefs: SharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE)
        val language = prefs.getString("My_Lang", "en") ?: "en"
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
    }
}
