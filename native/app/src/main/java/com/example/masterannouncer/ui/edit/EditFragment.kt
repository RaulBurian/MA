package com.example.masterannouncer.ui.edit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.masterannouncer.R
import com.example.masterannouncer.domain.Announcement

class EditFragment : Fragment() {

    private lateinit var editViewModel: EditViewModel
    private lateinit var adapter: EditViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        editViewModel =
            ViewModelProvider(this).get(EditViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_gallery, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.list_edit)
        adapter=EditViewAdapter(this,editViewModel)
        recyclerView.adapter=adapter
        recyclerView.layoutManager=FadeInLinearLayoutManager(context)
        editViewModel.announcements.observe(this, Observer {
            Log.d("items",it.toString())
            recyclerView.layoutManager=LinearLayoutManager(context)
            it?.let{
                adapter.setAnnouncements(it)
            }
        })

        return root
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==1) {
            val id = data?.getStringExtra("id").orEmpty()
            val title = data?.getStringExtra("title").orEmpty()
            val desc = data?.getStringExtra("desc").orEmpty()
            val intId = id.toLong()
            val announcement: Announcement = Announcement(intId, title, desc)
            editViewModel.update(announcement)
        }
    }
}