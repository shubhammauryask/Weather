package com.example.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.weather.databinding.ActivityMainBinding
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreference: SharedPreferenceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreference = SharedPreferenceManager(this)

        showData(sharedPreference.getData("city")!!)

        binding.btnSearch.setOnClickListener {
            showData(binding.cityName.text!!.toString())
        }
    }

    private fun showData(cityName : String){
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://weather-by-api-ninjas.p.rapidapi.com/v1/weather?city=$cityName")
            .addHeader("X-RapidAPI-Key", "087909d616msh4cc0e5423dc421ap1bee57jsn7a0f09b65c2a")
            .addHeader("X-RapidAPI-Host", "weather-by-api-ninjas.p.rapidapi.com")
            .build()
        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@MainActivity, e.message.toString(), Toast.LENGTH_LONG).show()
                }
            }
            override fun onResponse(call: Call, response: Response) {
                val responseBody = JSONObject(response.body!!.string())
                runOnUiThread {
                    Log.d("Chandan", responseBody.toString())
                    binding.city.text = cityName
                    binding.humidity.text = "Humidity : ${responseBody.getInt("humidity")} %"
                    binding.tvWind.text = "Wind : ${responseBody.getDouble("wind_speed")} km/h"
                    binding.tempshow.text = responseBody.getInt("temp").toString()
                    sharedPreference.saveData("city", cityName)
                    Log.d("Chandan", sharedPreference.getData("city")!!)
                    binding.cityName.text!!.clear()
                }
            }
        })
    }
}