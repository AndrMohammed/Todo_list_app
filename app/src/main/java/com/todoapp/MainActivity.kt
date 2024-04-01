package com.todoapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputLayout
import com.todoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mainActivity: ActivityMainBinding

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainActivity = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivity.root)

        val db = TodoDatabase(this)

        val list = ArrayList<TaskDataClass>()
        readDataAndDisplayIt(list, db)

        mainActivity.addButton.setOnClickListener {
            val alert = AlertDialog.Builder(this)
            val view: View = LayoutInflater.from(this).inflate(R.layout.add_layout, null)
            alert.setView(view)
            val textInputLayout: TextInputLayout = view.findViewById(R.id.taskEditText)
            val editText = textInputLayout.editText!!
            val cancelButton: ImageButton = view.findViewById(R.id.cancelButton)
            val addButton: Button = view.findViewById(R.id.addButton)
            val addAlert = alert.create()

            addButton.setOnClickListener {
                val taskText = editText.text.toString()
                if (taskText.isNotEmpty() && taskText.isNotBlank()) {
                    db.addTask(TaskDataClass(0, taskText, 0))
                    readDataAndDisplayIt(list, db)
                    addAlert.dismiss()
                } else {
                    textInputLayout.error = "Empty Field."
                    editText.doOnTextChanged { text, start, before, count ->
                        if (text!!.isNotEmpty() && text.isNotBlank()) {
                            textInputLayout.error = null
                        }
                    }
                }
            }
            cancelButton.setOnClickListener {
                addAlert.dismiss()
            }
            addAlert.show()
        }
    }

    private fun readDataAndDisplayIt(
        list: ArrayList<TaskDataClass>,
        db: TodoDatabase
    ) {
        list.clear()
        db.readData().forEach {
            list.add(it)
        }
        mainActivity.recyclerView.layoutManager = LinearLayoutManager(this)
        mainActivity.recyclerView.adapter = MyAdapter(this, R.layout.row_layout, list,db)
    }
}