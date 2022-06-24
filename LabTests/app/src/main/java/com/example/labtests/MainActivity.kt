package com.example.labtests

import android.app.DatePickerDialog
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.lifecycle.ViewModelProvider
import com.example.labtests.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    lateinit var sharedpreferences: SharedPreferences
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: UserViewModel

    lateinit var name: EditText
    lateinit var gender: Spinner

    lateinit var picker: DatePickerDialog
    var genders = arrayOf("Male", "Female", "Others")
    var gender_selected = ""
    var gender_pos_selected = 0
    lateinit var adapter: ArrayAdapter<CharSequence>

    companion object {
        const val Name = "nameKey"
        const val Email = "emailKey"
        const val Age = "ageKey"
        const val Gender = "genderKey"
        const val Gender2 = "genderKeyPos"
        const val DOB = "DOBKey"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Provider & Observer
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        viewModel.name.observe(this) {
            binding.etName.setText(it)
        }
        viewModel.email.observe(this) {
            binding.etEmail.setText(it)
        }
        viewModel.age.observe(this) {
            binding.age.value = it
        }
        viewModel.gender.observe(this) {
            binding.genderspinner.setSelection(it)
        }
        viewModel.dob.observe(this) {
            binding.DOBdatepicker.setText(it)
        }

        // Shared Preferences
        sharedpreferences = applicationContext.getSharedPreferences("com.example.userregistrationsharedpreference", MODE_PRIVATE)

        //Initialize NumberPicker for age
        if (binding.age != null) {
            binding.age.minValue = 0
            binding.age.maxValue = 120
            binding.age.wrapSelectorWheel = true
        }

        gender = binding.genderspinner
        gender.onItemSelectedListener = this
        // Create an ArrayAdapter using the string array and a default spinner layout
        adapter = ArrayAdapter.createFromResource(this,
            R.array.gender_array, android.R.layout.simple_spinner_item)
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        gender.adapter = adapter

        // Datepicker for DOB
        binding.DOBdatepicker.setOnClickListener(View.OnClickListener {
            val cldr = Calendar.getInstance()
            val day = cldr[Calendar.DAY_OF_MONTH]
            val month = cldr[Calendar.MONTH]
            val year = cldr[Calendar.YEAR]
            // date picker dialog
            picker = DatePickerDialog(this@MainActivity,
                { view, year, monthOfYear, dayOfMonth -> binding.DOBdatepicker.setText(dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year) },
                year,
                month,
                day)
            picker.show()
        })

        // Save
        binding.btnSave.setOnClickListener{
            var user = User(binding.etName.text.toString(), binding.etEmail.text.toString(), binding.age.value, binding.genderspinner.selectedItemPosition, binding.DOBdatepicker.text.toString())
            viewModel.save(user, sharedpreferences)
        }

        // Clear
        binding.btnClear.setOnClickListener{ viewModel.clear() }

        // Retrieve
        binding.btnRetr.setOnClickListener{
            viewModel.retrieve(sharedpreferences)
            if (sharedpreferences.contains(MainActivity.Gender)) {
                val spinnerPosition = adapter.getPosition(sharedpreferences.getString(MainActivity.Gender, "Male"))
                gender.setSelection(spinnerPosition)
            }
        }
    }

    override fun onItemSelected(
        parent: AdapterView<*>?, view: View,
        pos: Int, id: Long,
    ) {
        gender_selected = genders[pos]
        gender_pos_selected = pos
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }



}