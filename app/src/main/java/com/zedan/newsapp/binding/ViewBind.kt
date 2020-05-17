package com.zedan.newsapp.binding

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.zedan.newsapp.R

@BindingAdapter("visible")
fun View.visible(show : Boolean){
    visibility = if (show) View.VISIBLE else View.GONE
}

@BindingAdapter("loadImage")
fun ImageView.loadImage(urlImage: String?){
    urlImage?.let {
        visibility = View.VISIBLE
        Glide.with(this)
            .load(it)
            .into(this)
    }?: kotlin.run {
        visibility = View.GONE
    }
}