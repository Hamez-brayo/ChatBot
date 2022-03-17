package com.example.chatbot

class MessageModal {
    // string to store our message and sender
    private var message: String? = null
    private var sender: String? = null

    // constructor.
    fun MessageModal(message: String?, sender: String?) {
        this.message = message
        this.sender = sender
    }

    // getter and setter methods.
    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String?) {
        this.message = message
    }

    fun getSender(): String? {
        return sender
    }

    fun setSender(sender: String?) {
        this.sender = sender
    }
}