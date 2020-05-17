package com.zedan.newsapp.util

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController

fun Fragment.navigateTo(res : Int, bundle : Bundle){
    handleNavigation { findNavController().navigate(res, bundle) }
}

fun View.navigateTo(res : Int, bundle : Bundle){
    handleNavigation { findNavController().navigate(res, bundle) }
}

fun Fragment.navigateTo(res : Int){
    handleNavigation { findNavController().navigate(res) }
}

fun View.navigateTo(res : Int){
    handleNavigation { findNavController().navigate(res) }
}

fun View.navigateTo(res : NavDirections){
    handleNavigation { findNavController().navigate(res) }
}

fun Fragment.navigateTo(res : NavDirections){
    handleNavigation { findNavController().navigate(res) }
}

private inline fun handleNavigation(nav : () -> Unit){
    try {
        nav.invoke()
    }catch (t : Throwable){}
}