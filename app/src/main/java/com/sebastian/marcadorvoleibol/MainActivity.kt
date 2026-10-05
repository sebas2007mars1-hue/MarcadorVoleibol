
package com.sebastian.marcadorvoleibol

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Variables del marcador
    private var puntosEquipo1 = 0
    private var puntosEquipo2 = 0
    private var partidoIniciado = false
    private var setTerminado = false

    // Campos de los nombres
    private lateinit var edtEquipo1: EditText
    private lateinit var edtEquipo2: EditText

    // Textos de la pantalla
    private lateinit var txtEquipo1: TextView
    private lateinit var txtEquipo2: TextView
    private lateinit var txtPuntosEquipo1: TextView
    private lateinit var txtPuntosEquipo2: TextView
    private lateinit var txtResultado: TextView

    // Botones
    private lateinit var btnIniciar: Button
    private lateinit var btnPuntoEquipo1: Button
    private lateinit var btnPuntoEquipo2: Button
    private lateinit var btnRestarEquipo1: Button
    private lateinit var btnRestarEquipo2: Button
    private lateinit var btnNuevoSet: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Conectar campos
        edtEquipo1 = findViewById(R.id.edtEquipo1)
        edtEquipo2 = findViewById(R.id.edtEquipo2)

        // Conectar textos
        txtEquipo1 = findViewById(R.id.txtEquipo1)
        txtEquipo2 = findViewById(R.id.txtEquipo2)
        txtPuntosEquipo1 = findViewById(R.id.txtPuntosEquipo1)
        txtPuntosEquipo2 = findViewById(R.id.txtPuntosEquipo2)
        txtResultado = findViewById(R.id.txtResultado)

        // Conectar botones
        btnIniciar = findViewById(R.id.btnIniciar)
        btnPuntoEquipo1 = findViewById(R.id.btnPuntoEquipo1)
        btnPuntoEquipo2 = findViewById(R.id.btnPuntoEquipo2)
        btnRestarEquipo1 = findViewById(R.id.btnRestarEquipo1)
        btnRestarEquipo2 = findViewById(R.id.btnRestarEquipo2)
        btnNuevoSet = findViewById(R.id.btnNuevoSet)

        // Estado inicial
        habilitarBotones(false)
        btnNuevoSet.isEnabled = false

        // Iniciar partido
        btnIniciar.setOnClickListener {
            val nombre1 = edtEquipo1.text.toString().trim()
            val nombre2 = edtEquipo2.text.toString().trim()

            if (nombre1.isBlank() || nombre2.isBlank()) {
                txtResultado.text =
                    "Ingresa los nombres de ambos equipos"
            } else {
                txtEquipo1.text = nombre1
                txtEquipo2.text = nombre2

                puntosEquipo1 = 0
                puntosEquipo2 = 0
                partidoIniciado = true
                setTerminado = false

                actualizarMarcador()
                txtResultado.text = "Partido iniciado"

                edtEquipo1.isEnabled = false
                edtEquipo2.isEnabled = false
                btnIniciar.isEnabled = false

                habilitarBotones(true)
                btnNuevoSet.isEnabled = false
            }
        }

        // Sumar punto al equipo 1
        btnPuntoEquipo1.setOnClickListener {
            if (partidoIniciado && !setTerminado) {
                puntosEquipo1++
                actualizarMarcador()
                verificarGanador()
            }
        }

        // Sumar punto al equipo 2
        btnPuntoEquipo2.setOnClickListener {
            if (partidoIniciado && !setTerminado) {
                puntosEquipo2++
                actualizarMarcador()
                verificarGanador()
            }
        }

        // Restar punto al equipo 1
        btnRestarEquipo1.setOnClickListener {
            if (partidoIniciado && !setTerminado &&
                puntosEquipo1 > 0
            ) {
                puntosEquipo1--
                actualizarMarcador()
            }
        }

        // Restar punto al equipo 2
        btnRestarEquipo2.setOnClickListener {
            if (partidoIniciado && !setTerminado &&
                puntosEquipo2 > 0
            ) {
                puntosEquipo2--
                actualizarMarcador()
            }
        }

        // Iniciar nuevo set
        btnNuevoSet.setOnClickListener {
            if (partidoIniciado && setTerminado) {
                puntosEquipo1 = 0
                puntosEquipo2 = 0
                setTerminado = false

                actualizarMarcador()
                txtResultado.text = "Nuevo set iniciado"

                habilitarBotones(true)
                btnNuevoSet.isEnabled = false
            }
        }
    }

    // Actualizar el marcador
    private fun actualizarMarcador() {
        txtPuntosEquipo1.text = puntosEquipo1.toString()
        txtPuntosEquipo2.text = puntosEquipo2.toString()
    }

    // Verificar si algún equipo ganó
    private fun verificarGanador() {
        if (puntosEquipo1 >= 25) {
            setTerminado = true
            txtResultado.text =
                "SET TERMINADO\nGanador: ${txtEquipo1.text}"

            habilitarBotones(false)
            btnNuevoSet.isEnabled = true

        } else if (puntosEquipo2 >= 25) {
            setTerminado = true
            txtResultado.text =
                "SET TERMINADO\nGanador: ${txtEquipo2.text}"

            habilitarBotones(false)
            btnNuevoSet.isEnabled = true
        }
    }

    // Habilitar o deshabilitar los botones de puntuación
    private fun habilitarBotones(habilitar: Boolean) {
        btnPuntoEquipo1.isEnabled = habilitar
        btnPuntoEquipo2.isEnabled = habilitar
        btnRestarEquipo1.isEnabled = habilitar
        btnRestarEquipo2.isEnabled = habilitar
    }
}