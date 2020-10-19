package com.uso.guia7

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    companion object{
        val KEY_DATA_JUEGO = "com.uso.guia7.KEY_DATA_JUEGO"
        val KEY_JUGADOR = "com.uso.guia7.KEY_JUGADOR"
        val KEY_PUNTAJE = "com.uso.guia7.KEY_PUNTAJE"
    }

    lateinit var buttonJugar: Button
    lateinit var edittextJugador: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        cargarJugador()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edittextJugador = findViewById(R.id.edittextJugador)
        buttonJugar = findViewById(R.id.buttonJugar)

        buttonJugar.setOnClickListener{
            openReglas()
        }
    }

    private fun openReglas(){
        if(edittextJugador.text.isNotEmpty()){
            val intent = Intent(baseContext, RulesActivity::class.java)
            intent.putExtra(KEY_JUGADOR, edittextJugador.text)
            startActivity(intent)
        }else{
            edittextJugador.error = "Debe asignar el nombre de jugador"
        }
    }

    private fun cargarJugador(){
        val prefs:SharedPreferences = getSharedPreferences(KEY_DATA_JUEGO, MODE_PRIVATE)
        val jugador = prefs.getString(KEY_JUGADOR, null)
        if(jugador != null){
            val intent = Intent(baseContext, GameActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}