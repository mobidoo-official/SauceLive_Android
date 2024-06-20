package com.mobidoo.saucelivesample

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText

class MainActivity : Activity() {
    private lateinit var mContext: Context
    private lateinit var editText: EditText
    private lateinit var btnNextLive: Button
    private lateinit var btnNextCollection: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    fun init() {
        mContext = this
        editText = findViewById(R.id.edittext)
        btnNextLive = findViewById(R.id.btn_next_live)
        btnNextCollection = findViewById(R.id.btn_next_collection)

        editText.setText("lkebay-43eb1c46292b4ce7aacadc1620821410")
        editText.setHint("Enter broadcastId")
        editText.inputType = InputType.TYPE_CLASS_TEXT
        editText.maxLines = 1

        btnNextLive.setOnClickListener {
            val url = editText.text.toString()
            val intent = Intent(mContext, SauceLiveActivity::class.java)
            intent.putExtra("broadcastId", url)
            startActivity(intent)
        }

        btnNextCollection.setOnClickListener {
            startActivity(
                Intent(
                    mContext,
                    SauceCollectionActivity::class.java
                )
            )
        }
    }
}