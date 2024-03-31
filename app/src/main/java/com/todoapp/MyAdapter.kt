package com.todoapp

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.textfield.TextInputLayout

class MyAdapter(val context: Context, val layout: Int, val list: ArrayList<DataClass>) :
    RecyclerView.Adapter<MyAdapter.MyViewHold>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHold {
        val view: View = LayoutInflater.from(context).inflate(layout, parent, false)
        return MyViewHold(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHold(itemView: View) : ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
        val taskText: TextView = itemView.findViewById(R.id.taskText)
        val editButton: ImageButton = itemView.findViewById(R.id.editButton)
        val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)
    }

    @SuppressLint("MissingInflatedId")
    override fun onBindViewHolder(holder: MyViewHold, position: Int) {
        val data = list[position]
        holder.checkBox.isChecked = data.isCheck

        holder.taskText.text = data.task

        holder.editButton.setOnClickListener {
            val alert = AlertDialog.Builder(context)
            val view: View = LayoutInflater.from(context).inflate(R.layout.update_layout, null)
            alert.setView(view)

            val textInputLayout: TextInputLayout = view.findViewById(R.id.updateTaskEditText)
            val updateEditText: EditText = textInputLayout.editText!!
            updateEditText.text = Editable.Factory.getInstance().newEditable(data.task)
            val updateButton: Button = view.findViewById(R.id.updateButton)
            val cancelButton: ImageButton = view.findViewById(R.id.cancelButton)

            val updateAlert = alert.create()
            updateAlert.show()

            updateButton.setOnClickListener {
                val taskText = updateEditText.text.toString()
                if (taskText.isNotEmpty() || taskText.isNotBlank()) {
                    list[position].task = taskText
                    updateAlert.dismiss()
                    notifyDataSetChanged()
                } else {
                    textInputLayout.error = "Empty Field."
                    updateEditText.doOnTextChanged { text, _, _, _ ->
                        if (text!!.isNotBlank() || text.isNotBlank()) {
                            textInputLayout.error = null
                        }
                    }
                }
            }

            cancelButton.setOnClickListener {
                updateAlert.dismiss()
            }
        }

        holder.deleteButton.setOnClickListener {
            list.removeAt(position)
            notifyDataSetChanged()
        }
    }
}