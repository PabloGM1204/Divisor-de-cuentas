package com.example.calcularprecios

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.calcularprecios.databinding.ActivityMainBinding
import java.text.NumberFormat
import java.util.Currency;
import java.util.Locale;
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Para que se ponga por defecto
        binding.cantidadTotal.text = getString(R.string.total_a_pagar, "")
        // binding.radioGroup.check(R.id.rb_servicio_malo)
        binding.radioGroup.setOnCheckedChangeListener{_, i ->
            binding.cantidadTotal.text = getString(R.string.total_a_pagar, formateo(CalcularIncremento(i)))
        }

        var ctL: Locale = Locale.getDefault()
        var moneda: Currency = Currency.getInstance(ctL)
        binding.cuenta.suffixText = moneda.getSymbol(ctL)


        // Para configurar los valores del number picker
        val picker1 = binding.numPicker
        picker1.maxValue = 10
        picker1.minValue = 1
        picker1.wrapSelectorWheel = false



        var incremento = 0.0
        // Cuando le demos al botond de calcular
        binding.botonCalcular.setOnClickListener{
            val rbSeleccionado = binding.radioGroup.checkedRadioButtonId
            incremento = CalcularIncremento(rbSeleccionado)
            incremento /= binding.numPicker.value
            // Para redondear hacia la alta
            incremento = redondeo(incremento)
            binding.pagarPersona.text = getString(R.string.split_result, formateo(incremento))
        }

    }

    // Para redondear a la alta
    private fun redondeo(incremento: Double): Double {
        var incremento1 = incremento
        if (binding.check.isChecked) {
            incremento1 = ceil(incremento1)
        }
        return incremento1
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
        val cuentaAsString = binding.cantidadPagar.text.toString()
        var cuenta = cuentaAsString.toDouble()
        cuenta *= incremento
        return cuenta
    }


}