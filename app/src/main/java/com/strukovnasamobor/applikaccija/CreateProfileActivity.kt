package com.strukovnasamobor.applikaccija

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import android.provider.MediaStore

import android.graphics.Bitmap
import java.io.IOException
import com.google.firebase.storage.UploadTask

import android.widget.Toast

import androidx.annotation.NonNull

import com.google.android.gms.tasks.OnFailureListener

import com.google.android.gms.tasks.OnSuccessListener

import android.app.ProgressDialog
import com.google.firebase.firestore.OnProgressListener
import com.google.firebase.storage.ktx.storage
import java.io.File
import java.util.*


class CreateProfileActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    lateinit var displayFName: EditText
    lateinit var displayLName: EditText
    var ime_posla = ""
    lateinit var spinner: Spinner
    var db = FirebaseFirestore.getInstance()
    private lateinit var btnConfirm: Button
    val user = FirebaseAuth.getInstance().currentUser


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_profile)







        displayFName = findViewById(R.id.displayFName)
        displayLName = findViewById(R.id.displayLName)
        //btnConfirm = findViewById(R.id.btnConfirm)
        spinner = findViewById(R.id.posao_list)



        btnConfirm.setOnClickListener {
            updateProfile()
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.lista_poslova,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = this

    }






    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        ime_posla =  parent.getItemAtPosition(pos).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }

    private fun updateProfile() {
         var uid = ""
        user?.let {
             uid = user.uid
        }
        val user = hashMapOf(

            "first" to displayFName.text.toString(),
            "last" to displayLName.text.toString(),
            "job" to ime_posla

        )
        db.collection("users").document(uid)
            .set(user)
            .addOnSuccessListener { documentReference ->

                changeView()
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    private fun changeView()
    {
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
    }
}
