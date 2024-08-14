package com.example.eventinvitationapp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val eventNameInput = findViewById<EditText>(R.id.eventNameInput)
        val eventDateInput = findViewById<EditText>(R.id.eventDateInput)
        val eventLocationSpinner = findViewById<Spinner>(R.id.eventLocationSpinner)
        val createEventButton = findViewById<Button>(R.id.createEventButton)

        // Date Picker for Event Date
        eventDateInput.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    eventDateInput.setText("$selectedDay/${selectedMonth + 1}/$selectedYear")
                }, year, month, day)
            datePickerDialog.show()
        }

        // Populate Location Spinner
        val locations = arrayOf("New York", "Los Angeles", "Chicago", "San Francisco", "Miami")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, locations)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        eventLocationSpinner.adapter = adapter

        createEventButton.setOnClickListener {
            val eventName = eventNameInput.text.toString()
            val eventDate = eventDateInput.text.toString()
            val eventLocation = eventLocationSpinner.selectedItem.toString()

            val intent = Intent(this, EventDetailsActivity::class.java).apply {
                putExtra("EVENT_NAME", eventName)
                putExtra("EVENT_DATE", eventDate)
                putExtra("EVENT_LOCATION", eventLocation)
            }

            startActivity(intent)
        }
    }
}
