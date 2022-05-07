package com.bozdi.vapehubbeta

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class LoginActivity : AppCompatActivity() {
    var globVar: GlobalVars = GlobalVars
    private var okHttpClient: OkHttpClient = OkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

            }
        })

        val login: EditText = findViewById(R.id.editTextLogin)
        val password: EditText = findViewById(R.id.editTextPassword)
      //  login.setText("Courier")
      //  password.setText("Courier")
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
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("Login json", e.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    val body: String = response.body()?.string().toString()
                    if (response.code().toString() != "200") {
                        Log.e("HTTP", "Error Auth")

                    } else {
                        globVar.token = (JSONObject(body).getString("AuthToken")).toString()
                        globVar.UserId = (JSONObject(body).getString("UserId")).toString()
                        Log.i("token", globVar.token)
                        getUserType()
                    }
                }
            })
        }

    }

    private fun getUserType()
    {
        (applicationContext as AppServices).serverData.getUserType(
            object : GetUserDataCallBack {
                override fun onSuccess() {
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                }

                override fun onError(text: String) {
                    TODO("Not yet implemented")
                }


            }
        )
    }


}