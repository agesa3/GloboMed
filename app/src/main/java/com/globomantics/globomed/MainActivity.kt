package com.globomantics.globomed

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.globomed.learn.AddEmployeeActivity
import com.globomed.learn.EmployeeListAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var  databaseHelper: DatabaseHelper
    private val employeeListAdapter=EmployeeListAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseHelper= DatabaseHelper(this)
        recyclerView.adapter=employeeListAdapter
        recyclerView.layoutManager=LinearLayoutManager(this)
        employeeListAdapter.setEmployees(DataManager.fetchAllEmployees(databaseHelper))



        fab.setOnClickListener{
            val addEmployee= Intent (this,AddEmployeeActivity::class.java)
            startActivityForResult(addEmployee,1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode==Activity.RESULT_OK){
            employeeListAdapter.setEmployees(DataManager.fetchAllEmployees(databaseHelper))
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.action_deleteAll->{
                val builder= AlertDialog.Builder(this)
                builder.setMessage(R.string.confirm_sure)
                    .setPositiveButton(R.string.yes){dialog, eId ->
                        val result=DataManager.deleteAllEmployee(databaseHelper)
                        Toast.makeText(applicationContext,"$result", Toast.LENGTH_SHORT).show()
                       finish()
                    }
                    .setNegativeButton(R.string.no){dialog, id ->
                        dialog.dismiss()
                    }
                val dialog=builder.create()
                dialog.setTitle("Are you sure")
                dialog.show()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}