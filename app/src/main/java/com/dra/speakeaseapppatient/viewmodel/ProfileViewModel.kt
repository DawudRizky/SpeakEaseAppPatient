package com.dra.speakeaseapppatient.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.dra.speakeaseapppatient.model.Profile
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileViewModel : ViewModel() {
    private val _profile = MutableStateFlow(Profile())
    val profile: StateFlow<Profile> get() = _profile

    private val database = FirebaseDatabase.getInstance("https://speakease-eb1ab-default-rtdb.asia-southeast1.firebasedatabase.app/")
    private val profileRef = database.getReference("profile")

    init {
        fetchProfile()
    }

    fun fetchProfile() {
        profileRef.get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    val data = snapshot.getValue(Profile::class.java)
                    if (data != null) {
                        _profile.value = data
                    } else {
                        Log.e("ProfileViewModel", "Data is null")
                    }
                } else {
                    Log.e("ProfileViewModel", "Snapshot does not exist")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("ProfileViewModel", "Failed to fetch data", exception)
            }
    }
}
