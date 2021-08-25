package com.example.filter2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class reAdapter(val context: Context, val list:ArrayList<DataModel>)
    : RecyclerView.Adapter<reAdapter.ViewHolder>(), Filterable {

    var countryFilterList = ArrayList<DataModel>()

    init {
        countryFilterList = list
    }

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
                as LayoutInflater

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)  {
        val textView: TextView = view.findViewById(R.id.text1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): reAdapter.ViewHolder {
        val rowList = inflater.inflate(R.layout.list, parent, false)
        return ViewHolder(rowList)
    }

    override fun onBindViewHolder(holder: reAdapter.ViewHolder, position: Int) {
        holder.textView.text=countryFilterList.get(position).ststock
    }

    override fun getItemCount(): Int {
        return countryFilterList.size
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    countryFilterList = list
                } else {
                    val resultList = ArrayList<DataModel>()
                    for (row in list) {
                        if (row.ststock.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    countryFilterList = resultList

                }
                val filterResults = FilterResults()
                filterResults.values = countryFilterList
                return filterResults
            }


            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                countryFilterList = results?.values as ArrayList<DataModel>
                notifyDataSetChanged()
            }

        }
    }
}