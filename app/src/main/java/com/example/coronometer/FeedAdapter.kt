package com.example.coronometer

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class ViewHolder(v: View) {
    val stateName: TextView = v.findViewById(R.id.stateName)
    val caseConf: TextView = v.findViewById(R.id.caseConf)
    val caseRec: TextView = v.findViewById(R.id.caseRec)
    val caseDeath: TextView = v.findViewById(R.id.caseDeath)
}

class FeedAdapter(
    context: Context,
    private val resource: Int,
    private val states: ArrayList<State>
) :
    ArrayAdapter<State>(context, resource) {
    private val inflater = LayoutInflater.from(context)
    var stateName = listOf(
        "Andaman & Nicobar Islands",
        "Andhra Pradesh",
        "Arunachal Pradesh",
        "Assam",
        "Bihar",
        "Chandigarh"
        ,
        "Chhattisgarh",
        "dd",
        "Delhi",
        "Dadra and Nagar Haveli and Daman and Diu",
        "Goa",
        "Gujarat",
        "Himachal Pradesh",
        "Haryana",
        "Jharkhand",
        "Jammu and Kashmir"
        ,
        "Karnataka",
        "Kerala",
        "Ladakh",
        "Ld",
        "Maharashtra",
        "Meghalaya",
        "Manipur",
        "Madhya Pradesh",
        "Mizoram",
        "Nagaland",
        "Odisha"
        ,
        "Punjab",
        "Puducherry",
        "Rajhasthan",
        "Sikkim",
        "Telangana",
        "Tamil Nadu",
        "Tripura",
        "Total",
        "un",
        "Uttar Pradesh",
        "Uttarakhand",
        "West Bengal"
    )

    override fun getCount(): Int {
        return states.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        Log.d("Adapter", "$position called")
        val view: View
        val viewHolder: ViewHolder
        if (convertView == null) {
            view = inflater.inflate(resource, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        val currentState = states[position]
        viewHolder.stateName.text = (position+1).toString() + "." + stateName[position]
        viewHolder.caseConf.text = NumberFormat.getNumberInstance(Locale.US)
            .format(currentState.confirmed) + "\n" + "(+" + NumberFormat.getNumberInstance(Locale.US)
            .format(currentState.latestConf) + ")"
        viewHolder.caseRec.text = NumberFormat.getNumberInstance(Locale.US)
            .format(currentState.recovered) + "\n" + "(+" + NumberFormat.getNumberInstance(Locale.US)
            .format(currentState.latestRec) + ")"
        viewHolder.caseDeath.text = NumberFormat.getNumberInstance(Locale.US)
            .format(currentState.death) + "\n" + "(+" + NumberFormat.getNumberInstance(Locale.US)
            .format(currentState.latestDeath) + ")"
        return view
    }
}