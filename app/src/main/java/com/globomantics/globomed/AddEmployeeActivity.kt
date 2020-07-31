package com.globomed.learn

import android.app.Activity
import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Toast
import com.globomantics.globomed.DatabaseHelper
import com.globomantics.globomed.GloboMedDBContract.EmployeeEntry
import com.globomantics.globomed.R
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.list_item.*
import java.text.SimpleDateFormat
import java.util.*
import com.globomantics.globomed.GloboMedDBContract as GloboMedDBContract1

class AddEmployeeActivity : Activity() {

    private val myCalendar = Calendar.getInstance()
    private lateinit var databaseHelper: DatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        databaseHelper=DatabaseHelper(this)

        // on clicking ok on the calender dialog
        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            etDOB.setText(getFormattedDate(myCalendar.timeInMillis))
        }

        etDOB.setOnClickListener {
            setUpCalender(date)
        }

        bSave.setOnClickListener {
            saveEmployee()
        }
        bCancel.setOnClickListener {
            finish()
        }
    }

    private fun saveEmployee() {
        var isValid = true

        etEmpName.error = if (etEmpName?.text.toString().isEmpty()) {
            isValid = false
            "Required Filed"
        } else null
        etDesignation.error = if (etDesignation?.text.toString().isEmpty()) {
            isValid = false
            "Required Filed"
        } else null
        if (isValid) {
            val name: String = etEmpName?.text.toString()
            val designation: String = etDesignation?.text.toString()
            val dob: Long = myCalendar.timeInMillis
            val isSurgeon=if (sSurgeon.isChecked) 1 else 0

            val db: SQLiteDatabase = databaseHelper.writableDatabase
            val values = ContentValues()
            values.put(EmployeeEntry.COLUMN_NAME, name)
            values.put(EmployeeEntry.COLUMN_DESIGNATION, designation)
            values.put(EmployeeEntry.COLUMN_DOB, dob)
            values.put(EmployeeEntry.COLUMN_SURGEON,isSurgeon)


            val result = db.insert(EmployeeEntry.TABLE_NAME, null, values)
            setResult(RESULT_OK, Intent())
            Toast.makeText(applicationContext, "Employee added", Toast.LENGTH_SHORT).show()

        }
        finish()
    }

    private fun setUpCalender(date: DatePickerDialog.OnDateSetListener) {

        DatePickerDialog(
            this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun getFormattedDate(dobInMilis: Long?): String {

        return dobInMilis?.let {
            val sdf = SimpleDateFormat("d MMM, yyyy", Locale.getDefault())
            sdf.format(dobInMilis)
        } ?: "Not Found"
    }
}
