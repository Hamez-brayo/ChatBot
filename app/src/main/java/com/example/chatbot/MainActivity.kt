package com.example.chatbot

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException


class MainActivity : AppCompatActivity() {
    // creating variables for our
    // widgets in xml file.
    var chatsRV: RecyclerView? = null
    var sendMsgIB: ImageButton? = null
    var userMsgEdt: EditText? = null
    var USER_KEY = "user"
    val BOT_KEY = "bot"

    // creating a variable for
    // our volley request queue.
    var mRequestQueue: RequestQueue? = null

    // creating a variable for array list and adapter class.
    var messageModalArrayList: ArrayList<MessageModal>? = null
    var messageRVAdapter: MessageRVAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // on below line we are initializing all our views.
        //chatsRV = findViewById(R.id.idRVChats)
       // sendMsgIB = findViewById(R.id.idIBSend)
       // userMsgEdt =findViewById(R.id.idEdtMessage)

        // below line is to initialize our request queue.
        mRequestQueue = Volley.newRequestQueue(this@MainActivity)
        mRequestQueue!!.cache.clear()

        // creating a new array list
        messageModalArrayList = ArrayList()

        // adding on click listener for send message button.
        sendMsgIB!!.findViewById<ImageButton>(R.id.idIBSend).setOnClickListener(View.OnClickListener { // checking if the message entered
            // by user is empty or not.
            if (userMsgEdt!!.findViewById<EditText>(R.id.idEdtMessage).getText().toString().isEmpty()) {
                // if the edit text is empty display a toast message.
                Toast.makeText(this@MainActivity, "Please enter your message..", Toast.LENGTH_SHORT)
                    .show()
                return@OnClickListener
            }

            // calling a method to send message
            // to our bot to get response.
            sendMessage(userMsgEdt!!.findViewById<EditText>(R.id.idEdtMessage).getText().toString())

            // below line we are setting text in our edit text as empty
            userMsgEdt!!.findViewById<EditText>(R.id.idEdtMessage).setText("")
        })

        // on below line we are initialing our adapter class and passing our array list to it.
        messageRVAdapter = MessageRVAdapter(messageModalArrayList!!, this)

        // below line we are creating a variable for our linear layout manager.
        val linearLayoutManager =
            LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)

        // below line is to set layout
        // manager to our recycler view.
        chatsRV!!.findViewById<RecyclerView>(R.id.idRVChats).setLayoutManager(linearLayoutManager)

        // below line we are setting
        // adapter to our recycler view.
        chatsRV!!.findViewById<RecyclerView>(R.id.idRVChats).setAdapter(messageRVAdapter)
    }

    private fun sendMessage(userMsg: String) {
        // below line is to pass message to our
        // array list which is entered by the user.
        messageModalArrayList!!.add(MessageModal(userMsg, USER_KEY))
        messageRVAdapter!!.notifyDataSetChanged()

        // url for our brain
        // make sure to add mshape for uid.
        // make sure to add your url.
        val url ="http://api.brainshop.ai/get?bid=164812&key=M7H2jAlLL0HushUo&uid=[uid]&msg=[msg]$userMsg"

        // creating a variable for our request queue.
        val queue = Volley.newRequestQueue(this@MainActivity)

        // on below line we are making a json object request for a get request and passing our url .
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    // in on response method we are extracting data
                    // from json response and adding this response to our array list.
                    val botResponse = response.getString("cnt")
                    messageModalArrayList!!.add(MessageModal(botResponse, BOT_KEY))

                    // notifying our adapter as data changed.
                    messageRVAdapter!!.notifyDataSetChanged()
                } catch (e: JSONException) {
                    e.printStackTrace()

                    // handling error response from bot.
                    messageModalArrayList!!.add(MessageModal("No response", BOT_KEY))
                    messageRVAdapter!!.notifyDataSetChanged()
                }
            }) { // error handling.
            messageModalArrayList!!.add(MessageModal("Sorry no response found", BOT_KEY))
            Toast.makeText(this@MainActivity, "No response from the bot..", Toast.LENGTH_SHORT)
                .show()
        }

        // at last adding json object
        // request to our queue.
        queue.add(jsonObjectRequest)
    }
}