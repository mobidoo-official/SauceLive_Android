package com.mobidoo.saucelivesample

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.mobidoo.saucelive.Member
import com.mobidoo.saucelive.SauceLive

class SauceLiveActivity : Activity() {
    private lateinit var mContext: Context
    private lateinit var btnSauceviewActivity: Button
    private lateinit var btnSauceActivity: Button
    private lateinit var btnSauceActivityPip: Button
    private lateinit var broadcastId: String
    private lateinit var onEnter: CheckBox
    private lateinit var onMoveExit: CheckBox
    private lateinit var onMoveLogin: CheckBox
    private lateinit var onShare: CheckBox
    private lateinit var onPictureInPicture: CheckBox
    private lateinit var onMoveBanner: CheckBox
    private lateinit var onWebviewReloading: CheckBox
    private lateinit var onMoveReward: CheckBox
    private lateinit var onMoveProduct: CheckBox
    private lateinit var onTokenError: CheckBox
    private lateinit var radioGroup: RadioGroup
    private lateinit var radioDev: RadioButton
    private lateinit var radioStage: RadioButton
    private lateinit var radioPrd: RadioButton
    private lateinit var edittext: EditText

    private var queryString: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sauce_live)
        broadcastId = intent.getStringExtra("broadcastId") ?: ""
        init()
    }

    fun init() {
        mContext = this
        btnSauceviewActivity = findViewById(R.id.btn_sauceview_activity)
        btnSauceActivity = findViewById(R.id.btn_sauce_activity)
        btnSauceActivityPip = findViewById(R.id.btn_sauce_activity_pip)
        onEnter = findViewById(R.id.check_Enter)
        onMoveExit = findViewById(R.id.check_MoveExit)
        onMoveLogin = findViewById(R.id.check_MoveLogin)
        onShare = findViewById(R.id.check_OnShare)
        onPictureInPicture = findViewById(R.id.check_PictureInPicture)
        onMoveBanner = findViewById(R.id.check_MoveBanner)
        onWebviewReloading = findViewById(R.id.check_WebviewReloading)
        onMoveReward = findViewById(R.id.check_MoveReward)
        onMoveProduct = findViewById(R.id.check_MoveProduct)
        onTokenError = findViewById(R.id.check_TokenError)
        radioGroup = findViewById(R.id.radio_group)
        radioDev = findViewById(R.id.radio_dev)
        radioStage = findViewById(R.id.radio_stage)
        radioPrd = findViewById(R.id.radio_prod)
        edittext = findViewById(R.id.edittext)

        edittext.setHint("Enter query string here")

        btnSauceviewActivity.setOnClickListener() {
            val intent = Intent(this, SauceViewActivity::class.java)
            intent.putExtra("broadcastId", broadcastId)
            intent.putExtra(
                "urlPrefix",
                if (radioDev.isChecked) "dev." else if (radioStage.isChecked) "stage." else ""
            )
            queryString = edittext.text.toString()
            intent.putExtra("queryString", queryString)

            SauceViewActivity.onEnter = onEnter.isChecked
            SauceViewActivity.onMoveExit = onMoveExit.isChecked
            SauceViewActivity.onMoveLogin = onMoveLogin.isChecked
            SauceViewActivity.onShare = onShare.isChecked
            SauceViewActivity.onPictureInPicture = onPictureInPicture.isChecked
            SauceViewActivity.onMoveBanner = onMoveBanner.isChecked
            SauceViewActivity.onWebviewReloading = onWebviewReloading.isChecked
            SauceViewActivity.onMoveReward = onMoveReward.isChecked
            SauceViewActivity.onMoveProduct = onMoveProduct.isChecked
            SauceViewActivity.onTokenError = onTokenError.isChecked
            startActivity(intent)
        }

        btnSauceActivity.setOnClickListener {
            queryString = edittext.text.toString()
            SauceLive.openPipActivity(
                mContext, broadcastId, false,
                null,
                Member("memberId", "nickName", "30", "m", null),
                //onEnter
                if (onEnter.isChecked) {
                    {
                        Toast.makeText(mContext, "onEnter", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    null
                },
                //onMoveExit
                if (onMoveExit.isChecked) {
                    {
                        Toast.makeText(mContext, "onMoveExit", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    null
                },
                //onMoveLogin
                if (onMoveLogin.isChecked) {
                    {
                        Toast.makeText(mContext, "onMoveLogin", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    null
                },
                //onShare
                if (onShare.isChecked) {
                    { message ->
                        Toast.makeText(mContext, "onShare", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    null
                },
                //onPictureInPicture
                if (onPictureInPicture.isChecked) {
                    {
                        Toast.makeText(mContext, "onPictureInPicture", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    null
                },
                //onMoveBanner
                if (onMoveBanner.isChecked) {
                    { message ->
                        Toast.makeText(mContext, "onMoveBanner", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    null
                },
                //onWebviewReloading
                if (onWebviewReloading.isChecked) {
                    { message ->
                        Toast.makeText(mContext, "onWebviewReloading", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    null
                },
                //onMoveReward
                if (onMoveReward.isChecked) {
                    { message ->
                        Toast.makeText(mContext, "onMoveReward", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    null
                },
                //onMoveProduct
                if (onMoveProduct.isChecked) {
                    { message ->
                        Toast.makeText(mContext, "onMoveProduct", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    null
                },
                //onTokenError
                if (onTokenError.isChecked) {
                    { message ->
                        Toast.makeText(mContext, "onTokenError", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    null
                },
                { Toast.makeText(this, "PictureInPictureMode On", Toast.LENGTH_SHORT).show() },
                { Toast.makeText(this, "PictureInPictureMode OFF", Toast.LENGTH_SHORT).show() },
                if (radioDev.isChecked) "dev." else if (radioStage.isChecked) "stage." else "",
                queryString
            )
        }

        btnSauceActivityPip.setOnClickListener {
            queryString = edittext.text.toString()
            SauceLive.openPipActivity(
                mContext, broadcastId, true,
                null,
                Member("memberId", "nickName", "30", "m", null),
                //onEnter
                if (onEnter.isChecked) {
                    {
                        Toast.makeText(mContext, "onEnter", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    null
                },
                //onMoveExit
                if (onMoveExit.isChecked) {
                    {
                        Toast.makeText(mContext, "onMoveExit", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    null
                },
                //onMoveLogin
                if (onMoveLogin.isChecked) {
                    {
                        Toast.makeText(mContext, "onMoveLogin", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    null
                },
                //onShare
                if (onShare.isChecked) {
                    { message ->
                        Toast.makeText(mContext, "onShare", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    null
                },
                //onPictureInPicture
                if (onPictureInPicture.isChecked) {
                    {
                        Toast.makeText(mContext, "onPictureInPicture", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    null
                },
                //onMoveBanner
                if (onMoveBanner.isChecked) {
                    { message ->
                        Toast.makeText(mContext, "onMoveBanner", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    null
                },
                //onWebviewReloading
                if (onWebviewReloading.isChecked) {
                    { message ->
                        Toast.makeText(mContext, "onWebviewReloading", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    null
                },
                //onMoveReward
                if (onMoveReward.isChecked) {
                    { message ->
                        Toast.makeText(mContext, "onMoveReward", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    null
                },
                //onMoveProduct
                if (onMoveProduct.isChecked) {
                    { message ->
                        Toast.makeText(mContext, "onMoveProduct", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    null
                },
                //onTokenError
                if (onTokenError.isChecked) {
                    { message ->
                        Toast.makeText(mContext, "onTokenError", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    null
                },
                { Toast.makeText(this, "PictureInPictureMode On", Toast.LENGTH_SHORT).show() },
                { Toast.makeText(this, "PictureInPictureMode OFF", Toast.LENGTH_SHORT).show() },
                if (radioDev.isChecked) "dev." else if (radioStage.isChecked) "stage." else "",
                queryString
            )
        }

    }
}