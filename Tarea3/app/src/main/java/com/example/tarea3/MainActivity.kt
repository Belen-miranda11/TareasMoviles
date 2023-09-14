package com.example.tarea3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.Locale

data class Evento(
    val nombre: String,
    val descripcion: String,
    val fecha: String
)

class MainActivity : AppCompatActivity() {
    private lateinit var lectura1: String
    private lateinit var adapter : ArrayAdapter<*>
    private lateinit var listaDeEventos : MutableList<String>
    private lateinit var listaJSONEventos: MutableList<Evento>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Mi Barra de Acción"

        listaDeEventos = mutableListOf()
        listaJSONEventos = mutableListOf()

        val prefs : SharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val isFirstTime: Boolean = prefs.getBoolean("esPrimeraVez", true)
        lectura1 = prefs.getString("lectura", "").toString()

        if (isFirstTime) {
            // Si es la primera vez, mostrar el cuadro de diálogo de elección
            mostrarDialogSDMI()
            // Guardar que la aplicación ya se ha iniciado una vez
            with(prefs.edit()) {
                putBoolean("esPrimeraVez", false)
                apply()
            }
        }else{
            if (lectura1.equals("SD")){
                Toast.makeText(this, "Cargando datos de SD", Toast.LENGTH_SHORT).show()
                leerArchivoSD()
            }
            else if (lectura1.equals("Memoria Interna")) {
                Toast.makeText(this, "Cargando datos de MI", Toast.LENGTH_SHORT).show()
                leerArchivoMI()
            }
        }
        listViewExample()
    }

    fun savePreferences(opcion: String) {
        // Obtener una referencia al objeto SharedPreferences
        val sharedPreferences: SharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        // Editor para realizar cambios en SharedPreferences
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        // Almacenar un valor en SharedPreferences
        editor.putString("lectura", opcion)
        editor.putBoolean("esPrimeraVez", true)
        editor.apply() // Guardar los cambios
    }

    private fun mostrarDialogSDMI() {
        val builder = AlertDialog.Builder(this)
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_sd_mi, null)
        builder.setView(dialogView)
        val dialog = builder.create()
        val btnSD: Button = dialogView.findViewById(R.id.btnSD)
        btnSD.setOnClickListener {
            savePreferences("SD")
            dialog.dismiss()
        }
        val btnMI: Button = dialogView.findViewById(R.id.btnMI)
        btnMI.setOnClickListener {
            savePreferences("Memoria Interna")
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                // Acción para el elemento de agregar
                mostrarDialog()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    fun mostrarDialog() {

        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_calendario, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        val dialog = builder.create()

        val btnAgregar: Button = dialogView.findViewById(R.id.btnGuardar)
        btnAgregar.setOnClickListener {
            agregarJson(dialogView)
            adapter.notifyDataSetChanged()
        }

        val btnCerrar: Button = dialogView.findViewById(R.id.btnCerrar)
        btnCerrar.setOnClickListener {
            dialog.dismiss() // Cierra el diálogo cuando se hace clic en el botón "Cerrar"
        }
        dialog.show()
    }

    fun agregarJson(dialogView: View) {

        val et1: EditText = dialogView.findViewById(R.id.etNombre)
        val et2: EditText = dialogView.findViewById(R.id.etDescripcion)
        val calendarView: CalendarView = dialogView.findViewById(R.id.calendarView)

        val nombre = et1.text.toString()
        val descripcion = et2.text.toString()
        var fecha : String = ""

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(Calendar.YEAR, year)
            selectedCalendar.set(Calendar.MONTH, month)
            selectedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            fecha = dateFormat.format(selectedCalendar.time)
        }
        val evento = Evento(nombre, descripcion, fecha)
        val mypref: SharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val lectura: String? = mypref.getString("lectura", "")

        if (lectura == "SD") {
            val file = File(getExternalFilesDir(null), "eventosSD.json")

            listaDeEventos.add(evento.nombre)
            adapter.notifyDataSetChanged()
            listaJSONEventos.add(evento)

            val gson = Gson()
            val eventosJson = gson.toJson(listaJSONEventos)

            try {
                val outputStream = FileOutputStream(file)
                outputStream.write(eventosJson.toByteArray())
                outputStream.close()
                Toast.makeText(this, "Evento guardado exitosamente", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this, "Error al guardar el evento", Toast.LENGTH_SHORT)
                    .show()
            }

        }
        if (lectura == "Memoria Interna") {
            val gson = Gson() // Instancia de Gson
            try {
                listaDeEventos.add(evento.nombre)
                adapter.notifyDataSetChanged()
                listaJSONEventos.add(evento)

                val gson = Gson()
                val eventosJson = gson.toJson(listaJSONEventos)

                val outputStreamWriter = OutputStreamWriter(openFileOutput("eventosMI.json", MODE_PRIVATE))
                outputStreamWriter.write(eventosJson)
                outputStreamWriter.close()

                Toast.makeText(this, "Evento guardado exitosamente", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this, "Error al guardar el evento", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun leerArchivoMI() {
        val filename = "eventosMI.json"

        try {
            val fileInputStream = openFileInput(filename)
            val inputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            val contenido = StringBuilder()
            var linea: String? = bufferedReader.readLine()
            while (linea != null) {
                contenido.append(linea)
                linea = bufferedReader.readLine()
            }
            bufferedReader.close()

            val gson = Gson()
            val listaEventos = gson.fromJson<List<Evento>>(contenido.toString(), object : TypeToken<List<Evento>>() {}.type)

            for (evento in listaEventos) {
                listaDeEventos.add(evento.nombre)
                listaJSONEventos.add(evento)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun leerArchivoSD() {
        val file = File(getExternalFilesDir(null), "eventosSD.json")
        try {
            val bufferedReader = BufferedReader(FileReader(file))
            val stringBuilder = StringBuilder()
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
            bufferedReader.close()

            // Convierte la cadena JSON a un objeto Evento usando Gson
            val gson = Gson()
            val listaEventos = gson.fromJson<List<Evento>>(stringBuilder.toString(), object : TypeToken<List<Evento>>() {}.type)

            for (evento in listaEventos) {
                listaDeEventos.add(evento.nombre)
                listaJSONEventos.add(evento)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun listViewExample() {

        val listView: ListView = findViewById(R.id.lv1)

        // Crea un ArrayAdapter para mostrar los nombres en el ListView
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listaDeEventos)

        listView.adapter = adapter

        listView.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val eventoSelected = listaDeEventos[position]
                callActivity2(eventoSelected)
            }
        }
    }
    fun callActivity2(evento : String){
        // Crear un Intent para iniciar la Activity
        val intent = Intent(this, MainActivity2::class.java)
        lateinit var eventoSelected: Evento

        for ( evento2 in listaJSONEventos){
            if (evento2.nombre == evento) {
                eventoSelected = evento2
            }
        }
        if(eventoSelected != null){
            // Agrega los datos del evento como extras en el Intent
            intent.putExtra("nombre", eventoSelected.nombre)
            intent.putExtra("descripcion", eventoSelected.descripcion)
            intent.putExtra("fecha", eventoSelected.fecha)
        }
        else{
            // Agrega los datos del evento como extras en el Intent en caso de que no sea nulo el evento
            intent.putExtra("nombre", "No posee datos")
            intent.putExtra("descripcion", "No posee datos")
            intent.putExtra("fecha", "No posee datos")
        }
        startActivity(intent)
    }
}