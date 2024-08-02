package com.bill.floodfilldemo

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ColoringActivity : AppCompatActivity() {

    var coloringView : MyColoringView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_coloring)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        coloringView = findViewById(R.id.coloringView)

        val uri = intent.getStringExtra("uri")
        val assetManager = assets
        val inputStream = assetManager.open("SecretGarden/${uri}")
        val bitmap = BitmapFactory.decodeStream(inputStream)
        coloringView?.setBitmap(bitmap)
    }
}