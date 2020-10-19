package com.uso.guia7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class RulesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rules)

        val bundle: Bundle? = intent.extras
        val jugador = bundle?.getString(MainActivity.KEY_JUGADOR)

        val buttonContinuar:Button = findViewById(R.id.buttonContinuar)

        buttonContinuar.setOnClickListener {
            val intent = Intent(baseContext, GameActivity::class.java)
            intent.putExtra(MainActivity.KEY_JUGADOR, jugador)
            startActivity(intent)
        }
    }
}