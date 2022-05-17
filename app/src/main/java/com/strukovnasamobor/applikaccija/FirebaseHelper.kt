package com.strukovnasamobor.applikaccija

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase




class FirebaseHelper() {

    private lateinit var database: DatabaseReference

    public fun onCreate(){
        database.getDatabase()
    }

    public fun search_posao(ime:String):String{
        //database.addValueEventListener(ValueEventListener)
        var poslovi=database.child("poslovi")//.child("1").child("naziv")
        /*
        FirebaseDatabase.DefaultInstance
            .GetReference("poslovi")
            .GetValueAsync().ContinueWithOnMainThread(task => {
                if (task.IsFaulted) {
                    // Handle the error...
                }
                else if (task.IsCompleted) {
                    DataSnapshot snapshot = task.Result;
                    // Do something with snapshot...
                }
            }); */

        //for(DataSnapshot snapshot : snapshot.getChildren()){ }

        for(i in 1..3){
           // FirebaseDatabase.getInstance().getReference("poslovi").getValueAsync()
            //poslovi.child(i.toString()).child("naziv").getValueAsync().toString()
           // DataSnapshot snapshot =poslovi.child(i.toString()).child(" ")
            //if(ime==)
        }
        return " "
    }

    /*
    fun onDataChange(dataSnapshot: DataSnapshot) {
        // This method is called once with the initial value and again
        // whenever data at this location is updated.
        val value = dataSnapshot.getValue()
        Log.d(TAG, "Value is: $value")
    }*/

}
