package com.example.filter2

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: reAdapter
    lateinit var editText: EditText
    var i: Int = 0
    var list: ArrayList<DataModel> = ArrayList<DataModel>()
    val url: String = "https://maxgenitsolutions.in/stock/apistockview?category=intraday"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerview)
        editText = findViewById(R.id.edit1)
        editText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                adapter.filter.filter(s)

            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })

        val request: StringRequest =
            StringRequest(Request.Method.GET, url, Response.Listener {
                    response ->
                Log.e("response>>>>",response+"")
                val ja: JSONObject = JSONObject(response)
                val jb: JSONArray =ja.getJSONArray("list")
                for(i in 0 until jb.length()) {
                    val jsonobject: JSONObject = jb.getJSONObject(i)
                    val ststock: String = jsonobject.getString("ststock")

                    val dataModel = DataModel()
                    dataModel.ststock = ststock
                    list.add(dataModel)
                }


                adapter= reAdapter(this,list)
                val layoutManager= LinearLayoutManager(this)
                recyclerView.layoutManager=layoutManager
                recyclerView.adapter=adapter

            }, Response.ErrorListener {
            })

        val queque: RequestQueue = Volley.newRequestQueue(this)
        queque.add(request)
    }

}

