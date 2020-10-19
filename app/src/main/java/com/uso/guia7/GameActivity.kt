package com.uso.guia7

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import com.google.android.material.snackbar.Snackbar
import kotlin.random.Random

class GameActivity : AppCompatActivity() {
    var contadorCerrar = 0
    var random:Int = 0
    lateinit var jugador:String
    var puntaje:Int = 10
    lateinit var contextView: View
    lateinit var textviewNombreJugador:TextView
    lateinit var textviewPuntajeJugador:TextView
    lateinit var edittextNumero:EditText
    lateinit var buttonAdivinar:Button
    lateinit var buttonReanudar:Button
    lateinit var buttonRespuesta:Button
    lateinit var buttonLimpiar:Button
    lateinit var linearAdivinar:LinearLayout
    lateinit var linearContinuar:LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        cargarDatos()
        generarRandom()

        textviewNombreJugador = findViewById(R.id.textviewNombreJugador)
        textviewPuntajeJugador = findViewById(R.id.textviewPuntajeJugador)
        edittextNumero = findViewById(R.id.edittextNumero)
        buttonAdivinar = findViewById(R.id.buttonAdivinar)
        buttonReanudar = findViewById(R.id.buttonReanudar)
        buttonRespuesta = findViewById(R.id.buttonRespuesta)
        buttonLimpiar = findViewById(R.id.buttonLimpiar)
        linearAdivinar = findViewById(R.id.linearAdivinar)
        linearContinuar = findViewById(R.id.linearContinuar)

        cargarDatos()
        updateUi()

        buttonAdivinar.setOnClickListener {
            adivinar(it)
        }
        buttonReanudar.setOnClickListener {
            reanudar(it)
        }
        buttonRespuesta.setOnClickListener {
            mostrarRandom()
        }
        buttonLimpiar.setOnClickListener {
            mostrarDialog()
        }
    }

    private fun mostrarDialog(){
        val builder: AlertDialog.Builder? = this?.let {
            AlertDialog.Builder(it)
        }

        builder?.setMessage("Si continua, sus datos serán borrados y volverá a la pantalla de login, ¿desea continuar?")
            ?.setTitle("Advertencia")

        builder?.apply {
            setPositiveButton("SÍ",
                DialogInterface.OnClickListener { dialog, id ->
                    limpiarDatos()
                    openMain()
                })
            setNegativeButton("CANCELAR",
                DialogInterface.OnClickListener { dialog, id ->

                })
        }

        val dialog: AlertDialog? = builder?.create()

        builder?.show()
    }

    private fun cargarDatos(){
        val prefs = getSharedPreferences(MainActivity.KEY_DATA_JUEGO, MODE_PRIVATE)
        val valor = prefs.getString(MainActivity.KEY_JUGADOR, null)
        if(valor != null){
            jugador = valor
            puntaje = prefs.getInt(MainActivity.KEY_PUNTAJE, 0)
        }else{
            val bundle: Bundle? = intent.extras
            jugador = bundle?.getString(MainActivity.KEY_JUGADOR).toString()
        }
    }

    private fun guardarDatos(){
        limpiarDatos()

        val prefs:SharedPreferences.Editor = getSharedPreferences(
            MainActivity.KEY_DATA_JUEGO,
            MODE_PRIVATE
        ).edit()
        prefs.putString(MainActivity.KEY_JUGADOR, jugador)
        prefs.putInt(MainActivity.KEY_PUNTAJE, puntaje)
        prefs.apply()
    }

    private fun limpiarDatos(){
        val prefs:SharedPreferences.Editor = getSharedPreferences(
            MainActivity.KEY_DATA_JUEGO,
            MODE_PRIVATE
        ).edit()
        prefs.clear()
        prefs.apply()
    }

    private fun openMain(){
        val intent = Intent(baseContext, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    private fun adivinar(view: View){
        val num = edittextNumero.text.toString().toInt()

        if(num == random) {
            puntaje += 10
            val imm:InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            toggleAdivinar(false)
        }
        else {
            puntaje -= 1
            edittextNumero.text.clear()
            Toast.makeText(baseContext, "Respuesta incorrecta", Toast.LENGTH_SHORT).show()
        }

        updateUi()
    }

    private fun toggleAdivinar(mostrar:Boolean){
        if(mostrar){
            linearAdivinar.visibility = View.VISIBLE
            linearContinuar.visibility = View.GONE
        }else{
            linearAdivinar.visibility = View.GONE
            linearContinuar.visibility = View.VISIBLE
        }
    }

    private fun reanudar(view: View){
        generarRandom()
        toggleAdivinar(true)
        edittextNumero.text.clear()
        edittextNumero.requestFocus()
        val imm:InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    private fun generarRandom(){
        random = Random.nextInt(1, 10)
    }

    private fun mostrarRandom(){
        Toast.makeText(baseContext, "Número: " + random, Toast.LENGTH_SHORT).show()
    }

    private fun updateUi(){
        textviewPuntajeJugador.text = puntaje.toString()
    }

    override fun onBackPressed() {
        contadorCerrar++
        if(contadorCerrar == 2) {
            guardarDatos()
            this.finishAffinity()
        }
        else Toast.makeText(baseContext, "Pulsa otra vez para cerrar", Toast.LENGTH_SHORT).show()
    }
}