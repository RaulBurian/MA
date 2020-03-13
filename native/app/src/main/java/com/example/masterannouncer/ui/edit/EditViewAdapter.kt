package com.example.masterannouncer.ui.edit

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.masterannouncer.EditActivity
import com.example.masterannouncer.R
import com.example.masterannouncer.domain.Announcement
import com.example.masterannouncer.isNetworkConnected
import com.example.masterannouncer.service.Service

class EditViewAdapter(private val fragment: Fragment,private val viewModel: EditViewModel)
    : RecyclerView.Adapter<EditViewAdapter.ViewHolder>(){


    private var items = emptyList<Announcement  >() // Cached copy of words

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val textView: TextView =view.findViewById<TextView>(R.id.itemTitleEdit)

    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): EditViewAdapter.ViewHolder {


        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_edit_layout, parent, false)



        return EditViewAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: EditViewAdapter.ViewHolder, position: Int) {
        holder.textView.text = items[position].title

        val editButton: View = holder.view.findViewById(R.id.itemEdit)
        val deleteButton: View = holder.view.findViewById(R.id.itemDelete)

        deleteButton.setOnClickListener{view->
            if(isNetworkConnected(fragment.context!!)) {
                Log.d("delete", items[position].title)
                viewModel.delete(items[position])
                this.notifyDataSetChanged()
            }
            else{
                Toast.makeText(fragment.context,"Network connection not found!", Toast.LENGTH_SHORT).show()
            }
        }

        editButton.setOnClickListener{

            if(isNetworkConnected(fragment.context!!)){
                val intent=Intent(fragment.context,EditActivity::class.java)
                intent.putExtra("id",items[position].id)
                intent.putExtra("title",items[position].title)
                intent.putExtra("desc",items[position].desc)

                fragment.startActivityForResult(intent,1)
            }
            else{
                Toast.makeText(fragment.context,"Network connection not found!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    internal fun setAnnouncements(words: List<Announcement>) {
        this.items = words
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size
}