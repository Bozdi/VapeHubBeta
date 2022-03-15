package com.bozdi.vapehubbeta

import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.bozdi.vapehubbeta.databinding.ActivityMainBinding
import com.bozdi.vapehubbeta.databinding.FragmentManagerOrdersBinding
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    var okHttpClient: OkHttpClient = OkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val button: Button = findViewById(R.id.loginButton)
        button.setOnClickListener{
            val login: EditText = findViewById(R.id.editTextLogin)
            val password: EditText = findViewById(R.id.editTextPassword)

            val formBody: RequestBody =  FormBody.Builder()
                .add("login", login.text.toString())
                .add("password", password.text.toString())
                .build()
            val request: Request = Request.Builder()
                .url("http://178.20.45.151:3000/api/auth")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .post(formBody)
                .build()
            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call?, e: IOException?) {
                    Log.e("json", e.toString())
                }

                override fun onResponse(call: Call?, response: Response?) {
                    Log.e("json", response?.code().toString())
                    Log.e("json", response?.body()?.string().toString() )

                    if (response?.code().toString() != "200")
                    {
//                        Toast.makeText(applicationContext, "Неверный логин или пароль", Toast.LENGTH_SHORT);
                    } else{
                        val intent = Intent(this@MainActivity, LoginActivity::class.java);
                        startActivity(intent)
                    }
                }
            })
//            Toast.makeText(this, "login: " + login.text + " password: " + password.text , Toast.LENGTH_SHORT).show()
        }

    }


}