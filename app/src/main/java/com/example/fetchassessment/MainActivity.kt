package com.example.fetchassessment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.kittinunf.fuel.httpGet
import org.json.JSONArray
import org.json.JSONTokener
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private fun customFilter(itemList: ArrayList<Items>): ArrayList<Items> {
        val newItemList = ArrayList<Items>()
        for (item in itemList) {
            if (item.name == "null" || item.name == "") continue
            newItemList.add(item)
        }
        return newItemList
    }

    private fun recyclerViewInit(dataSet: List<Items>) : RecyclerView {
        val recyclerView: RecyclerView = findViewById(R.id.rv_item_list)
        val adapter = ItemAdapter(dataSet, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        return recyclerView
    }

    private fun httpRequest(): List<Items> {
        val url = "https://fetch-hiring.s3.amazonaws.com/hiring.json"
        var sortedByListId: List<Items> = listOf()

        try {
            url.httpGet().responseString { _, _, result ->
                val itemList: ArrayList<Items> = arrayListOf()
                val jsonArray = JSONTokener(result.get()).nextValue() as JSONArray

                for (i in 0 until jsonArray.length()) {
                    val item = jsonArray.getJSONObject(i)

                    val id: Int = item.getInt("id")
                    val listId: Int = item.getInt("listId")
                    val name: String = item.getString("name")

                    val newItem = Items(id, listId, name)

                    itemList.add(newItem)
                }

                sortedByListId = customFilter(itemList).sortedWith(compareBy({ it.listId }, { it.id }))

                recyclerViewInit(sortedByListId)
            }
        } catch (e: Exception) {println(e)}

        return sortedByListId
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var dataSet : List<Items> = httpRequest()
        val recyclerView = recyclerViewInit(dataSet)
    }
}