package com.zedan.newsapp.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import com.zedan.newsapp.util.navigateTo
import com.zedan.newsapp.util.showToast

open class BaseFragment : Fragment(), NavigationMethod{

    override fun navigate(des : Int) {
        navigateTo(des)
    }

    override fun navigate(des : NavDirections) {
        navigateTo(des)
    }

    override fun navigate(des : Int, args : Bundle) {
        navigateTo(des, args)
    }

    protected fun showToast(message : String){
        requireContext().showToast(message)
    }

    protected fun showToast(message : Int){
        requireContext().showToast(message)
    }
}