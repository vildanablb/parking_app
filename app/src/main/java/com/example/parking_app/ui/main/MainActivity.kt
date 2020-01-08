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
import kotlinx.android.synthetic.main.header_layout.view.*
import android.net.Uri
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.bumptech.glide.Glide
import com.example.parking_app.R
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {


    var user: FirebaseUser? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        NavigationUI.setupWithNavController(
            navigationView,
            my_nav_host_fragment.findNavController()
        )

        user = intent.getParcelableExtra("user")
        val photoUrl: Uri?  = intent.getParcelableExtra("photoUrl")
        val headerView = navigationView.getHeaderView(0)
        headerView.username.text = user?.displayName
        headerView.email.text = user?.email
        Glide.with(this).load(photoUrl).into(headerView.profile_picture)


    }


    companion object {
        fun launch(context: Context?, fragmentId: Int? = null, user: FirebaseUser? = null, photoUrl: Uri? = null) {
            context?.startActivity(Intent(context, MainActivity::class.java).apply {
                putExtra("fragment", fragmentId)
                putExtra("user", user)
                putExtra("photoUrl", photoUrl)

            })
        }
    }
}
