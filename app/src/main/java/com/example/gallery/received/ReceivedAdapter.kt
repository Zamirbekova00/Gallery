package com.example.gallery.received
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.gallery.model.Photo
import com.example.gallery.databinding.ItemImgGalleryBinding
import com.example.gallery.loadImage

class ReceivedAdapter(var listener: MainActivity): Adapter<ReceivedAdapter.ReceivedViewHolder>(){
    private val list = arrayListOf<Photo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceivedViewHolder {
        return ReceivedViewHolder(
            ItemImgGalleryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ReceivedViewHolder, position: Int) {
        holder.bind(list[position], listener, position)
    }

    override fun getItemCount() = list.size
    fun setItem(images : ArrayList<Photo>){
        list.addAll(images)
        notifyDataSetChanged()
    }
    inner class ReceivedViewHolder(private val binding:ItemImgGalleryBinding ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo, listener: MainActivity, position: Int) {
            binding.imgGallery.loadImage(photo.photo)
            binding.selectedImage.isVisible = photo.isSelected
            itemView.setOnClickListener {
                listener.onClick(photo, position)
            }
        }
    }
    interface Listener {
        fun onClick(photo: Photo, position: Int)
    }
}