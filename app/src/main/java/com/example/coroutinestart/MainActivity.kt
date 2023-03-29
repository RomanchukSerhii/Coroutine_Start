package com.example.coroutinestart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.coroutinestart.databinding.ActivityMainBinding
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLoad.setOnClickListener {
//            lifecycleScope.launch {
//                loadData()
//            }
            loadWithoutCoroutine()
        }
    }

    private fun loadWithoutCoroutine(state: Int = 0, obj: Any? = null) {
        when (state) {
            0 -> {
                binding.progress.isVisible = true
                binding.buttonLoad.isEnabled = false
                loadCityWithoutCoroutine { city ->
                    loadWithoutCoroutine(1, city)
                }
            }
            1 -> {
                val city = obj as String
                binding.tvCity.text = city
                loadTemperatureWithoutCoroutine(city) { temperature ->
                    loadWithoutCoroutine(2, temperature)
                }
            }
            2 -> {
                val temp = obj as Int
                binding.tvTemperature.text = temp.toString()
                binding.progress.isVisible = false
                binding.buttonLoad.isEnabled = true
            }
        }
    }

    private fun loadCityWithoutCoroutine(callback: (String) -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed( {
            callback.invoke("Kyiv")
        },5000)
    }

    private fun loadTemperatureWithoutCoroutine(city: String, callback: (Int) -> Unit) {
        Toast.makeText(
            this,
            String.format(getString(R.string.load_temperature), city),
            Toast.LENGTH_SHORT
        ).show()
        Handler(Looper.getMainLooper()).postDelayed({
            callback.invoke(17)
        }, 5000)
    }

    private suspend fun loadData() {
        with(binding) {
            progress.isVisible = true
            buttonLoad.isEnabled = false
            val city = loadCity()
            tvCity.text = city
            val temp = loadTemperature(city)
            tvTemperature.text = temp.toString()
            progress.isVisible = false
            buttonLoad.isEnabled = true
        }
    }

    private suspend fun loadCity(): String {
        delay(5000)
        return "Kyiv"
    }

    private suspend fun loadTemperature(city: String): Int {
        Toast.makeText(
            this,
            String.format(getString(R.string.load_temperature), city),
            Toast.LENGTH_SHORT
        ).show()
        delay(5000)
        return 17
    }
}