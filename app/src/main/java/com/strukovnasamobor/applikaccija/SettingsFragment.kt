package com.strukovnasamobor.applikaccija

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore






class SettingsFragment : Fragment(R.layout.fragment_settings) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val db = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid
        val displayFName = requireView().findViewById<TextView>(R.id.firstName)
        val displayLName = requireView().findViewById<TextView>(R.id.lastName)
        val displayJName = requireView().findViewById<TextView>(R.id.jobName)
        db.collection("users").document(uid.toString())
            .get()
            .addOnSuccessListener {
                displayFName.text = it.get("first").toString()
                displayLName.text = it.get("last").toString()
                displayJName.text = it.get("job").toString()
                //TO DO BATO TU IDE SLIKA JOS

            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}