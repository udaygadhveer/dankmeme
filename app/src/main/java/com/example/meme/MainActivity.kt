 package com.example.meme

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var url: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()
    }
    private fun loadMeme(){
           // val textView = findViewById<TextView>(R.id.text)
        // ...
        progress.visibility=View.VISIBLE

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        url = "https://meme-api.herokuapp.com/gimme"


        // Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            { response ->
                url=response.getString("url")
                Toast.makeText(this, "MoJ kar Di", Toast.LENGTH_SHORT).show()
                Glide.with(this).load(url).listener(object: RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progress.visibility=View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progress.visibility=View.GONE
                        return false
                    }
                }).into(MemeImage)
            },
            { Log.d("error", it.localizedMessage) })

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }
    fun ShareMeme(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"yo here's a cool meme $url")
        val chooser = Intent.createChooser(intent,"Share this using ....")
        startActivity(chooser)
    }
    fun NextMeme(view: View) {
        loadMeme()
    }
}