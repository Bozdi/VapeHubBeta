package com.bozdi.vapehubbeta

import android.content.Context
import android.util.Log
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

interface CreateUserCallBack {
    fun onSuccess()
    fun onError(text: String)
}

interface EditCityCallback {
    fun onSuccess()
    fun onError(text: String)
}

interface ActualCitiesListCallBack {
    fun onSuccess(ids: Array<String>, names: Array<String>)
    fun onError(text: String)
}

interface StorageDateListCallBack {
    fun onSuccess(Street: String)
    fun onError(text: String)
}

interface GetCityNameCallBack {
    fun onSuccess(Name: String)
    fun onError(text: String)
}

interface GetUserDataCallBack {
    fun onSuccess()
    fun onError(text: String)
}

class ServerData(_context: Context) {

    var globVar: GlobalVars = GlobalVars
    private var okHttpClient: OkHttpClient = OkHttpClient()
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
            override fun onFailure(call: Call, e: IOException) {
                Log.e("json", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string().toString()
                Log.i("OrderList JSON", body)
                val objects: JSONObject = JSONTokener(body).nextValue() as JSONObject
                val key: JSONArray? = objects.names()
                if (key != null) {
                    for (i in 0 until key.length()) {
                        val keys = key.getString(i)
                        val value: JSONObject = objects.getJSONObject(keys)
                        var status = "Ожидает"
                        when (value.getString("Status")) {
                            "PEND" -> status = "Ожидает"
                            "ACCEPT" -> status = "В работе"
                            "DONE" -> status = "Завершён"
                        }
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
                                status,
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
            override fun onFailure(call: Call, e: IOException) {
                Log.e("json", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string().toString()
                Log.e("list", body)
                val objects: JSONObject = JSONTokener(body).nextValue() as JSONObject
                val key: JSONArray? = objects.names()
                if (key != null) {
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
            override fun onFailure(call: Call, e: IOException) {
                Log.e("json", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string().toString()
                Log.e("list", body)
                val objects: JSONObject = JSONTokener(body).nextValue() as JSONObject
                val key: JSONArray? = objects.names()
                if (key != null) {
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


            }
        })
    }

    fun createManager(
        Type: String = "MNGR",
        Phone: String,
        Login: String,
        Password: String,
        Name: String,
        StoreId: String,
        actionListener: CreateUserCallBack
    ) {
        val formBody: RequestBody = FormBody.Builder()
            .add("Type", Type)
            .add("Phone", Phone)
            .add("Login", Login)
            .add("Password", Password)
            .add("Name", Name)
            .add("StoreId", StoreId)
            .build()
        val request: Request = Request.Builder()
            .url(globVar.URL + "users/")
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .addHeader("auth-token", globVar.token)
            .post(formBody)
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("json", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                Log.e("Code Create Order", response.code().toString())
                if (response.code() != 201) {
                    actionListener.onError("Ошибка добавления заказа")
                } else {
                    actionListener.onSuccess()
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
    ) {
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
            override fun onFailure(call: Call, e: IOException) {
                Log.e("json", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                Log.e("Code Create Order", response.code().toString())
                if (response.code() != 201) {
                    actionListener.onError("Ошибка добавления заказа")
                } else {
                    val body = response.body()?.string().toString()
                    val storeId = (JSONObject(body).getString("ID")).toString()
                    addGoodsOrder(goodsList, storeId, actionListener)
                }
            }
        })
    }
    fun editOrder(
        ClientName: String,
        ClientPhone: String,
        StreetName: String,
        BuildingNum: String,
        ApartNum: String,
        EntranceNum: String,
        OrderId: String?,
        actionListener: CreateOrderCallBack
    ) {
        val formBody: RequestBody = FormBody.Builder()
            .add("ClientName", ClientName)
            .add("ClientPhone", ClientPhone)
            .add("StreetName", StreetName)
            .add("BuildingNum", BuildingNum)
            .add("ApartNum", ApartNum)
            .add("EntranceNum", EntranceNum)
            .build()
        val request: Request = Request.Builder()
            .url(globVar.URL + "orders/" + OrderId + "/")
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .addHeader("auth-token", globVar.token)
            .put(formBody)
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("json", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                Log.e("Code Create Order", response.code().toString())
                if (response.code() != 201) {
                    actionListener.onError("Ошибка добавления заказа")
                } else {
                    actionListener.onSuccess()
                }
            }
        })
    }

    fun createStore(
        CityId: Int,
        Street: String,
        BuildingNumber: String,
        actionListener: CreateOrderCallBack
    ) {
        val formBody: RequestBody = FormBody.Builder()
            .add("Street", Street)
            .add("CityId", CityId.toString())
            .add("BuildingNumber", BuildingNumber)
            .build()
        val request: Request = Request.Builder()
            .url(globVar.URL + "stores/")
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .addHeader("auth-token", globVar.token)
            .post(formBody)
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("json", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                Log.e("Code Create Store", response.code().toString())
                if (response.code() != 201) {
                    actionListener.onError("Ошибка добавления заказа")
                } else {
                    actionListener.onSuccess()
                }
            }
        })
    }

    fun createCourier(
        Type: String = "COUR",
        Phone: String,
        Login: String,
        Password: String,
        Name: String,
        StoreId: String,
        actionListener: CreateUserCallBack
    ) {
        val formBody: RequestBody = FormBody.Builder()
            .add("Type", Type)
            .add("Phone", Phone)
            .add("Login", Login)
            .add("StoreId", StoreId)
            .add("Password", Password)
            .add("Name", Name)
            .build()
        val request: Request = Request.Builder()
            .url(globVar.URL + "users/")
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .addHeader("auth-token", globVar.token)
            .post(formBody)
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("json", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                Log.e("Code Create Order", response.code().toString())
                if (response.code() != 201) {
                    actionListener.onError("Ошибка создания курьера")
                } else {
                    actionListener.onSuccess()
                }
            }
        })
    }
    fun editUser(
        Login: String,
        Password: String,
        Name: String,
        Phone: String,
        StoreId: String,
        userId: String,
        actionListener: CreateUserCallBack
    ) {
        val formBody: RequestBody = FormBody.Builder()
            .add("Login", Login)
            .add("Password", Password)
            .add("Name", Name)
            .add("Phone", Phone)
            .add("StoreId", StoreId)
            .build()
        val request: Request = Request.Builder()
            .url(globVar.URL + "users/" + userId)
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .addHeader("auth-token", globVar.token)
            .put(formBody)
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("json", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                Log.e("Code Create Order", response.code().toString())
                if (response.code() != 201) {
                    actionListener.onError("Ошибка редактирования курьера")
                } else {
                    actionListener.onSuccess()
                }
            }
        })
    }
    fun editUserWithoutPassword(
        Login: String,
        // Password: String,
        Name: String,
        Phone: String,
        StoreId: String,
        userId: String,
        actionListener: CreateUserCallBack
    ) {
        val formBody: RequestBody = FormBody.Builder()
            .add("Login", Login)
            //   .add("Password", Password)
            .add("Name", Name)
            .add("Phone", Phone)
            .add("StoreId", StoreId)
            .build()
        val request: Request = Request.Builder()
            .url(globVar.URL + "users/" + userId)
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .addHeader("auth-token", globVar.token)
            .put(formBody)
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("json", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                Log.e("Code Create Order", response.code().toString())
                if (response.code() != 201) {
                    actionListener.onError("Ошибка редактирования курьера")
                } else {
                    actionListener.onSuccess()
                }
            }
        })
    }


    fun addGoodsOrder(
        goodsList: List<GoodsData>,
        storeId: String,
        actionListener: CreateOrderCallBack
    ) {
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
            override fun onFailure(call: Call, e: IOException) {
                Log.e("json", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                Log.e("Add goods Order", response.code().toString())
                if (response.code() != 201) {
                    actionListener.onError("Ошибка добавления заказа")
                } else {
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
            override fun onFailure(call: Call, e: IOException) {
                Log.e("json", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string().toString()
                Log.e("list", body)
                val objects: JSONObject = JSONTokener(body).nextValue() as JSONObject
                val key: JSONArray? = objects.names()
                if (key != null) {
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


            }
        })
    }

    fun getStoreData(
        id: String,
        action: StorageDateListCallBack
    ) {
        val request: Request = Request.Builder()
            .url(globVar.URL + "stores/" + id)
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .addHeader("auth-token", globVar.token)
            .get()
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("json", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string().toString()
                Log.e("getStoreData", body)
                val objects: JSONObject = JSONTokener(body).nextValue() as JSONObject

                action.onSuccess("${objects.getString("Street")} ${objects.getString("BuildingNumber")}")

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
            override fun onFailure(call: Call, e: IOException) {
                Log.e("json", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string().toString()
                Log.e("cities", body)
                val objects: JSONArray = JSONTokener(body).nextValue() as JSONArray
                for (i in 0 until objects.length()) {
                    val keys = objects.getString(i)
                    val value = JSONObject(keys)

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


    fun getActualCitiesList(
        action: ActualCitiesListCallBack
    ) {
        val request: Request = Request.Builder()
            .url(globVar.URL + "cities/")
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .addHeader("auth-token", globVar.token)
            .get()
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("json", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string().toString()
                Log.e("cities", body)
                val objects: JSONArray = JSONTokener(body).nextValue() as JSONArray
                val ids = mutableListOf<String>()
                val names = mutableListOf<String>()
                for (i in 0 until objects.length()) {
                    val keys = objects.getString(i)
                    val value = JSONObject(keys)


                    ids.add(value.getString("CityId"))

                    names.add(value.getString("Name"))
                }

                action.onSuccess(ids.toTypedArray(), names.toTypedArray())


            }
        })
    }
    fun getCityName(
        cityId : String,
        action: GetCityNameCallBack
    ) {
        val request: Request = Request.Builder()
            .url(globVar.URL + "cities/" + cityId)
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .addHeader("auth-token", globVar.token)
            .get()
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("json", e.toString())
            }
            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string().toString()
                Log.e("getCityName", body)
                val objects: JSONObject = JSONTokener(body).nextValue() as JSONObject

                action.onSuccess(objects.getString("Name"))
            }
        })
    }

    fun getUserType(
        action: GetUserDataCallBack
    ) {
        val request: Request = Request.Builder()
            .url(globVar.URL + "users/${globVar.UserId}/")
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .addHeader("auth-token", globVar.token)
            .get()
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("json", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val body: String = response.body()?.string().toString()
                Log.e("StoreId", body)
                globVar.UserType = (JSONObject(body).getString("Type")).toString()
                globVar.Login = (JSONObject(body).getString("Login")).toString()
                globVar.ProfileName = (JSONObject(body).getString("Name")).toString()
                globVar.ProfilePhoneNumber = (JSONObject(body).getString("Phone")).toString()
                if (globVar.UserType == "MNGR" || globVar.UserType == "COUR")
                    globVar.StoreId = (JSONObject(body).getInt("StoreID"))

                if (globVar.UserType == "MNGR" || globVar.UserType == "COUR") {
                    getStoreData(globVar.StoreId.toString(),
                        object : StorageDateListCallBack {
                            override fun onSuccess(Street: String) {

                                globVar.ProfileStoreName = Street
                                action.onSuccess()

                            }

                            override fun onError(text: String) {
                                action.onError(text)
                                TODO("Not yet implemented")
                            }


                        }
                    )

                } else { action.onSuccess()
//                    getStoreData("17",
//                        object : StorageDateListCallBack {
//                            override fun onSuccess(Street: String) {
//
//                                globVar.ProfileStoreName = Street
//                                action.onSuccess()
//                            }
//
//                            override fun onError(text: String) {
//                                action.onError(text)
//                                TODO("Not yet implemented")
//                            }
//
//
//                        }
//                    )
                }

            }
        })
    }


    fun createCity(
        Name: String,
        actionListener: CreateOrderCallBack
    ) {
        val formBody: RequestBody = FormBody.Builder()
            .add("Name", Name)
            .build()
        val request: Request = Request.Builder()
            .url(globVar.URL + "cities/")
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .addHeader("auth-token", globVar.token)
            .post(formBody)
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("json", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                Log.e("Code createCity", response.code().toString())
                if (response.code() != 201) {
                    actionListener.onError("Ошибка создания города")
                } else {
                    actionListener.onSuccess()
                }
            }
        })
    }
    fun editStore(
        storeId: String,
        CityId: Int,
        Street: String,
        BuildingNumber: String,
        actionListener: EditCityCallback
    ) {
        val formBody: RequestBody = FormBody.Builder()
            .add("CityId", CityId.toString())
            .add("Street", Street)
            .add("BuildingNumber", BuildingNumber)
            .build()
        val request: Request = Request.Builder()
            .url(globVar.URL + "stores/" + storeId)
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .addHeader("auth-token", globVar.token)
            .put(formBody)
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("json", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                Log.e("Code createCity", response.code().toString())
                if (response.code() != 201) {
                    actionListener.onError("Ошибка создания города")
                } else {
                    actionListener.onSuccess()
                }
            }
        })
    }

    fun deleteCity(
        Id: String,
        actionListener: CreateOrderCallBack
    ) {
        val formBody: RequestBody = FormBody.Builder()
            .build()
        val request: Request = Request.Builder()
            .url(globVar.URL + "cities/" + Id + "/")
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .addHeader("auth-token", globVar.token)
            .delete(formBody)
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("json", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                Log.e("Code createCity", response.code().toString())
                if (response.code() != 201) {
                    actionListener.onError("Ошибка создания города")
                } else {
                    actionListener.onSuccess()
                }
            }
        })
    }

    fun deleteOrder(
        OrderId: String?,
        actionListener: CreateOrderCallBack
    ) {
        val formBody: RequestBody = FormBody.Builder()
            .build()
        val request: Request = Request.Builder()
            .url(globVar.URL + "orders/" + OrderId + "/")
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .addHeader("auth-token", globVar.token)
            .delete(formBody)
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("json", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                Log.e("Code createCity", response.code().toString())
                if (response.code() != 201) {
                    actionListener.onError("Ошибка создания города")
                } else {
                    actionListener.onSuccess()
                }
            }
        })
    }

    fun deleteUser(
        Id: String,
        actionListener: CreateOrderCallBack
    ) {
        val formBody: RequestBody = FormBody.Builder()
            .build()
        val request: Request = Request.Builder()
            .url(globVar.URL + "users/" + Id + "/")
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .addHeader("auth-token", globVar.token)
            .delete(formBody)
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("json", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                Log.e("Code createCity", response.code().toString())
                if (response.code() != 204) {
                    actionListener.onError("Ошибка создания города")
                } else {
                    actionListener.onSuccess()
                }
            }
        })
    }

    fun deleteStore(
        Id: String,
        actionListener: CreateOrderCallBack
    ) {
        val formBody: RequestBody = FormBody.Builder()
            .build()
        val request: Request = Request.Builder()
            .url(globVar.URL + "stores/" + Id + "/")
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .addHeader("auth-token", globVar.token)
            .delete(formBody)
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("json", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                Log.e("Code createCity", response.code().toString())
                if (response.code() != 201) {
                    actionListener.onError("Ошибка создания города")
                } else {
                    actionListener.onSuccess()
                }
            }
        })
    }
    fun editCity(
        Id: String,
        Name: String,
        actionListener: CreateOrderCallBack
    ) {
        val formBody: RequestBody = FormBody.Builder()
            .add("Name", Name)
            .build()
        val request: Request = Request.Builder()
            .url(globVar.URL + "cities/" + Id + "/")
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .addHeader("auth-token", globVar.token)
            .post(formBody)
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("json", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                Log.e("Code Create Order", response.code().toString())
                if (response.code() != 201) {
                    actionListener.onError("Ошибка создания города")
                } else {
                    actionListener.onSuccess()
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
            override fun onFailure(call: Call, e: IOException) {
                Log.e("json", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string().toString()
                Log.e("list", body)
                val objects: JSONObject = JSONTokener(body).nextValue() as JSONObject
                val key: JSONArray? = objects.names()
                if (key != null) {
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
            override fun onFailure(call: Call, e: IOException) {
                Log.e("GoodDialog", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string().toString()
                Log.e("GoodDialog", body)
                val objects: JSONObject = JSONTokener(body).nextValue() as JSONObject
                val key: JSONArray? = objects.names()
                if (key != null) {
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


            }
        })
    }

fun acceptOrder(
    orderId: String,
    actionListener: CreateOrderCallBack
) {
    val formBody: RequestBody = FormBody.Builder()
        .build()
    val request: Request = Request.Builder()
        .url(globVar.URL + "orders/" + orderId + "/accept")
        .addHeader("Content-Type", "application/x-www-form-urlencoded")
        .addHeader("auth-token", globVar.token)
        .post(formBody)
        .build()
    okHttpClient.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.e("json", e.toString())
        }

        override fun onResponse(call: Call, response: Response) {
            Log.e("Code createCity", response.code().toString())
            if (response.code() != 204) {
                actionListener.onError("Ошибка создания города")
            } else {
                actionListener.onSuccess()
            }
        }
    })
}
    fun completeOrder(
        orderId: String,
        CashPayment: String,
        CardPayment: String,
        actionListener: CreateOrderCallBack
    ) {
        val formBody: RequestBody = FormBody.Builder()
            .add("CashPayment", CashPayment)
            .add("CardPayment", CardPayment)
            .build()
        val request: Request = Request.Builder()
            .url(globVar.URL + "orders/" + orderId + "/close/")
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .addHeader("auth-token", globVar.token)
            .post(formBody)
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("json", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string().toString()
                Log.e("Order Close JSON", body)
                Log.e("Code createCity", response.code().toString())
                if (response.code() != 204) {
                    actionListener.onError("Ошибка завершения заказа")
                } else {
                    actionListener.onSuccess()
                }
            }
        })
    }
}