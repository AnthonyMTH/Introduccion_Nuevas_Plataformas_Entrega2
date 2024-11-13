package com.example.myapplication.viewmodels

import android.content.Context
import android.graphics.RectF
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.models.Ambiente
import com.opencsv.CSVReader
import java.io.InputStreamReader

class EdificioViewModel : ViewModel() {

    private val _ambientes = MutableLiveData<List<Ambiente>>()
    val ambientes: LiveData<List<Ambiente>> get() = _ambientes

    fun cargarDatosDesdeCSV(context: Context, archivo: String) {
        val listaAmbientes = mutableListOf<Ambiente>()

        // Abre el archivo CSV desde los assets
        context.assets.open(archivo).use { inputStream ->
            val reader = CSVReader(InputStreamReader(inputStream))

            // Leer cada fila del CSV
            var line = reader.readNext() // Ignorar la cabecera
            while (reader.readNext().also { line = it } != null) {
                val nombre = line[0]
                val x1 = line[1].toFloat()
                val y1 = line[2].toFloat()
                val x2 = line[3].toFloat()
                val y2 = line[4].toFloat()
                val description = line[5]

                // Crear el objeto RectF usando los vértices
                val rect = RectF(x1, y1, x2, y2)
                listaAmbientes.add(Ambiente(nombre, rect, description))
            }
        }

        // Publicar la lista en el LiveData
        _ambientes.postValue(listaAmbientes)
    }
}