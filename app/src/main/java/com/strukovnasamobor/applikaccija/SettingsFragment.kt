package com.strukovnasamobor.applikaccija

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class SettingsFragment : Fragment(R.layout.fragment_settings) {
    /*var db = FirebaseFirestore.getInstance()
    val user = FirebaseAuth.getInstance().currentUser
    var uid = user?.uid
    val docRef = db.collection("users").document(uid.toString())
    var usermap =

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    usermap = document.data
                    Log.d(TAG, "${document.id} => ${document.data}")
                }

            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
        // [END get_all_users]
    }*/
}