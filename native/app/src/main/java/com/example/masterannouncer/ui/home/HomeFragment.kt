package com.example.masterannouncer.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.masterannouncer.R
import com.example.masterannouncer.ui.edit.EditViewAdapter
import com.example.masterannouncer.ui.edit.EditViewModel
import com.example.masterannouncer.ui.edit.FadeInLinearLayoutManager

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter: HomeViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.list_view)

        homeViewModel.announcements.observe(this, Observer {
            val homeViewAdapter= HomeViewAdapter(it,this.context)
            recyclerView.adapter=homeViewAdapter
            adapter=homeViewAdapter
            recyclerView.layoutManager= FadeInLinearLayoutManager(context)
        })

        return root
    }

    override fun onResume() {
        super.onResume()
//        adapter.notifyDataSetChanged()
    }
}