package com.example.fetchtakehome
import android.annotation.SuppressLint
import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class VolleySingleton private constructor(context: Context){
    private var requestQueue: RequestQueue? = null

    init {
        ctx = context.applicationContext
        requestQueue = getRequestQueue()
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var instance: VolleySingleton? = null
        @SuppressLint("StaticFieldLeak")
        private var ctx: Context? = null

        @Synchronized
        fun getInstance(context: Context): VolleySingleton {
            if (instance == null) {
                instance = VolleySingleton(context)
            }
            return instance!!
        }
    }

    fun getRequestQueue(): RequestQueue {
        if (requestQueue == null) {
            // Use the application context to avoid leaking the Activity or BroadcastReceiver
            requestQueue = Volley.newRequestQueue(ctx)
        }
        return requestQueue!!
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        getRequestQueue().add(req)
    }
}