package com.bozdi.vapehubbeta

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import java.io.IOException


class OrdersList : Fragment() {
    var globVar: GlobalVars = GlobalVars
    var okHttpClient: OkHttpClient = OkHttpClient()
    var listOrders = mutableListOf<ItemSizeDataModel>()
    var start: Boolean = true;
    data class ItemSizeDataModel(
//mutableListOf<ItemSizeDataModel>()
        val OrderId: String?,
        val From: String,
        val Status: String,
        val TotalCost: String,
    )

    var fragmentManagerOrders: View? = null;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        fragmentManagerOrders = inflater.inflate(R.layout.fragment_manager_orders, container, false)
        getlist();
        return fragmentManagerOrders
    }

    fun getlist()
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
                val objects:JSONObject = JSONTokener(body).nextValue() as JSONObject
                val key: JSONArray = objects.names()
                for (i in 0 until key.length()) {
                    val keys = key.getString(i)
                    val value: JSONObject = objects.getJSONObject(keys)
                    var item : ItemSizeDataModel = ItemSizeDataModel(
                        value.getString("OrderId"),
                        value.getString("StreetName"),
                        value.getString("Status"),
                        "5000"
                    );
                    Log.i(value.getString("StreetName"),value.getString("StreetName"));
                    listOrders.add(item)
                }

                setData()


            }
        })
    }

    fun setData()
    {
        val List = view?.findViewById<LinearLayout>(R.id.Order_List)
        val inflaters = LayoutInflater.from(view?.getContext())
        for (item in listOrders) {
            var status: String =  item.Status /*when (item.Status) {
                "PEND" -> "Ожидает"
                "ACCEPT" -> "В работе"
                "DONE" -> "Доставлен"
                else -> "Ошибка"
            }*/

            val view: View = inflaters.inflate(R.layout.order_item, List, false)
            view.findViewById<TextView>(R.id.orderIdTextView).setText("Заказ #${item.OrderId}");
            view.findViewById<TextView>(R.id.orderStatusTextView).setText(status);
            view.findViewById<TextView>(R.id.orderAdressTextView).setText(item.From);
            view.findViewById<TextView>(R.id.orderTotalCostTextView).setText(item.TotalCost);
            List?.addView(view);
        }
    }
}
