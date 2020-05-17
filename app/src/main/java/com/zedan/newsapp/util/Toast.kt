package com.zedan.newsapp.util

import android.annotation.SuppressLint
import android.content.Context
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.zedan.newsapp.R


fun Context.showToast(message: String){
    createToast { text = message }
}

fun Context.showToast(message: Int){
    createToast { setText(message) }
}
@SuppressLint("InflateParams")
private inline fun  Context.createToast(setMessage: TextView.()->Unit){
    //inflate custom layout
    val view = LayoutInflater.from(this).inflate(R.layout.toast_layout_custom, null)
    //set text to view
    setMessage.invoke( view.findViewById(R.id.toast_text))
    showToast(view)
}

private fun Context.showToast(view : View){
    //Declare for get action bar size to show toast below toolbar
    val typedValue = TypedValue()
    //get action bar size
    theme.resolveAttribute(android.R.attr.actionBarSize, typedValue, true)
    //declare y below toolbar
    val yOffset: Int = resources.getDimensionPixelSize(typedValue.resourceId) + 120

    Toast(this).apply {//create toast
        duration = Toast.LENGTH_SHORT
        setGravity(Gravity.TOP, 0 , yOffset)
        this.view = view
    }.show()
}