package com.example.tarea3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val nombreEvento = intent.getStringExtra("nombre")
        val descripcionEvento = intent.getStringExtra("descripcion")
        val fechaEvento = intent.getStringExtra("fecha")

        val tvNombre: TextView = findViewById(R.id.tvNombre)
        val tvDescripcion: TextView = findViewById(R.id.tvDescripcion)
        val tvFecha: TextView = findViewById(R.id.tvFecha)

        tvNombre.text = "$nombreEvento"
        tvDescripcion.text = "$descripcionEvento"
        tvFecha.text = "$fechaEvento"
    }
}