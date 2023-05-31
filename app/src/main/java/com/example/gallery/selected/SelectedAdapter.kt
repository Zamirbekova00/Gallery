package com.example.gallery.selected

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.gallery.model.Photo
import com.example.gallery.databinding.ItemImgReceivedBinding
import com.example.gallery.loadImage

class SelectedAdapter : Adapter<SelectedAdapter.SelectViewHolder>() {

    private val listResult = arrayListOf<Photo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectViewHolder {
        return SelectViewHolder(
            ItemImgReceivedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: SelectViewHolder, position: Int) {
        holder.bind(listResult[position])
    }

    override fun getItemCount() = listResult.size
    fun setImages(images: ArrayList<Photo>) {
        listResult.addAll(images)
        notifyDataSetChanged()
    }

    inner class SelectViewHolder(private val binding: ItemImgReceivedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo) {
            binding.imgResult.loadImage(photo.photo)
        }
    }
}