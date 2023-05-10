package com.example.coroutinestart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.coroutinestart.databinding.ActivityMainBinding
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLoad.setOnClickListener {
            with(binding) {
                buttonLoad.isEnabled = false
                progress.visibility = View.VISIBLE
                val deferredCity: Deferred<String> = lifecycleScope.async {
                    val city = loadCity()
                    tvCity.text = city
                    city
                }
                val deferredTemp: Deferred<Int> = lifecycleScope.async {
                    val temp = loadTemperature()
                    tvTemperature.text = temp.toString()
                    temp
                }
                lifecycleScope.launch {
                    val city = deferredCity.await()
                    val temp = deferredTemp.await()
                    Toast.makeText(
                        this@MainActivity,
                        "City:$city Temperature:$temp",
                        Toast.LENGTH_SHORT
                    ).show()
                    progress.visibility = View.GONE
                    buttonLoad.isEnabled = true
                }
            }
        }
    }

    private suspend fun loadCity(): String {
        delay(5000)
        return "Kyiv"
    }

    private suspend fun loadTemperature(): Int {
        delay(5000)
        return 17
    }


}