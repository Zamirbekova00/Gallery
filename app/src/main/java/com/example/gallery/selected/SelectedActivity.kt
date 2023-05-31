package com.example.gallery.selected

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gallery.model.Photo
import com.example.gallery.databinding.ActivitySelectedBinding
import com.example.gallery.received.ReceivedAdapter

class SelectedActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectedBinding
    private lateinit var adapter: ReceivedAdapter
     var imgSelect =  ArrayList<Photo> ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        imgSelect = intent.getSerializableExtra("result") as ArrayList<Photo>
        binding.recyclerView2.adapter = adapter
        adapter.setItem(imgSelect)
    }
}