package com.example.proy_ventas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Login : AppCompatActivity() {
    lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        cargarR()
        estadoButton()
    }
    //cargar la funcion de cargar R
    fun cargarR(){
        btnLogin = findViewById(R.id.logButton)
    }

    //estado boton
    fun estadoButton(){
        btnLogin.setOnClickListener{
            var aa = Intent(this, Home::class.java)
            startActivity(aa)
        }
    }
}