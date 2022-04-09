package com.bozdi.vapehubbeta

import android.content.Context
import android.util.Log
import com.bozdi.vapehubbeta.model.*
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import java.io.IOException


class ServerData(_context: Context) {

    var globVar: GlobalVars = GlobalVars
    var okHttpClient: OkHttpClient = OkHttpClient()
    var context: Context? = null
    init {
        context = _context
    }

    fun getOrdersList() {
        val request: Request = Request.Builder()
            .url(globVar.URL + "orders/")
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
                    (context as AppServices).ordersService.add(
                        OrdersData(
                            value.getString("OrderId"),
                            value.getString("OrderLink"),
                            value.getString("ClientName"),
                            value.getString("StoreId"),
                            value.getString("StoreLink"),
                            value.getString("UserID"),
                            value.getString("ClientPhone"),
                            value.getString("StreetName"),
                            value.getString("BuildingNum"),
                            value.getString("ApartNum"),
                            value.getString("EntranceNum"),
                            value.getString("TargetTime"),
                            value.getString("Status"),
                            value.getString("GMapPlaceID"),
                            value.getString("Lat"),
                            value.getString("Lng"),
                            value.getString("TotalCost"),
                            value.getString("DeliveryCost"),
                            value.getString("GoodsTotalCost"),
                            value.getString("CashPayment"),
                            value.getString("CardPayment"),
                            value.getString("PayedFromCourier"),
                            value.getString("DeliveredTime"),
                            value.getString("CreateAt")
                        )
                    )
                    Log.i(value.getString("StreetName"), value.getString("StreetName"));
                }


            }
        })
    }

    fun getCouriersList() {
        val request: Request = Request.Builder()
            .url(globVar.URL + "users/")
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

                    (context as AppServices).couriersService.add(
                        CouriersData(
                            value.getString("UserID"),
                            value.getString("Login"),
                            value.getString("StoreID"),
                            value.getString("StoreLink"),
                            value.getString("Name"),
                            value.getString("Phone"),
                            value.getString("Type"),
                            value.getString("Status"),
                        )
                    )
                }


            }
        })
    }

    fun getManagersList() {
        val request: Request = Request.Builder()
            .url(globVar.URL + "users/")
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

                    (context as AppServices).managersService.add(
                        ManagersData(
                            value.getString("UserID"),
                            value.getString("Login"),
                            value.getString("StoreID"),
                            value.getString("StoreLink"),
                            value.getString("Name"),
                            value.getString("Phone"),
                            value.getString("Type"),
                            value.getString("Status"),
                        )
                    )
                }


            }
        })
    }

    fun getStoresList() {
        val request: Request = Request.Builder()
            .url(globVar.URL + "stores/")
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

                    (context as AppServices).storesService.add(
                        StoresData(
                            value.getString("StoreId"),
                            value.getString("CityId"),
                            value.getString("CityLink"),
                            value.getString("Street"),
                            value.getString("BuildingNumber"),
                            value.getString("PlaceId"),
                        )
                    )
                }


            }
        })
    }

    fun getCitiesList() {
        val request: Request = Request.Builder()
            .url(globVar.URL + "cities/")
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
                Log.e("cities", body)
                val objects: JSONObject = JSONTokener(body).nextValue() as JSONObject
                val key: JSONArray = objects.names()
                for (i in 0 until key.length()) {
                    val keys = key.getString(i)
                    val value: JSONObject = objects.getJSONObject(keys)

                    (context as AppServices).citiesService.add(
                        CitiesData(
                            value.getString("CityId"),
                            value.getString("Name"),
                        )
                    )
                }


            }
        })
    }

    fun getCourierBackpackList() {
        val request: Request = Request.Builder()
            .url(globVar.URL + "goods/")
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

                    (context as AppServices).courierGoodsService.add(
                        CourierGoodsData(
                            value.getString("GoodID"),
                            value.getString("GoodLink"),
                            value.getString("Name"),
                            value.getString("Price"),
                            value.getString("Available")
                        )
                    )
                }


            }
        })
    }
}