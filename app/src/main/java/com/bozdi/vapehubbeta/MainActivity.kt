package com.bozdi.vapehubbeta

import android.content.ClipData
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bozdi.vapehubbeta.managerFragments.CouriersList
import com.bozdi.vapehubbeta.model.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import java.io.IOException

class MainActivity : AppCompatActivity() {

    var globVar: GlobalVars = GlobalVars
    var okHttpClient: OkHttpClient = OkHttpClient()

    private val managerOrders = OrdersList()
    private val managerCouriers = CouriersList()
    private val managerMap = Map()
    private val managerProfile = Profile()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(managerOrders)


        (getApplicationContext() as AppServices).serverData.getOrdersList()
        (getApplicationContext() as AppServices).serverData.getCouriersList()
        (getApplicationContext() as AppServices).serverData.getManagersList()
        (getApplicationContext() as AppServices).serverData.getStoresList()
        (getApplicationContext() as AppServices).serverData.getCitiesList()
        (getApplicationContext() as AppServices).serverData.getCourierBackpackList()



        lateinit var bottomNavigationView: BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        setBottomNavigation(globVar.UserType, bottomNavigationView)


    }


    private fun replaceFragment(fragment: Fragment) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }

    private fun setBottomNavigation(type: String, bottomNav: BottomNavigationView)
    {
        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_orders -> replaceFragment(managerOrders)
                R.id.nav_couriers -> replaceFragment(managerCouriers)
                R.id.nav_map -> replaceFragment(managerMap)
                R.id.nav_profile -> replaceFragment(managerProfile)
            }
            true
        }
        when(type){
            "ADMN" -> {
                bottomNav.menu.findItem(R.id.nav_cities).isVisible = true;
                bottomNav.menu.findItem(R.id.nav_stores).isVisible = true;
                bottomNav.menu.findItem(R.id.nav_managers).isVisible = true;
            }
            "MNGR" -> {//*
                bottomNav.menu.findItem(R.id.nav_orders).isVisible = true;
                bottomNav.menu.findItem(R.id.nav_couriers).isVisible = true;
                bottomNav.menu.findItem(R.id.nav_map).isVisible = true;
                bottomNav.menu.findItem(R.id.nav_profile).isVisible = true;
            }
            "COUR" -> {

            }
            "DELT" -> {

            }

        }
    }
}