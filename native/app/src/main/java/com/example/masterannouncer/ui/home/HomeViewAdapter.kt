package com.example.masterannouncer.ui.home

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.masterannouncer.EditActivity
import com.example.masterannouncer.R
import com.example.masterannouncer.domain.Announcement
import com.example.masterannouncer.service.Service

class HomeViewAdapter(private val items:List<Announcement>, private val context: Context?) : RecyclerView.Adapter<HomeViewAdapter.ViewHolder>(){

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val textView: TextView =view.findViewById<TextView>(R.id.itemTitleView)

    }



    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): HomeViewAdapter.ViewHolder {


        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_layout, parent, false)



        return HomeViewAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewAdapter.ViewHolder, position: Int) {
        holder.textView.text = items[position].title

        holder.textView.setOnClickListener{
            val intent=Intent(context,EditActivity::class.java)

            intent.putExtra("id",items[position].id)
            intent.putExtra("title",items[position].title)
            intent.putExtra("desc",items[position].desc)
            intent.putExtra("visible",false)

            context?.startActivity(intent)
        }

    }

    override fun getItemCount() = items.size
}