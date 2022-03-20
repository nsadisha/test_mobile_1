package com.nsadisha.test1

import android.annotation.SuppressLint
import android.os.Bundle
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.TextView
import androidx.core.view.get
import com.google.android.material.snackbar.Snackbar
import com.nsadisha.test1.api.UserAPIService
import com.nsadisha.test1.model.User

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private val userAPIService = UserAPIService.create()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            val id = view.findViewById<EditText>(R.id.textField).text
            val user = userAPIService.getUser(id.toString())
            user.enqueue(object : Callback<User>{
                @SuppressLint("WrongConstant")
                override fun onResponse(call: Call<User>, response: Response<User>){
                    val body = response.body()
                    body?.let{
                        view.findViewById<TextView>(R.id.userTitle).visibility = 1
                        view.findViewById<TextView>(R.id.name).text = it.name
                        view.findViewById<TextView>(R.id.email).text = it.email
                        view.findViewById<TextView>(R.id.username).text = it.username
                        view.findViewById<TextView>(R.id.website).text = it.website
                    }
                }
                @SuppressLint("WrongConstant")
                override fun onFailure(call: Call<User>, t: Throwable){
                    Snackbar.make(view, "Please enter a valid ID!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()

                    view.findViewById<TextView>(R.id.userTitle).visibility = 0
                    view.findViewById<TextView>(R.id.name).text = ""
                    view.findViewById<TextView>(R.id.email).text = ""
                    view.findViewById<TextView>(R.id.username).text = ""
                    view.findViewById<TextView>(R.id.website).text = ""
                }
            })
        }
    }
}