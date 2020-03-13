package com.example.masterannouncer

import android.app.Activity
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.masterannouncer.domain.Announcement
import com.example.masterannouncer.repository.Repository
import com.example.masterannouncer.service.Service
import kotlinx.android.synthetic.main.edit_layout.*

class EditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_layout)

        var id: Long=-1
        var visible: Boolean=true
        val bundle=intent.extras
        if(bundle!=null){
            id=bundle.getLong("id")
            editTitle.setText(bundle.getString("title"))
            editDesc.setText(bundle.getString("desc"))
            visible=bundle.getBoolean("visible",true)
        }
        if(!visible) {
            editButton.visibility= View.INVISIBLE
        }

        editButton.setOnClickListener{
//            Service.editAnnouncement(id, Announcement(id,editTitle.text.toString(),editDesc.text.toString()))
            val intent= Intent()
            intent.putExtra("id",id.toString())
            intent.putExtra("title",editTitle.text.toString())
            intent.putExtra("desc",editDesc.text.toString())
            setResult(Activity.RESULT_OK,intent)
            finish()
        }
    }
}
