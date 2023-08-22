package com.example.practicapaises_s4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val nombrePais = intent.getStringExtra("nombrePais")

        val tv1: TextView = findViewById(R.id.tv1)
        tv1.text = "Mostrar la información de $nombrePais"
        val tvInfoTit: TextView = findViewById(R.id.tvInfoTitulo)
        tvInfoTit.text = "Información del país $nombrePais"
        spinnerExample(nombrePais)
    }
    fun spinnerExample(nombrePais: String?){
        //val elementos = listOf("Elemento 1", "Elemento 2", "Elemento 3")

        val elementos = resources.getStringArray(R.array.infopaises_options)
        val spinner: Spinner = findViewById(R.id.spinner)
        val calcularButton: Button = findViewById(R.id.btnBuscar)

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
                //Toast.makeText(this@MainActivity2, "Seleccionaste: $itemSeleccionado", Toast.LENGTH_SHORT).show()
                calcularButton.setOnClickListener {
                    buscarInfoPais(itemSeleccionado, nombrePais)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Se llama cuando no se ha seleccionado nada en el Spinner (opcional)
                Toast.makeText(this@MainActivity2, "Nada", Toast.LENGTH_SHORT).show()
            }
        }

    }
    fun buscarInfoPais(itemSeleccionado: String, nombrePais: String?){
        val tvInfo:TextView = findViewById(R.id.tvInfo)
        val calcularButton2: Button = findViewById(R.id.btnAceptar)
        val calcularButton3: Button = findViewById(R.id.btnCancelar)
        val diccPaises = hashMapOf<String, Any>(
            "Costa Rica" to hashMapOf<String, Any>(
                "Habitantes" to "Número de Habitantes: Aproximadamente 5 millones",
                "Territorio" to "Cantidad de Territorio: Alrededor de 51,100 kilómetros cuadrados",
                "Capital" to "Capital: San José",
                "Continente" to "Continente: América Central",
                "Paises Vecinos" to "Países Vecinos: Nicaragua al norte y Panamá al sureste."

            ),
            "Panama" to hashMapOf<String, Any>(
                "Habitantes" to "Número de Habitantes: Aproximadamente 4.3 millones",
                "Territorio" to "Cantidad de Territorio: Alrededor de 75,420 kilómetros cuadrados",
                "Capital" to "Capital: Ciudad de Panamá",
                "Continente" to "Continente: América Central",
                "Paises Vecinos" to "Países Vecinos: Costa Rica al oeste y Colombia al sureste."

            ),
            "Nicaragua" to hashMapOf<String, Any>(
                "Habitantes" to "Número de Habitantes: Aproximadamente 6.8 millones",
                "Territorio" to "Cantidad de Territorio: Alrededor de 130,373 kilómetros cuadrados",
                "Capital" to "Capital: Managua",
                "Continente" to "Continente: América Central",
                "Paises Vecinos" to "Países Vecinos: Honduras al norte y Costa Rica al sur"

            ),
            "Mexico" to hashMapOf<String, Any>(
                "Habitantes" to "Número de Habitantes: Aproximadamente 126 millones",
                "Territorio" to "Cantidad de Territorio: Alrededor de 1,964,375 kilómetros cuadrados",
                "Capital" to "Capital: Ciudad de México",
                "Continente" to "Continente: América del Norte",
                "Paises Vecinos" to "Países Vecinos: Estados Unidos al norte y Belice y Guatemala al sureste"
            )
        )
        val resultado: String? = when (nombrePais) {
            "Costa Rica" -> when (itemSeleccionado) {
                "Habitantes" ->  (diccPaises["Costa Rica"] as? Map<String,String>)?.get("Habitantes")
                "Territorio" ->  (diccPaises["Costa Rica"] as? Map<String,String>)?.get("Territorio")
                "Capital" ->  (diccPaises["Costa Rica"] as? Map<String,String>)?.get("Capital")
                "Continente" ->  (diccPaises["Costa Rica"] as? Map<String,String>)?.get("Continente")
                "Paises Vecinos" ->  (diccPaises["Costa Rica"] as? Map<String,String>)?.get("Paises Vecinos")
                else -> "No se encontró la información seleccionada"}
            "Panama" -> when (itemSeleccionado) {
                "Habitantes" ->  (diccPaises["Panama"] as? Map<String,String>)?.get("Habitantes")
                "Territorio" ->  (diccPaises["Panama"] as? Map<String,String>)?.get("Territorio")
                "Capital" ->  (diccPaises["Panama"] as? Map<String,String>)?.get("Capital")
                "Continente" ->  (diccPaises["Panama"] as? Map<String,String>)?.get("Continente")
                "Paises Vecinos" ->  (diccPaises["Panama"] as? Map<String,String>)?.get("Paises Vecinos")
                else -> "No se encontró la información seleccionada"}
            "Nicaragua" -> when (itemSeleccionado) {
                "Habitantes" ->  (diccPaises["Nicaragua"] as? Map<String,String>)?.get("Habitantes")
                "Territorio" ->  (diccPaises["Nicaragua"] as? Map<String,String>)?.get("Territorio")
                "Capital" ->  (diccPaises["Nicaragua"] as? Map<String,String>)?.get("Capital")
                "Continente" ->  (diccPaises["Nicaragua"] as? Map<String,String>)?.get("Continente")
                "Paises Vecinos" ->  (diccPaises["Nicaragua"] as? Map<String,String>)?.get("Paises Vecinos")
                else -> "No se encontró la información seleccionada"}
            "Mexico" -> when (itemSeleccionado) {
                "Habitantes" ->  (diccPaises["Mexico"] as? Map<String,String>)?.get("Habitantes")
                "Territorio" ->  (diccPaises["Mexico"] as? Map<String,String>)?.get("Territorio")
                "Capital" ->  (diccPaises["Mexico"] as? Map<String,String>)?.get("Capital")
                "Continente" ->  (diccPaises["Mexico"] as? Map<String,String>)?.get("Continente")
                "Paises Vecinos" ->  (diccPaises["Mexico"] as? Map<String,String>)?.get("Paises Vecinos")
                else -> "No se encontró la información seleccionada"}
            else -> "No se encontró la información seleccionada"
        }
        tvInfo.text = resultado.toString()
        calcularButton2.setOnClickListener {
            callActivity1(resultado, nombrePais)
        }
        calcularButton3.setOnClickListener {
            setResult(RESULT_CANCELED);
            finish();
        }
    }
    fun callActivity1(infoPais : String?, pais: String?){
        // Crear un Intent para iniciar la Activity
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("nombrePais", pais)
        intent.putExtra("infoPais", infoPais)
        setResult(RESULT_OK, intent)
        finish()
        // Iniciar la Activity1 utilizando el Intent
        //startActivity(intent)
    }
}