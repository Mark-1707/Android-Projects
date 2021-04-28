package com.mark.memeshare

import android.app.admin.FactoryResetProtectionPolicy
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.Response.Listener
import com.android.volley.Response.error
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    var currentImageUrl : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadMeme()

        findViewById<Button>(R.id.next_btn).setOnClickListener {
            nextMeme()
        }

        findViewById<Button>(R.id.share_btn).setOnClickListener {
            shareMeme()
        }
    }

    private fun loadMeme(){
        findViewById<ProgressBar>(R.id.progress_bar).visibility = View.VISIBLE
        // Instantiate the RequestQueue.
        val url = "https://meme-api.herokuapp.com/gimme"

        // Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                currentImageUrl = response.getString("url")

                Glide.with(this).load(currentImageUrl).listener(object : RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        findViewById<ProgressBar>(R.id.progress_bar).visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        findViewById<ProgressBar>(R.id.progress_bar).visibility = View.GONE
                        return false
                    }
                }).into(findViewById(R.id.memeImageView))

                findViewById<Button>(R.id.share_btn).isEnabled = true
                findViewById<Button>(R.id.next_btn).isEnabled = true

            },
            { error ->
                Toast.makeText(applicationContext,"Failed to Load",Toast.LENGTH_SHORT).show()
                findViewById<ProgressBar>(R.id.progress_bar).visibility = View.GONE
            }
        )

    // Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    fun shareMeme(){
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey Buddy, Checkout this Cool Meme $currentImageUrl")
        val chooser = Intent.createChooser(intent,"Share this Meme")
        startActivity(chooser)
    }

    fun nextMeme(){
        loadMeme()
    }
}