package com.bozdi.vapehubbeta

import android.content.Context
import android.util.Log
import com.bozdi.vapehubbeta.adapters.SelectedGoodsActionListener
import com.bozdi.vapehubbeta.model.*
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import java.io.IOException

interface CreateOrderCallBack {
    fun onSuccess()
    fun onError(text: String)
}

class ServerData(_context: Context) {

    var globVar: GlobalVars = GlobalVars
    var okHttpClient: OkHttpClient = OkHttpClient()
    var context: Context? = null
    var lastOrderNumber: Int = -1;

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
                Log.i("OrderList JSON", body)
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
                            value.getString("UserId"),
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
                    Log.i(value.getString("StreetName"), value.getString("StreetName"))
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

    fun createOrder() {
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

    fun createOrder(
        StoreId: Int,
        StreetName: String,
        BuildingNum: String,
        ApartNum: Int,
        goodsList: List<GoodsData>,
        ClientName: String = "",
        ClientPhone: String = "",
        EntranceNum: String = "",
        TargetTime: Int = 0,
        actionListener: CreateOrderCallBack
    ){
        val formBody: RequestBody = FormBody.Builder()
            .add("StoreId", StoreId.toString())
            .add("StreetName", StreetName)
            .add("BuildingNum", BuildingNum)
            .add("ApartNum", ApartNum.toString())
            .add("ClientName", ClientName)
            .add("ClientPhone", ClientPhone)
            .add("EntranceNum", EntranceNum)
            .add("TargetTime", TargetTime.toString())
            .build()
        val request: Request = Request.Builder()
            .url(globVar.URL + "orders/")
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .addHeader("auth-token", globVar.token)
            .post(formBody)
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                Log.e("json", e.toString())
            }

            override fun onResponse(call: Call?, response: Response?) {
                Log.e("Code Create Order", response?.code().toString());
                if (response?.code() != 201)
                {
                    actionListener.onError("Ошибка добавления заказа")
                } else{
                    val body = response.body()?.string().toString()
                    val storeId = (JSONObject(body).getString("ID")).toString()
                    addGoodsOrder(goodsList, storeId, actionListener)
                }
            }
        })
    }

    fun addGoodsOrder(goodsList: List<GoodsData>, storeId: String, actionListener: CreateOrderCallBack){
        val formBody: FormBody.Builder = FormBody.Builder()

        goodsList.forEach {
            formBody.add(it.GoodId.toString(), it.Available.toString())
        }

        val request: Request = Request.Builder()
            .url(globVar.URL + "orders/" + storeId + "/goods")
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .addHeader("auth-token", globVar.token)
            .post(formBody.build())
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                Log.e("json", e.toString())
            }

            override fun onResponse(call: Call?, response: Response?) {
                Log.e("Add goods Order", response?.code().toString());
                if (response?.code() != 201)
                {
                    actionListener.onError("Ошибка добавления заказа")
                } else{
                    actionListener.onSuccess()
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
                val objects: JSONArray = JSONTokener(body).nextValue() as JSONArray
                //val key: JSONArray = objects.names()
                for (i in 0 until objects.length()) {
                    val keys = objects.getString(i)
                    val value: JSONObject = JSONObject(keys);

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
                            value.getString("GoodId"),
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

    fun getGoodDialogList() {
        val request: Request = Request.Builder()
            .url(globVar.URL + "goods/")
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .addHeader("auth-token", globVar.token)
            .get()
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                Log.e("GoodDialog", e.toString())
            }

            override fun onResponse(call: Call?, response: Response?) {
                var body = response?.body()?.string().toString()
                Log.e("GoodDialog", body)
                val objects: JSONObject = JSONTokener(body).nextValue() as JSONObject
                val key: JSONArray = objects.names()
                for (i in 0 until key.length()) {
                    val keys = key.getString(i)
                    val value: JSONObject = objects.getJSONObject(keys)

                    (context as AppServices).goodsDialogService.add(
                        GoodsData(
                            value.getString("GoodId"),
                            value.getString("GoodLink"),
                            value.getString("Name"),
                            value.getString("Price"),
                            value.getString("Available"),
                            value.getInt("Available"),
                        )
                    )
                }


            }
        })
    }
}