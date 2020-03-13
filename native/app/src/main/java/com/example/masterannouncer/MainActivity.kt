package com.example.masterannouncer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.masterannouncer.domain.Announcement
import com.example.masterannouncer.domain.AnnouncementId
import com.example.masterannouncer.repository.Repository
import com.example.masterannouncer.service.Service
import com.example.masterannouncer.ui.edit.EditViewModel
import com.example.masterannouncer.ui.home.HomeViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var editViewModel: EditViewModel
    private lateinit var syncItem: MenuItem
    private lateinit var signOut: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            val intent = Intent(this, AddActivity::class.java)
            startActivityForResult(intent, 2)
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_announcements, R.id.nav_myAnnouncements
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        editViewModel = ViewModelProvider(this).get(EditViewModel::class.java)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)

        syncItem = menu.getItem(0)
        signOut = menu.getItem(1)

        signOut.setOnMenuItemClickListener {
            signOut()
            true
        }

        syncItem.setOnMenuItemClickListener {
            if (isNetworkConnected(applicationContext)) {
                GlobalScope.launch {
                    val announcementsDB: List<Announcement> = Service.repository.getAnnouncementsStatic()

                    Log.d("SYNC",announcementsDB.toString())
                    val announcementsToAdd: List<AnnouncementId> = Service.repository.getToAdd()
                    Log.d("SYNC",announcementsToAdd.toString())

                    for(announcementId in announcementsToAdd){
                        Service.service.postAnnouncement(announcementsDB.first { a ->a.id==announcementId.id  })
                    }

                    Service.repository.deleteAll()
                    val announcements: List<Announcement> = Service.service.getAnnouncements()
                    for (announcement in announcements) {
                        Service.repository.addAnnouncement(Announcement(announcement.id*(-1),announcement.title,announcement.desc))
                    }

                    Service.repository.deleteAllToAdd()

                }
            }
            else{
                Toast.makeText(applicationContext,"Network connection not found!",Toast.LENGTH_SHORT).show()
            }
            true
        }

        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2) {
            val title = data?.getStringExtra("title").orEmpty()
            val desc = data?.getStringExtra("desc").orEmpty()
            val announcement: Announcement = Announcement(0, title, desc)
            editViewModel.insert(announcement)
            if(isNetworkConnected(applicationContext )){
                GlobalScope.launch {
                    Service.service.postAnnouncement(announcement)
                }
            }
            else{
                    editViewModel.insertToAdd(announcement)

                Toast.makeText(applicationContext,"Will be sent to server when synced",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signOut() {
        startActivity(SignInActivity.getLaunchIntent(this))
        FirebaseAuth.getInstance().signOut()
        GoogleSignIn.getClient(
            this,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
        ).signOut()
        finish()
    }

    companion object {
        fun getLaunchIntent(from: Context) = Intent(from, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

}
