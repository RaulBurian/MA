package com.example.masterannouncer

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.masterannouncer.domain.Announcement
import com.example.masterannouncer.service.Service
import kotlinx.android.synthetic.main.add_layout.*
import kotlinx.android.synthetic.main.edit_layout.*

class AddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_layout)

        addButton.setOnClickListener{
//            Service.addAnnouncement(Announcement(Service.getId(),addTitle.text.toString(),addDesc.text.toString()))

            val intent= Intent()
            intent.putExtra("title",addTitle.text.toString())
            intent.putExtra("desc",addDesc.text.toString())
            setResult(Activity.RESULT_OK,intent)
            this.finish()
        }
    }
}
