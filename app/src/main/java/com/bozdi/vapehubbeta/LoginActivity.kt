package com.bozdi.vapehubbeta

import android.app.PendingIntent.getActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bozdi.vapehubbeta.managerFragments.CouriersList
import com.bozdi.vapehubbeta.model.Order
import com.google.android.material.bottomnavigation.BottomNavigationView
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import java.io.IOException

class LoginActivity : AppCompatActivity() {

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

        getData()
        lateinit var bottomNavigationView: BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
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
    fun getData()
    {
        val request: Request = Request.Builder()
            .url(globVar.URL +"orders/")
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .addHeader("auth-token", globVar.token)
            .get()
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                Log.e("json", e.toString())
            }

            override fun onResponse(call: Call?, response: Response?) {
                var body = response?.body()?.string().toString()
                Log.e("list", body)
                val objects: JSONObject = JSONTokener(body).nextValue() as JSONObject
                val key: JSONArray = objects.names()
                for (i in 0 until key.length()) {
                    val keys = key.getString(i)
                    val value: JSONObject = objects.getJSONObject(keys)
                    (getApplicationContext() as App).ordersService.addOrder( Order(
                        value.getString("OrderId"),
                        value.getString("StreetName"),
                        value.getString("Status"),
                        "5000"
                    )
                    )
                    Log.i(value.getString("StreetName"),value.getString("StreetName"));
                }


            }
        })
    }

}