package com.example.fetchtakehome
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.VolleyError

// TODO: main class implements FetchDataListener
class MainActivity : AppCompatActivity(), FetchDataListener {
    private lateinit var listFetcher: ListFetcher
    private lateinit var adapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // display listItems in recycler view
        val listRv = findViewById<RecyclerView>(R.id.listRv)

        // pass listItems to adapter so adapter can bind the data to ViewHolder
        adapter = ListAdapter(mutableListOf())

        // attach the adapter to RV
        listRv.adapter = adapter

        // set layout manager to position the items
        listRv.layoutManager = LinearLayoutManager(this)

        // Initialize ListFetcher and fetch data
        listFetcher = ListFetcher(this)
        listFetcher.fetchItems(this)

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onDataFetched(itemsMap: MutableMap<Int, MutableList<ListItem>>) {
        // Update the RecyclerView with new data
        runOnUiThread {
            val newListItems = mutableListOf<ListDisplayItem>()

            // sort the item by listID
            val sortedListIds = itemsMap.keys.sorted()

            for (listId in sortedListIds) {
                // Add the header
                newListItems.add(ListDisplayItem.Header(listId))

                // sort item by name
                val items = itemsMap[listId]?.sortedBy { it.name } ?: continue

                for (item in items) {
                    newListItems.add(ListDisplayItem.Item(item))
                }
            }

            // Update the adapter with the new list
            adapter.updateData(newListItems)
        }
    }

    override fun onError(error: VolleyError) {
        // Handle the error
        runOnUiThread {
            Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show()
        }
    }

}