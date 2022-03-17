package com.example.chatbot

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class MessageRVAdapter     // constructor class.
    (// variable for our array list and context.
    private val messageModalArrayList: ArrayList<MessageModal>, private val context: Context
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder?>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        when (viewType) {
            0 -> {
                // below line we are inflating user message layout.
                view = LayoutInflater.from(parent.context).inflate(R.layout.user_msg, parent, false)
                return UserViewHolder(view)
            }
            1 -> {
                // below line we are inflating bot message layout.
                view = LayoutInflater.from(parent.context).inflate(R.layout.bot_msg, parent, false)
                return BotViewHolder(view)
            }
        }
        return RecyclerView.ViewHolder
    }


    override fun getItemCount(): Int {
        // return the size of array list
        return messageModalArrayList.size
    }

    override fun getItemViewType(position: Int): Int {
        // below line of code is to set position.
        return when (messageModalArrayList[position].getSender()) {
            "user" -> 0
            "bot" -> 1
            else -> -1
        }
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // creating a variable
        // for our text view.
        var userTV: TextView

        init {
            // initializing with id.
            userTV = itemView.findViewById(R.id.idTVUser)
        }
    }

    class BotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // creating a variable
        // for our text view.
        var botTV: TextView

        init {
            // initializing with id.
            botTV = itemView.findViewById(R.id.idTVBot)
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val modal = messageModalArrayList[position]
        when (modal.getSender()) {
            "user" ->                 // below line is to set the text to our text view of user layout
                (holder as UserViewHolder).userTV.text = modal.getMessage()
            "bot" ->                 // below line is to set the text to our text view of bot layout
                (holder as BotViewHolder).botTV.text = modal.getMessage()
        }
    }
}