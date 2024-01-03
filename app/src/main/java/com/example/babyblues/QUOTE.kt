package com.example.babyblues

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QUOTE : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quote)
        val text = findViewById<TextView>(R.id.quote)
        val loading = findViewById<ProgressBar>(R.id.progressbar)


        loading.visibility = View.VISIBLE
        RetrofitApi.apiInterface.getResponse()?.enqueue(object : Callback<Arrayquote?> {
            override fun onResponse(call: Call<Arrayquote?>, response: Response<Arrayquote?>) {
                if (response.isSuccessful){
                    var quotetext = response.body()
                    if (quotetext != null) {
                        loading.visibility = View.INVISIBLE
                        text.text = quotetext[0].q
                    }
                }
            }

            override fun onFailure(call: Call<Arrayquote?>, t: Throwable) {

                val errorMessage: String = t.localizedMessage
                Log.d("ttss",errorMessage)
                Toast.makeText(this@QUOTE, "error",Toast.LENGTH_SHORT).show()

            }
        })

    }
    override fun onBackPressed() {
       finish()
    }
}