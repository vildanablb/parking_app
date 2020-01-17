package com.example.parking_app.ui.authentication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.parking_app.R
import com.example.parking_app.util.disable
import com.example.parking_app.util.enable
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_register)
        login.setOnClickListener {
            onBackPressed()
        }

    }

    companion object{
        fun launch(context: Context){
            context.startActivity(Intent(context, RegisterActivity::class.java))
        }
    }
}