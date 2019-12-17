package com.example.parking_app.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.my_nav_host_fragment
import kotlinx.android.synthetic.main.activity_main.navigationView
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.header_layout.*
import kotlinx.android.synthetic.main.header_layout.view.*
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.parking_app.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true )
        setContentView(R.layout.activity_main)
        NavigationUI.setupWithNavController(
            navigationView,
            my_nav_host_fragment.findNavController()
        )

        val toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.open,
            R.string.close
        )
        drawer.addDrawerListener(toggle)
        toggle.setDrawerIndicatorEnabled(true)
        toggle.syncState()

       navigationView.getHeaderView(0).username.text = "testing testing"
    }



    companion object{
        fun launch(context: Context?, fragmentId: Int? = null, fragmentData: Bundle? = null){
            context?.startActivity(Intent(context,MainActivity::class.java).apply {
                if(fragmentId != null){
                    putExtra("fragment", fragmentId)
                }
                if(fragmentData != null){
                    putExtra("data", fragmentData)
                }
            })
        }
    }
}
