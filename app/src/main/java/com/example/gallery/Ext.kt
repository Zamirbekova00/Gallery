package com.example.gallery

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImage(uri:String){
    Glide.with(this.context).load(uri).into(this)
}