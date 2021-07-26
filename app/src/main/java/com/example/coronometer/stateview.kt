package com.example.coronometer

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.stateview.*
import java.text.NumberFormat
import java.util.*
import kotlin.properties.Delegates

class stateview : AppCompatActivity(), GetRawData.OnDownLoadComplete,
    GetJsonData.OnDataAvailable{
    var TAG="stateView"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.stateview)
        var url=intent.getStringExtra("url")
        Log.d(TAG,"Enteres stateView with $url")
        var getRawData = GetRawData(this)
        getRawData.execute(url)
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
        val feedAdapter = FeedAdapter(
            this,
            R.layout.singlestate,
            data
        )   //CustomAdapter
        jsonListView.adapter = feedAdapter

    }

    override fun onError(exception: Exception) {
        TODO("Not yet implemented")
        Log.e(TAG, "onError called ")
    }
}

