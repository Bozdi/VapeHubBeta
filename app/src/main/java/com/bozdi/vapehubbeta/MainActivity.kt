package com.bozdi.vapehubbeta

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bozdi.vapehubbeta.adminFragments.CitiesList
import com.bozdi.vapehubbeta.adminFragments.ManagersList
import com.bozdi.vapehubbeta.adminFragments.StoresList
import com.bozdi.vapehubbeta.courierFragments.CourierGoods
import com.bozdi.vapehubbeta.managerFragments.CouriersList
import com.bozdi.vapehubbeta.model.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import okhttp3.*

class MainActivity : AppCompatActivity() {

    var globVar: GlobalVars = GlobalVars
    var okHttpClient: OkHttpClient = OkHttpClient()

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
        replaceFragment(managerOrders)
        setBottomNavigation(GlobalVars.UserType);


    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    private fun setBottomNavigation(type: String): BottomNavigationView {
        val result : BottomNavigationView;

        when (type) {
            "ADMN" -> {
                result = findViewById(R.id.adminBottomNavigation);
                result.setOnNavigationItemSelectedListener {
                    when (it.itemId) {
                        R.id.nav_cities -> replaceFragment(adminCities)
                        R.id.nav_managers -> replaceFragment(adminManagers)
                        R.id.nav_stores -> replaceFragment(adminStores)
                    }
                    true
                }
                (getApplicationContext() as AppServices).serverData.getManagersList()
                (getApplicationContext() as AppServices).serverData.getStoresList()
                (getApplicationContext() as AppServices).serverData.getCitiesList()

            }
            "MNGR" -> {
                result = findViewById(R.id.managersBottomNavigation);
                result.setOnNavigationItemSelectedListener {
                    when (it.itemId) {
                        R.id.nav_orders -> replaceFragment(managerOrders)
                        R.id.nav_couriers -> replaceFragment(managerCouriers)
                        R.id.nav_map -> replaceFragment(managerMap)
                        R.id.nav_profile -> replaceFragment(managerProfile)
                    }
                    true
                }
                (getApplicationContext() as AppServices).serverData.getGoodDialogList()
                (getApplicationContext() as AppServices).serverData.getOrdersList()
                (getApplicationContext() as AppServices).serverData.getCouriersList()
            }
            else -> {
                result = findViewById(R.id.couriersBottomNavigation);
                result.setOnNavigationItemSelectedListener {
                    when (it.itemId) {
                        R.id.nav_orders -> replaceFragment(managerOrders)
                        R.id.nav_backpack -> replaceFragment(courierGoods)
                        R.id.nav_map -> replaceFragment(managerMap)
                        R.id.nav_profile -> replaceFragment(managerProfile)
                    }
                    true
                }
                (getApplicationContext() as AppServices).serverData.getOrdersList()
                (getApplicationContext() as AppServices).serverData.getCourierBackpackList()
            }
        }
        result.isVisible = true;

        return result;
    }
}