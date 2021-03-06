package com.bozdi.vapehubbeta

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bozdi.vapehubbeta.adminFragments.CitiesList
import com.bozdi.vapehubbeta.adminFragments.ManagersList
import com.bozdi.vapehubbeta.adminFragments.StoresList
import com.bozdi.vapehubbeta.courierFragments.CourierGoods
import com.bozdi.vapehubbeta.managerFragments.CouriersList
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private var globVar: GlobalVars = GlobalVars

    private val managerOrders = OrdersList()
    private val managerCouriers = CouriersList()
    private val managerMap = Map()
    private val managerProfile = Profile()
    private val adminCities = CitiesList()
    private val adminStores = StoresList()
    private val adminManagers = ManagersList()
    private val courierGoods = CourierGoods()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        when(globVar.UserType) {
            "ADMN" -> replaceFragment(adminCities)
            else -> replaceFragment(managerOrders)
        }
        setBottomNavigation(GlobalVars.UserType)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(GlobalVars.UserType == "ADMN") {
            menuInflater.inflate(R.menu.admin_logout_menu, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.adminLogout) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        return true
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    private fun setBottomNavigation(type: String): BottomNavigationView {
        val result : BottomNavigationView

        when (type) {
            "ADMN" -> {
                result = findViewById(R.id.adminBottomNavigation)
                result.setOnNavigationItemSelectedListener {
                    when (it.itemId) {
                        R.id.nav_cities -> replaceFragment(adminCities)
                        R.id.nav_managers -> replaceFragment(adminManagers)
                        R.id.nav_stores -> replaceFragment(adminStores)
                    }
                    true
                }
                (applicationContext as AppServices).serverData.getManagersList()
                (applicationContext as AppServices).serverData.getStoresList()
                (applicationContext as AppServices).serverData.getCitiesList()


            }
            "MNGR" -> {
                result = findViewById(R.id.managersBottomNavigation)
                result.setOnNavigationItemSelectedListener {
                    when (it.itemId) {
                        R.id.nav_orders -> replaceFragment(managerOrders)
                        R.id.nav_couriers -> replaceFragment(managerCouriers)
                        R.id.nav_map -> replaceFragment(managerMap)
                        R.id.nav_profile -> replaceFragment(managerProfile)
                    }
                    true
                }
                (applicationContext as AppServices).serverData.getOrdersList()
                (applicationContext as AppServices).serverData.getGoodDialogList()
                (applicationContext as AppServices).serverData.getCouriersList()
                (applicationContext as AppServices).serverData.getStoresList()
                (applicationContext as AppServices).serverData.getCitiesList()
            }
            else -> {
                result = findViewById(R.id.couriersBottomNavigation)
                result.setOnNavigationItemSelectedListener {
                    when (it.itemId) {
                        R.id.nav_orders -> replaceFragment(managerOrders)
                        R.id.nav_backpack -> replaceFragment(courierGoods)
                        R.id.nav_map -> replaceFragment(managerMap)
                        R.id.nav_profile -> replaceFragment(managerProfile)
                    }
                    true
                }
                (applicationContext as AppServices).serverData.getOrdersList()
                (applicationContext as AppServices).serverData.getCourierBackpackList()
                (applicationContext as AppServices).serverData.getStoresList()
                (applicationContext as AppServices).serverData.getCitiesList()
            }
        }
        result.isVisible = true

        return result
    }
}