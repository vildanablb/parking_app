package com.example.parking_app.ui.main

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.example.parking_app.R
import com.example.parking_app.api.ParkingLot
import com.example.parking_app.util.BaseDialog
import com.example.parking_app.util.FirebaseUtil
import kotlinx.android.synthetic.main.dialog_rate_parking.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RateParkingDialog : DialogFragment(), CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.Main

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_rate_parking, null, false)
        val parkingLot =
            requireArguments().getParcelable<ParkingLot>("parkingLot") ?: throw Exception("")
        val newWorking = !parkingLot.parked_car
            view.close_icon.setOnClickListener {
                dismiss()
            }
        view.report_traffic.setOnClickListener {
            val rating = view.rating.rating.toInt()
            launch {
                FirebaseUtil.updateParkingStatus(parkingLot,newWorking,rating)
            }
            Toast.makeText(
                context,
                "You rated this parking lot " + rating + " stars",
                Toast.LENGTH_SHORT
            ).show()
            dismiss()
        }

        return AlertDialog.Builder(requireContext())
            .setView(view)
            .create().apply {
                window?.setBackgroundDrawableResource(R.drawable.bg_rounded_15dp)
            }
    }

    companion object {
        fun create(parkingLot: ParkingLot): RateParkingDialog {
            return RateParkingDialog().apply {
                arguments = bundleOf(
                    "parkingLot" to parkingLot
                )
            }

        }
    }
}