package com.example.lightsensor

import android.content.Context
import android.hardware.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.lightsensor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var brightness: Sensor
    private lateinit var sensorManager: SensorManager
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setUpSensorStuff()
    }
    private fun brightness(brightness: Float) : String{
        return when(brightness.toInt()){
            0 -> "Pitch black"
            in 1..10 -> "Dark"
            in 11..50 -> "Grey"
            in 51..5000 -> "Normal"
            in 5001..10000 -> "Bright"
            in 10001..20000 -> "Incredibly bright"
            else -> "This light will blind you"
        }
    }
    private fun setUpSensorStuff(){
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        brightness = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }


    override fun onSensorChanged(event: SensorEvent?) {
        if(event?.sensor?.type == Sensor.TYPE_LIGHT){
            val light =event.values[0]
            binding.txtTv.text= "Sensor: $light\n${brightness(light)}"
            binding.circularProgressBar.setProgressWithAnimation(light)
        }

    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this,brightness,SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

}