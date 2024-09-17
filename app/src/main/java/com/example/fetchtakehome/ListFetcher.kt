package com.example.fetchtakehome
import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import org.json.JSONArray
import org.json.JSONException

interface FetchDataListener {
    fun onDataFetched(itemsMap: MutableMap<Int, MutableList<ListItem>>)
    fun onError(error: VolleyError)
}

/*
     Logic on how to fetch the item from the api
     Filtering logic
 */
class ListFetcher(private val context: Context){

    fun fetchItems(listener: FetchDataListener){
        val url = "https://fetch-hiring.s3.amazonaws.com/hiring.json"

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response: JSONArray ->
                val itemsMap: MutableMap<Int, MutableList<ListItem>> = HashMap()

                for (i in 0 until response.length()){
                    try {
                        val jsonObject = response.getJSONObject(i)
                        val id = jsonObject.getInt("id")
                        val listId = jsonObject.getInt("listId")
                        val name = jsonObject.getString("name")

                        // Skip item if its name is empty
                        if (name.isNullOrBlank() || name.equals("null", ignoreCase = true)) continue

//                        Log.d("Volley","Item $i: id=$id, name=$name")

                        val item = ListItem(id, listId, name)

                        if (itemsMap.containsKey(listId)){
                            itemsMap[listId]?.add(item)
                        } else {
                            itemsMap[listId] = mutableListOf(item)
                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

                listener.onDataFetched(itemsMap)
            },

            { error: VolleyError ->
                // TODO: Handle error
                Log.e("Volley", "Error: ${error.message}")
                listener.onError(error)
            }

        )

        // Add request to the requestQueue
        VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest)
    }

}