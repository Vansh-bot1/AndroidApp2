package com.example.coronometer

import android.os.AsyncTask
import android.util.Log
import java.io.IOException
import java.lang.Exception
import java.net.MalformedURLException
import java.net.URL

enum class DownloadStatus {
    OK, IDLE, NOT_INITIALISED, FAILED_OR_EMPTY, PERMISSIONS_ERROR, ERROR
}

class GetRawData(var listener: OnDownLoadComplete):AsyncTask<String,Void,String>() {
    private val TAG="GetRawData"
    private var downloadStatus = DownloadStatus.IDLE

    interface OnDownLoadComplete {
        fun onDownloadComplete(data: String, status: DownloadStatus)
    }
    override fun onPostExecute(result: String) {
        Log.d(TAG, "OnPost called with $result")
        listener.onDownloadComplete(result, downloadStatus)
    }

    override fun doInBackground(vararg params: String?): String {
        if (params[0] == null) {
            downloadStatus = DownloadStatus.NOT_INITIALISED
            return "No Url Specified"
        }
        try {
            downloadStatus = DownloadStatus.OK
            return URL(params[0]).readText()
        } catch (e: Exception) {
            val errorMessage = when (e) {
                is MalformedURLException -> {
                    downloadStatus = DownloadStatus.NOT_INITIALISED
                    "doInBackGround: Invalid Url ${e.message}"
                }
                is IOException -> {
                    downloadStatus = DownloadStatus.FAILED_OR_EMPTY
                    "doInBackGround: IOException ${e.message}"
                }
                is SecurityException -> {
                    downloadStatus = DownloadStatus.PERMISSIONS_ERROR
                    "doInBackGround: Security ${e.message}"
                }
                else -> {
                    downloadStatus = DownloadStatus.ERROR
                    "Unknown Error ${e.message}"
                }
            }
            Log.e(TAG, errorMessage)
            return errorMessage
        }
    }
}