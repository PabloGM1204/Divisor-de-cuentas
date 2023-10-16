package com.example.calcularprecios

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.calcularprecios.databinding.ActivityMainBinding
import java.text.NumberFormat
import java.util.zip.Inflater

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Para que se ponga por defecto
        binding.radioGroup.check(R.id.rb_servicio_malo)
        binding.radioGroup.setOnCheckedChangeListener{_, i ->
            binding.cantidadTotal.text = formateo(CalcularIncremento(i))
        }

        // Para configurar los valores del number picker
        val picker1 = binding.numPicker
        picker1.maxValue = 10
        picker1.minValue = 1
        picker1.wrapSelectorWheel = false

        // Cuando le demos al botond de calcular
        binding.botonCalcular.setOnClickListener{
            val rbSeleccionado = binding.radioGroup.checkedRadioButtonId
            var incremento = CalcularIncremento(rbSeleccionado)
            incremento /= binding.numPicker.value
            binding.pagarPersona.text = formateo(incremento)
        }
    }

    // Formatea para que sea una moneda dependiendo del los ajustes locales
    private fun formateo(cuenta: Double): String {
        val formateado = NumberFormat.getCurrencyInstance().format(cuenta)
        return formateado
    }

    // Calcula el incremento
    private fun CalcularIncremento(i: Int): Double {
        val incremento = when (i) {
            R.id.rb_servicio_malo -> {
                1.0
            }

            R.id.rb_servicio_normal -> {
                1.1
            }

            else -> {
                1.2
            }
        }
        val cuentaAsString = binding.cuenta.text.toString()
        var cuenta = cuentaAsString.toDouble()
        cuenta *= incremento
        return cuenta
    }


}