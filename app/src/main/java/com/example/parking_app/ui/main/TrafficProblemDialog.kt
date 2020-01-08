package com.example.parking_app.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.example.parking_app.R
import com.example.parking_app.util.BaseDialog
import kotlinx.android.synthetic.main.dialog_report_traffic.view.*

class TrafficProblemDialog:BaseDialog(){

    override val viewLayoutId: Int
        get() = R.layout.dialog_report_traffic

    override fun bindView(view: View, savedInstanceState: Bundle?) {
        super.bindView(view, savedInstanceState)
        view.close_icon.setOnClickListener {
            dismiss()
        }

        view.report_traffic.setOnClickListener {
            TrafficProblemActivity.launch(requireContext())
            dismiss()
        }
    }

}