package com.example.coronometer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.content_main.*
import java.io.Serializable
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity(), GetRawData.OnDownLoadComplete,
    GetJsonData.OnDataAvailable {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "OnCreate called")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        val url = "https://api.covid19india.org/states_daily.json"
        var getRawData = GetRawData(this)
        getRawData.execute(url)
        stateButton.setOnClickListener{
            val intent= Intent(this,stateview::class.java)
            intent.putExtra("url",url)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDownloadComplete(data: String, status: DownloadStatus) {
        if (status == DownloadStatus.OK) {
            Log.d(TAG, "DownloadComplete with $data")
            val getJsonData = GetJsonData(this)
            getJsonData.execute(data)
        } else {
            Log.d(TAG, "Download failed with status $status with error message $data")
        }
    }

    override fun onDataAvailable(data: ArrayList<State>) {
        super.onDataAvailable(data)
        Log.d(TAG, "onDataAvailable called data $data")
        var totalConfirmed = 0
        var totalRecovered = 0
        var totalDeaths = 0
        var latConf = 0
        var latRec = 0
        var latDed = 0
        for (i in 0 until 39) {
            if (data[i].name == "tt") {
                totalConfirmed = data[i].confirmed
                totalRecovered = data[i].recovered
                totalDeaths = data[i].death
                latConf = data[i].latestConf
                latRec = data[i].latestRec
                latDed = data[i].latestDeath
                break
            }
        }
        confNo.text = NumberFormat.getNumberInstance(Locale.US).format(totalConfirmed)
        recNo.text = NumberFormat.getNumberInstance(Locale.US).format(totalRecovered)
        dedNo.text = NumberFormat.getNumberInstance(Locale.US).format(totalDeaths)
        latestConf.text = "+ " + NumberFormat.getNumberInstance(Locale.US).format(latConf)
        latestRec.text = "+ " + NumberFormat.getNumberInstance(Locale.US).format(latRec)
        latestDed.text = "+ " + NumberFormat.getNumberInstance(Locale.US).format(latDed)
    }

    override fun onError(exception: Exception) {
        TODO("Not yet implemented")
        Log.e(TAG, "onError called ")
    }
}