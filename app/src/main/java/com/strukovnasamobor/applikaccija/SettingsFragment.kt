package com.strukovnasamobor.applikaccija

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

import android.widget.EditText









class SettingsFragment : Fragment(R.layout.fragment_settings) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val db = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid
        val v: View = inflater.inflate(R.layout.fragment_settings, container, false)
        val displayFName = v.findViewById<TextView>(R.id.firstName)
        val displayLName = v.findViewById<TextView>(R.id.lastName)
        val displayJName = v.findViewById<TextView>(R.id.jobName)
        val displayAName = v.findViewById<TextView>(R.id.address)
        val phoneNumber = v.findViewById<TextView>(R.id.phoneNumber)
        db.collection("users").document(uid.toString())
            .get()
            .addOnSuccessListener {
                displayFName.text = it.get("first").toString()
                displayLName.text = it.get("last").toString()
                displayJName.text = it.get("job").toString()
                displayAName.text = it.get("address").toString()
                phoneNumber.text = it.get("phone").toString()


            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
        return v
    }
}