package com.example.parking_app.ui.authentication

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.parking_app.BuildConfig
import com.example.parking_app.R
import com.example.parking_app.ui.main.MainActivity
import com.example.parking_app.util.FirebaseUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    internal var client: GoogleSignInClient? = null
    internal var user: FirebaseUser? = null

    internal val SIGN_IN_CODE = 0

    internal var currentUser: FirebaseUser? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setAuth()
        login_btn.setOnClickListener {
            MainActivity.launch(this, null, null)
        }
        google_btn.setOnClickListener {
            signIn()
        }
    }

    fun signIn() {

        val signInIntent = client?.signInIntent
        startActivityForResult(signInIntent, SIGN_IN_CODE)

    }

    fun setAuth() {
        FirebaseApp.initializeApp(this)
        FirebaseUtil.auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.REQUEST_ID_TOKEN)
            .requestEmail().build()

        client = GoogleSignIn.getClient(this, gso)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SIGN_IN_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                Toast.makeText(this, e.statusMessage, Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {

        val progress = ProgressDialog(this)
        progress.setTitle("Signing in")
        progress.setCancelable(false) // disable dismiss by tapping outside of the dialog
        progress.show()

        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        try {
            FirebaseUtil.auth?.signInWithCredential(credential)
                ?.addOnCompleteListener(
                    this
                ) { task ->
                    if (task.isSuccessful) {
                        val user = FirebaseUtil.auth?.currentUser
                        progress.dismiss()
                        MainActivity.launch(this, null, user, user?.photoUrl)
                        finishAffinity()

                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "Google sign in failed",
                            Toast.LENGTH_SHORT
                        ).show()
                        progress.dismiss()

                    }
                }
        } catch (e: ApiException) {
            e.printStackTrace()
            Toast.makeText(this, e.statusMessage , Toast.LENGTH_SHORT).show()
            progress.dismiss()
        }

    }

}
