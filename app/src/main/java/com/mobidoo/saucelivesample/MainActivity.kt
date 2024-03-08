package com.mobidoo.saucelivesample

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mobidoo.saucelivesample.ui.theme.SauceLiveSampleTheme

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

        editText.setText("https://stage.player.sauceflex.com/broadcast/lkbutterand-2cd937d7220240d080bc3fce51c2e1d9")
        editText.setHint("Enter URL")
        editText.inputType = InputType.TYPE_CLASS_TEXT
        editText.maxLines = 1



        btnNext.setOnClickListener {
            val url = editText.text.toString()
            val intent = Intent(mContext, SecondActivity::class.java)
            intent.putExtra("linkUrl", url)
            startActivity(intent)
        }
    }
}