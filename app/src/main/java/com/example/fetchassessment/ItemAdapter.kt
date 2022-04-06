package com.example.fetchassessment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(private val dataSet: List<Items>, context: Context) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    init{ println("ITEM ADAPTER INSTANTIATED") }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.name)
        var listId: TextView = itemView.findViewById(R.id.listId)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        println("ON CREATE VIEW-HOLDER---------------------------------")
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        println("ON BIND VIEW-HOLDER---------------------------------")
        val givenName: String? = dataSet[position].name
        val givenId: Int = dataSet[position].listId

        val nameString: String = "Name: %s"
        val idString: String = "List: %d"

        holder.name.text = nameString.format(givenName)
        holder.listId.text = idString.format(givenId)
    }

    override fun getItemCount(): Int {
        println("GET ITEM COUNT---------------------------------")
        return dataSet.size
    }
}