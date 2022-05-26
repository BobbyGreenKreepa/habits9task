package com.example.habitsclean.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.TextView
import com.example.habitsclean.R

object LoadingScreen {

    private var dialog: Dialog? = null

    fun showLoading(context: Context?) {

        dialog = Dialog(context!!)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.loading_dialog_fragment)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog?.show()
    }

    fun hideLoading() = dialog?.dismiss()
}