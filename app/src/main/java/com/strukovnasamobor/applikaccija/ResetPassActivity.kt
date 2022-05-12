package com.strukovnasamobor.applikaccija

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ResetPassActivity : AppCompatActivity() {
    lateinit var etEmail: EditText
    lateinit var btnReset: Button
    private lateinit var tvRedirectSignUp: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_pass)

        etEmail = findViewById(R.id.etEmailAddress)
        tvRedirectSignUp = findViewById(R.id.tvRedirectSignUp)
        btnReset = findViewById(R.id.btnReset)

        btnReset.setOnClickListener {
            passReset()
        }

        tvRedirectSignUp.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            // using finish() to end the activity
            finish()
        }
    }

    private fun passReset() {
        val emailAddress = etEmail.text.toString()

        Firebase.auth.sendPasswordResetEmail(emailAddress)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                   changeView()
                }
            }

    }

    private fun changeView()
    {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

}