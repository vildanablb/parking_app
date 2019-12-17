package com.example.parking_app.ui.authentication

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.example.parking_app.R
import com.example.parking_app.ui.main.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    internal var client: GoogleSignInClient? = null
    internal var auth: FirebaseAuth? = null
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

        Toast.makeText(this, "Trying to sign in", Toast.LENGTH_SHORT).show()
        val signInIntent = client?.signInIntent
        startActivityForResult(signInIntent, SIGN_IN_CODE)

    }


    fun setAuth() {
        FirebaseApp.initializeApp(this)
        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("443634677513-p2bt55kaajute54ian765qemssa1ctln.apps.googleusercontent.com")
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
                Toast.makeText(this, "Sign in failed 1 ", Toast.LENGTH_SHORT).show()
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
            auth?.signInWithCredential(credential)
                ?.addOnCompleteListener(
                    this
                ) { task ->
                    if (task.isSuccessful) {
                        val user = auth?.currentUser
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
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Google sign in fail 2", Toast.LENGTH_SHORT).show()
            progress.dismiss()
        }

    }

}
