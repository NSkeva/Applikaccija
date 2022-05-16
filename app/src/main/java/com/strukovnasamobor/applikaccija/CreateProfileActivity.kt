package com.strukovnasamobor.applikaccija

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class CreateProfileActivity : AppCompatActivity() {
    lateinit var displayName: EditText
    private lateinit var btnConfirm: Button
    val user = Firebase.auth.currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_profile)

        displayName = findViewById(R.id.displayName)
        btnConfirm = findViewById(R.id.btnConfirm)

    }

    private fun updateProfile() {

        val user = Firebase.auth.currentUser

        val profileUpdates = userProfileChangeRequest {
            displayName = displayName.toString()
            photoUri = Uri.parse("https://example.com/jane-q-user/profile.jpg")

        }

        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    changeView()
                }
            }

    }

    private fun changeView()
    {
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
    }
}
