package com.todoapp

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
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

        val list = ArrayList<DataClass>()

        mainActivity.recyclerView.layoutManager = LinearLayoutManager(this)
        mainActivity.recyclerView.adapter = MyAdapter(this, R.layout.row_layout, list)

        mainActivity.addButton.setOnClickListener {
            val alert = AlertDialog.Builder(this)
            val view: View = LayoutInflater.from(this).inflate(R.layout.add_layout, null)
            /*val inflater: LayoutInflater = layoutInflater
            val view2: View = inflater.inflate(R.layout.add_layout, null)*/
            alert.setView(view)
            val textInputLayout: TextInputLayout = view.findViewById(R.id.taskEditText)
            val editText = textInputLayout.editText!!
            val cancelButton: ImageButton = view.findViewById(R.id.cancelButton)
            val addButton: Button = view.findViewById(R.id.addButton)
            val addAlert = alert.create()

/*            val colorStateList = ColorStateList (
                arrayOf(intArrayOf(android.R.attr.state_focused), intArrayOf()),
                intArrayOf(Color.GRAY, Color.GRAY)
            )
            textInputLayout.defaultHintTextColor = colorStateList*/

            addButton.setOnClickListener {
                val taskText = editText.text.toString()
                if (taskText.isNotEmpty() && taskText.isNotBlank()) {
                    list.add(DataClass(false, taskText))
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
}