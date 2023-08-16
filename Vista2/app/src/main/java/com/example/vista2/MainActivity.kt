package com.example.vista2

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        spinnerExample()

    }
    fun suma(view:View){
        val et1:EditText = findViewById(R.id.et1)
        val et2:EditText = findViewById(R.id.et2)
        val tvResultado:TextView = findViewById(R.id.tvResultado)
        val rSuma:RadioButton = findViewById(R.id.r1)
        var valor1 = et1.text.toString().toInt()
        var valor2 = et2.text.toString().toInt()

        var resultado = if (rSuma.isChecked)
            valor1+valor2
        else
            valor1-valor2

        tvResultado.text = resultado.toString()
    }
    fun spinnerExample(){
        //val elementos = listOf("Elemento 1", "Elemento 2", "Elemento 3")

        val elementos = resources.getStringArray(R.array.spinner_options)
        val spinner: Spinner = findViewById(R.id.spinner)
        val calcularButton: Button = findViewById(R.id.button2)

        // Crea un ArrayAdapter usando los elementos y el diseño predeterminado para el spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, elementos)

        // Especifica el diseño que se usará cuando se desplieguen las opciones
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Une el ArrayAdapter al Spinner
        spinner.adapter = adapter

        // Opcionalmente, puedes configurar un escuchador para detectar la selección del usuario
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val itemSeleccionado = elementos[position]
                // Realiza alguna acción con el elemento seleccionado
                Toast.makeText(this@MainActivity, "Seleccionaste: $itemSeleccionado", Toast.LENGTH_SHORT).show()
                calcularButton.setOnClickListener {
                    calcularResultadoSpinner(itemSeleccionado)
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Se llama cuando no se ha seleccionado nada en el Spinner (opcional)
                Toast.makeText(this@MainActivity, "Nada", Toast.LENGTH_SHORT).show()
            }
        }

    }
    fun calcularResultadoSpinner(item: String){
        val et1:EditText = findViewById(R.id.et1)
        val et2:EditText = findViewById(R.id.et2)
        val tvResultado:TextView = findViewById(R.id.tvResultado)
        var valor1 = et1.text.toString().toInt()
        var valor2 = et2.text.toString().toInt()

        val resultado: Int = when (item) {
            "Suma" -> valor1 + valor2 // Suma
            "Resta" -> valor1 - valor2 // Resta
            "Multiplicacion" -> valor1 * valor2 // Multiplicación
            "Division" -> valor1 / valor2 // División
            else -> 0
        }
        tvResultado.text = resultado.toString()

    }
}