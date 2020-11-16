package com.example.retrofit_coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        var r = Retrofit.Builder()
            .baseUrl("https://reqres.in/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = r.create(ReqResAPI::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getUsers()
                GlobalScope.launch(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "RequestSuccess", Toast.LENGTH_SHORT).show()
                    response.body()?.users?.let { printUsers(it) }
                }

            } catch (e: Exception) {
                GlobalScope.launch(Dispatchers.IO) {
                    Toast.makeText(this@MainActivity, "Request Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

        private fun printUsers(users: List<UsersResponse.User>) {
            lvPeople.adapter = ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                users.map { it.firstName }
            )
        }
    }
