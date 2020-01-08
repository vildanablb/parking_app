package com.example.parking_app.util

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.DialogFragment
import com.example.parking_app.R

abstract class BaseDialog : DialogFragment() {

    lateinit var innerView: View
        private set

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val layoutInflater = LayoutInflater.from(requireContext())
        innerView = layoutInflater.inflate(viewLayoutId, null, false)
        bindView(innerView, savedInstanceState)
        return AlertDialog.Builder(requireContext())
            .setView(innerView)
            .create().apply {
                window?.setBackgroundDrawableResource(R.drawable.bg_rounded_15dp)
            }
    }

    abstract val viewLayoutId: Int

    open fun bindView(view: View, savedInstanceState: Bundle?) {}
}