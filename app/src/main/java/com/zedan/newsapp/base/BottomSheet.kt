package com.zedan.newsapp.base

import android.app.Dialog
import android.os.Bundle
import androidx.navigation.NavDirections
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zedan.newsapp.R
import com.zedan.newsapp.util.navigateTo
import com.zedan.newsapp.util.showToast

open class BottomSheet : BottomSheetDialogFragment(), NavigationMethod{

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

//    //For make Bottom sheet with Round corner.
//    override fun getTheme() : Int  = R.style.BottomSheetDialogTheme
//
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
//        BottomSheetDialog(requireContext(), theme)
}