package com.bozdi.vapehubbeta

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class LoginActivity : AppCompatActivity() {
    var globVar: GlobalVars = GlobalVars
    var okHttpClient: OkHttpClient = OkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val login: EditText = findViewById(R.id.editTextLogin)
        val password: EditText = findViewById(R.id.editTextPassword)
        login.setText("NewUser");
        password.setText("1234");
        val button: Button = findViewById(R.id.loginButton)
        button.setOnClickListener {

            val formBody: RequestBody = FormBody.Builder()
                .add("login", login.text.toString())
                .add("password", password.text.toString())
                .build()
            val request: Request = Request.Builder()
                .url(globVar.URL + "auth/")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .post(formBody)
                .build()
            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call?, e: IOException?) {
                    Log.e("json", e.toString())
                }

                override fun onResponse(call: Call?, response: Response?) {
                    var body: String = response?.body()?.string().toString();
                    if (response?.code().toString() != "200") {
                        Log.e("HTTP", "Error Auth")
//                        Toast.makeText(this@LoginActivity,"Неверный логин или пароль",Toast.LENGTH_SHORT).show();
//                        Toast.makeText(applicationContext, "Неверный логин или пароль", Toast.LENGTH_SHORT);
                    } else {
                        globVar.token = (JSONObject(body).getString("AuthToken")).toString();
                        globVar.UserId = (JSONObject(body).getString("UserId")).toString();
                        Log.i("token", globVar.token)
                        getUserType()
                    }
                }
            })
        }

    }

    public fun getUserType() {
        val request: Request = Request.Builder()
            .url(globVar.URL + "users/${globVar.UserId}/")
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .addHeader("auth-token", globVar.token)
            .get()
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                Log.e("json", e.toString())
            }

            override fun onResponse(call: Call?, response: Response?) {
                var body: String = response?.body()?.string().toString()
                Log.e("StoreId", body)
                globVar.UserType = (JSONObject(body).getString("Type")).toString()
                globVar.Login = (JSONObject(body).getString("Login")).toString()
                globVar.ProfileName = (JSONObject(body).getString("Name")).toString()
                globVar.ProfilePhoneNumber = (JSONObject(body).getString("Phone")).toString()
                globVar.StoreId = JSONObject(body).getInt("StoreID")
                Log.e("StoreId", globVar.StoreId.toString())

                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
            }
        })
    }


}