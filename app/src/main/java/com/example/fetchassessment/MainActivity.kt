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

    private lateinit var adapter : RecyclerView.Adapter<ItemAdapter.ViewHolder>
    private lateinit var recyclerView: RecyclerView

    private fun customFilter(itemList: ArrayList<Items>): ArrayList<Items> {
        val newItemList = ArrayList<Items>()
        for (item in itemList) {
            if (item.name == "null" || item.name == "") continue
            newItemList.add(item)
        }
        return newItemList
    }

    private fun recyclerViewInit(dataSet: List<Items>) {
        println("RECYCLER VIEW INIT CALLED ---------------------------------")
        recyclerView = findViewById(R.id.rv_item_list)
        adapter = ItemAdapter(dataSet)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun httpRequest() {
        val url = "https://fetch-hiring.s3.amazonaws.com/hiring.json"

        try {
            val req = url.httpGet().responseString { _, _, result ->

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

                val sortedByListId = customFilter(itemList).sortedWith(compareBy({ it.listId }, { it.id }))

                recyclerViewInit(sortedByListId)
            }

            req.join()

        } catch (e: Exception) {println(e)}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println("ONCREATE CALLED---------------------------------")

        httpRequest()
    }
}

