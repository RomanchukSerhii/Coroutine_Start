package com.example.coroutinestart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.coroutinestart.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLoad.setOnClickListener {
            loadData()
        }
    }

    private fun loadData() {
        with(binding) {
            progress.isVisible = true
            buttonLoad.isEnabled = false
            loadCity { city ->
                tvCity.text = city
                loadTemperature(city) { temp ->
                    tvTemperature.text = temp.toString()
                    progress.isVisible = false
                    buttonLoad.isEnabled = true
                }
            }
        }
    }

    private fun loadCity(callback: (String) -> Unit) {
        thread {
            Thread.sleep(5000)
            runOnUiThread {
                callback("Kyiv")
            }
        }
    }

    private fun loadTemperature(city: String, callback: (Int) -> Unit) {
        thread {
            Looper.prepare()
            Handler(Looper.myLooper()!!)
            runOnUiThread {
                Toast.makeText(
                    this,
                    String.format(getString(R.string.load_temperature), city),
                    Toast.LENGTH_SHORT
                ).show()
            }
            Thread.sleep(5000)
            runOnUiThread {
                callback(17)
            }
        }
    }
}