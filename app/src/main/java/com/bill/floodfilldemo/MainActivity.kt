package com.bill.floodfilldemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.GridLayoutManager
import java.io.IOException


class MainActivity : AppCompatActivity() {

    var pictureBeans: List<PictureBean.Picture>? = null
    var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.detail_gird)
        loadLocalData()

    }

    private fun loadLocalData() {
        try {
            val assetList = assets.list("SecretGarden")?.toList() ?: emptyList()
            pictureBeans = getSecretGardenBean(ArrayList(assetList))
            if (pictureBeans == null) {
                Toast.makeText(this, "加载数据失败", Toast.LENGTH_SHORT).show()
            } else {
                showGrid(true)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun getSecretGardenBean(secretGarden: ArrayList<String>): List<PictureBean.Picture> {
        val pictureBeans = mutableListOf<PictureBean.Picture>()
        for (s in secretGarden) {
            pictureBeans.add(PictureBean.Picture(s))
        }
        return pictureBeans
    }

    private fun showGrid(isLocal: Boolean) {
        Log.e("YBill", "pictureBeans size: " + pictureBeans?.size)
        recyclerView?.layoutManager = GridLayoutManager(this, 2)
        val adapter = PictureAdapter(pictureBeans!!)
        recyclerView?.adapter = adapter
        adapter.setClickListener(object : OnRecycleViewItemClickListener {
            override fun recycleViewItemClickListener(view: View?, position: Int) {
                val intent = Intent(this@MainActivity, ColoringActivity::class.java)
                intent.putExtra("uri", pictureBeans!![position].uri)
                startActivity(intent)
            }
        })
    }


}