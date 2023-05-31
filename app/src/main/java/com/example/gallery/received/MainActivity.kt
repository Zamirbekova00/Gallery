package com.example.gallery.received

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.gallery.model.Photo
import com.example.gallery.selected.SelectedActivity
import com.example.gallery.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var images = ArrayList<Photo>()
    var selectedImages = ArrayList<Photo>()
    private lateinit var adapter: ReceivedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = ReceivedAdapter(this)
        binding.recyclerView.adapter = adapter
        initListener()
        isPermission()
    }

    private fun initListener() {
        with(binding) {
            btnNext.setOnClickListener {
                val intent = Intent(this@MainActivity, SelectedActivity::class.java)
                intent.putExtra("result", selectedImages)
                startActivity(intent)
            }
        }
    }

    private fun isPermission() {
        val permissionStatus = ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE)
        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            images = getImagesFromGallery()
            adapter.setItem(images)
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(WRITE_EXTERNAL_STORAGE), 200)
        }
    }

    private fun getImagesFromGallery(): ArrayList<Photo> {
        val cursor: Cursor
        val listOfAllImages: ArrayList<Photo> = ArrayList()
        var absolutePathOfImage: String
        val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection =
            arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        val orderBy: String = MediaStore.Images.Media.DATE_TAKEN
        cursor = contentResolver.query(uri, projection, null, null, "$orderBy DESC")!!
        val columnIndexData: Int = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(columnIndexData)
            listOfAllImages.add(Photo(absolutePathOfImage, false))
        }
        return listOfAllImages
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 200 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            images = getImagesFromGallery()
            adapter.setItem(images)
        } else {
            finish()
        }
    }

    fun onClick(gallery: Photo, position: Int) {
        images[position].isSelected = !gallery.isSelected
        adapter.notifyDataSetChanged()
        selectedImages = images.filter { it.isSelected } as ArrayList<Photo>
        binding.textSelected.text = "Выбрано ${selectedImages.size} фотографий"
    }
}

