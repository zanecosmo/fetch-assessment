package com.example.fetchassessment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(private val dataSet: List<Items>) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    init{ println("ITEM ADAPTER INSTANTIATED") }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val listId: TextView = itemView.findViewById(R.id.listId)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
        println("ON CREATE VIEW-HOLDER---------------------------------")
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.item_list_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        println("ON BIND VIEW-HOLDER---------------------------------")
        val givenName: String? = dataSet[position].name
        val givenId: Int = dataSet[position].listId

        val nameString = "NAME:   %s"
        val idString = "LIST:   %d"

        holder.name.text = nameString.format(givenName)
        holder.listId.text = idString.format(givenId)
    }

    override fun getItemCount() : Int {
        val size = dataSet.size
        println("$size---------------------------------")
        return size
    }
}