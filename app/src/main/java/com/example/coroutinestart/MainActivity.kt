package com.example.coroutinestart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
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
            with(binding) {
                buttonLoad.isEnabled = false
                progress.visibility = View.VISIBLE
                val cityJob = lifecycleScope.launch {
                    val city = loadCity()
                    tvCity.text = city
                }
                val tempJob = lifecycleScope.launch {
                    val temp = loadTemperature()
                    tvTemperature.text = temp.toString()
                }
                lifecycleScope.launch {
                    cityJob.join()
                    tempJob.join()
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