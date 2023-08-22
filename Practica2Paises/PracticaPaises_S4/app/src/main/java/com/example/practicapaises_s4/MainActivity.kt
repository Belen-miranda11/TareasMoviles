package com.example.practicapaises_s4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

private lateinit var startForResult: ActivityResultLauncher<Intent>

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == androidx.appcompat.app.AppCompatActivity.RESULT_OK) {
                val data: android.content.Intent? = result.data
                val resultado1 = data?.getStringExtra("nombrePais")
                val resultado2 = data?.getStringExtra("infoPais")
                // Hacer algo con el resultado recibido de Activity3
                Toast.makeText(this@MainActivity, "Información mostrada de $resultado1: $resultado2", Toast.LENGTH_LONG).show()
            }
        }
        listViewExample()

    }
    fun listViewExample(){
        val paises = listOf("Costa Rica", "Panama", "Nicaragua", "Mexico")
//        val paises = resources.getStringArray(R.array.paises_options)
        val listView: ListView = findViewById(R.id.lv1)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, paises)
        listView.adapter = adapter
        listView.onItemClickListener = object : AdapterView.OnItemClickListener{
            override fun onItemClick(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val nombreSeleccionado = paises[position]
                Toast.makeText(this@MainActivity, "País Seleccionado: $nombreSeleccionado", Toast.LENGTH_SHORT).show()
                callActivity2(nombreSeleccionado)
            }
        }
    }
    fun callActivity2(nombrePaises : String){
        // Crear un Intent para iniciar la Activity
        val intent = Intent(this, MainActivity2::class.java)
        intent.putExtra("nombrePais",nombrePaises)
        // Iniciar la Activity2 utilizando el Intent
        startForResult.launch(intent)
    }
}
