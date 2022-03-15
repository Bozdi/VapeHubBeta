package com.bozdi.vapehubbeta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class LoginActivity : AppCompatActivity() {

    private val managerOrders = ManagerOrders()
    private val managerCouriers = ManagerCouriers()
    private val managerMap = ManagerMap()
    private val managerProfile = ManagerProfile()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(managerOrders)

        lateinit var bottomNavigationView: BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        bottomNavigationView.setOnNavigationItemSelectedListener{
            when(it.itemId){
                R.id.nav_orders -> replaceFragment(managerOrders)
                R.id.nav_couriers -> replaceFragment(managerCouriers)
                R.id.nav_map -> replaceFragment(managerMap)
                R.id.nav_profile -> replaceFragment(managerProfile)
            }
            true
        }



    }


    private fun replaceFragment(fragment: Fragment) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }

    }
}