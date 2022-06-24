package com.example.labtests

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel() : ViewModel() {
    var _name = MutableLiveData("")
    val name: LiveData<String> = _name

    var _email = MutableLiveData("")
    val email: LiveData<String> = _email

    var _age = MutableLiveData(0)
    val age: LiveData<Int> = _age

    var _dob = MutableLiveData("")
    val dob: LiveData<String> = _dob

    var _gender = MutableLiveData(0)
    val gender: LiveData<Int> = _gender

    fun retrieve(sharedPreferences: SharedPreferences) {
        //check if the preference by the key "nameKey" exist
        if (sharedPreferences.contains(MainActivity.Name)) {
            // Set the edit text content to the value of "nameKey"
            _name.value = sharedPreferences.getString(MainActivity.Name, "").toString()
        }
        //check if the preference by the key "emailKey" exist
        if (sharedPreferences.contains(MainActivity.Email)) {
            // Set the edit text content to the value of "emailKey"
            _email.value = (sharedPreferences.getString(MainActivity.Email, "").toString())
        }
        //check if the preference by the key "DOBKey" exist
        if (sharedPreferences.contains(MainActivity.DOB)) {
            // Set the edit text content to the value of "DOBKey"
            _dob.value = sharedPreferences.getString(MainActivity.DOB, "").toString()
        }
        //check if the preference by the key "genderKey" exist
        if (sharedPreferences.contains(MainActivity.Gender2)) {
            // Set the number picker content to the value of "genderKey"
            _gender.value = sharedPreferences.getInt(MainActivity.Gender2, 0)
        }
        //check if the preference by the key "ageKey" exist
        if (sharedPreferences.contains(MainActivity.Age)) {
            // Set the number picker content to the value of "ageKey"
            _age.value = sharedPreferences.getInt(MainActivity.Age, 0)
        }
    }

    fun save(user: User, sharedPreferences: SharedPreferences) {
        _name.value = user.name
        _email.value = user.email
        _age.value = user.age
        _gender.value = user.gender
        _dob.value = user.dob

        var N = user.name
        var E = user.email
        var A = user.age
        var G = user.gender
        var D = user.dob

        var editor = sharedPreferences.edit()
        editor.putString(MainActivity.Name, N) //store string into the shared preference with nameKey
        editor.putString(MainActivity.Email, E) //store string into the shared preference with emailKey
        if (A != null) {
            editor.putInt(MainActivity.Age, A)
        }
        editor.putInt(MainActivity.Gender2, G)
        editor.putString(MainActivity.DOB, D)
        editor.apply()
    }

    fun clear() {
        _name.value = ""
        _age.value = 0
        _gender.value = 0
        _dob.value = ""
        _email.value = ""
    }
}