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
    private lateinit var btnNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    fun init() {
        mContext = this
        editText = findViewById(R.id.editText)
        btnNext = findViewById(R.id.btnNext)

        editText.setText("lkbutterand-ec0064a8d169438083e1c2ebacf91d25")
        editText.setHint("Enter broadcastId")
        editText.inputType = InputType.TYPE_CLASS_TEXT
        editText.maxLines = 1


        btnNext.setOnClickListener {
            val url = editText.text.toString()
            val intent = Intent(mContext, SecondActivity::class.java)
            intent.putExtra("broadcastId", url)
            startActivity(intent)
        }
    }
}