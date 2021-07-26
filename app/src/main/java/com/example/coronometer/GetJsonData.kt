package com.example.coronometer

import android.os.AsyncTask
import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception

class GetJsonData(private val listener: OnDataAvailable) :
    AsyncTask<String, Void, ArrayList<State>>() {
    private val TAG = "GetJsonData"
    val stateCode = listOf<String>(
        "an", "ap", "ar", "as", "br", "ch", "ct", "dd", "dl", "dn", "ga", "gj", "hp",
        "hr", "jh", "jk", "ka", "kl", "la", "ld", "mh", "ml", "mn", "mp", "mz", "nl",
        "or", "pb", "py", "rj", "sk", "tg", "tn", "tr", "tt", "un", "up", "ut", "wb"
    )

    interface OnDataAvailable {
        fun onDataAvailable(data: ArrayList<State>) {}
        fun onError(exception: Exception)
    }

    override fun onPostExecute(result: ArrayList<State>) {
        Log.d(TAG, "onPostExecute starts")
        super.onPostExecute(result)
        listener.onDataAvailable(result)
        Log.d(TAG, "onPost execute ends")
    }

    override fun doInBackground(vararg params: String?): ArrayList<State> {
        Log.d(TAG, "doInBackground starts")
        val stateList = ArrayList<State>()
        try {
            for (i in 0 until 39) {
                stateList.add(State(stateCode[i], 0, 0, 0,0,0,0))
            }
            val jsonData = JSONObject(params[0])
            val itemArray = jsonData.getJSONArray("states_daily")
            for (i in 0 until itemArray.length()) {
                val jsonStates = itemArray.getJSONObject(i)
                when(jsonStates.getString("status")) {
                    "Confirmed" -> {
                        for (j in 0 until 39) {
                            stateList[j].confirmed =
                                stateList[j].confirmed + jsonStates.getString(stateCode[j]).toInt()
                            stateList[j].latestConf=jsonStates.getString(stateCode[j]).toInt()
                        }
                    }
                    "Recovered" -> {
                        for (j in 0 until 39) {
                            stateList[j].recovered =
                                stateList[j].recovered + jsonStates.getString(stateCode[j]).toInt()
                            stateList[j].latestRec=jsonStates.getString(stateCode[j]).toInt()
                        }

                    }
                    else -> {
                        for (j in 0 until 39) {
                            stateList[j].death =
                                stateList[j].death + jsonStates.getString(stateCode[j]).toInt()
                            stateList[j].latestDeath=jsonStates.getString(stateCode[j]).toInt()
                        }
                    }
                }
            }

        } catch (e: JSONException) {
            e.printStackTrace()
            Log.e(TAG, "doInBackground :Error processing JsonData ${e.message}")
            listener.onError(e)
        }
        Log.d(TAG, "doInBackground ends")
        return stateList
    }
}