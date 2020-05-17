package com.zedan.newsapp.base

import android.os.Bundle
import androidx.navigation.NavDirections

interface NavigationMethod {

    fun navigate(des: Int)

    fun navigate(des: Int, args: Bundle)

    fun navigate(des: NavDirections)
}