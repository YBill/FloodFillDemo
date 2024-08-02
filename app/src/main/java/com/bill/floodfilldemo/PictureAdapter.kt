package com.bill.floodfilldemo

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

/**
 * author ywb
 * date 2024/7/31
 * desc
 */
class PictureAdapter(private val pictures: List<PictureBean.Picture>) :
    RecyclerView.Adapter<PictureAdapter.PictureViewHolder>() {

    var onRecycleViewItemClickListener: OnRecycleViewItemClickListener? = null

    fun setClickListener(onRecycleViewItemClickListener: OnRecycleViewItemClickListener) {
        this.onRecycleViewItemClickListener = onRecycleViewItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.gird_view_item, parent, false)
        return PictureViewHolder(view)
    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        val picture = pictures[position]
        val assetManager = holder.imageView.context.assets
        val inputStream = assetManager.open("SecretGarden/${picture.uri}")
        val bitmap = BitmapFactory.decodeStream(inputStream)
        holder.imageView.setImageBitmap(bitmap)

        holder.imageView.setOnClickListener {
            onRecycleViewItemClickListener?.recycleViewItemClickListener(holder.imageView, position)
        }
    }

    override fun getItemCount(): Int = pictures.size

    class PictureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView as ImageView
    }
}