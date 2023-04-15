package com.example.film_app_2.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.film_app_2.R
import com.example.film_app_2.application.Application
import com.example.film_app_2.models.Constants
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {
    private var signInButton: Button? = null
    private var signUpButton: Button? = null
    private var signOutButton: Button? = null
    private var login: TextView? = null
    private var password: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as Application).activityComponent.injectLoginActivity(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signInButton = findViewById(R.id.signInButton)
        signUpButton = findViewById(R.id.signUpButton)
        signOutButton = findViewById(R.id.signOutButton)
        login = findViewById(R.id.loginField)
        password = findViewById(R.id.passwordField)

        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            val intent = Intent(this, FavoritesActivity::class.java)
            startActivity(intent)
        }

        signInButton!!.setOnClickListener {
            val login = login!!.text.toString()
            val password = password!!.text.toString()

            if (auth.currentUser != null) {
                val intent = Intent(this, FavoritesActivity::class.java)
                startActivity(intent)
            } else {
                auth.signInWithEmailAndPassword(login, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this, FavoritesActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                this@LoginActivity,
                                Constants.FAILED_SIGN_IN,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }
        }

        signUpButton!!.setOnClickListener {
            val login = login!!.text.toString()
            val password = password!!.text.toString()

            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(login, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this@LoginActivity,
                            Constants.SUCCESS_SIGN_UP,
                            Toast.LENGTH_LONG
                        )
                            .show()
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            Constants.FAILED_SIGN_IN,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }

        signOutButton!!.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
        }
    }
}