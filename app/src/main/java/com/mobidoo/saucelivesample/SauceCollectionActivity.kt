package com.mobidoo.saucelivesample

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText

class SauceCollectionActivity : Activity() {

    private lateinit var mContext: Context
    private lateinit var edittext : EditText
    private lateinit var edittext2 : EditText
    private lateinit var btnSauceCollectionViewActivity : Button
    private lateinit var stageMode: CheckBox
    private lateinit var onMoveTopBanner: CheckBox
    private lateinit var onMoveContentBanner: CheckBox
    private lateinit var onMoveBroadcast: CheckBox
    private lateinit var onMoveProduct: CheckBox
    private lateinit var onClickCuration: CheckBox
    private lateinit var onApplyForNotification: CheckBox
    private lateinit var onCancelForNotification: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sauce_collection)
        init()
    }

    private fun init() {
        mContext = this
        edittext = findViewById(R.id.edittext)
        edittext2 = findViewById(R.id.edittext2)
        btnSauceCollectionViewActivity = findViewById(R.id.btn_collection_activity)
        stageMode = findViewById(R.id.check_stage_mode)
        onMoveTopBanner = findViewById(R.id.check_move_top_banner)
        onMoveContentBanner = findViewById(R.id.check_move_content_banner)
        onMoveBroadcast = findViewById(R.id.check_move_broadcast)
        onMoveProduct = findViewById(R.id.check_move_product)
        onClickCuration = findViewById(R.id.check_click_curation)
        onApplyForNotification = findViewById(R.id.check_apply_for_notification)
        onCancelForNotification = findViewById(R.id.check_cancel_for_notification)

        edittext.setText("uiux")
        edittext.setHint("Enter partnerId")
        edittext.inputType = InputType.TYPE_CLASS_TEXT
        edittext.maxLines = 1

        edittext2.setText("cxj3eUrSxgG8XZQvWnU7dB")
        edittext2.setHint("Enter curationId")

        btnSauceCollectionViewActivity.setOnClickListener {
            val partnerId = edittext.text.toString()
            val curationId = edittext2.text.toString()
            val intent = Intent(mContext, SauceCollectionViewActivity::class.java)
            intent.putExtra("partnerId", partnerId)
            intent.putExtra("stageMode", stageMode.isChecked)
            intent.putExtra("curationId", curationId)
            SauceCollectionViewActivity.onMoveTopBanner = onMoveTopBanner.isChecked
            SauceCollectionViewActivity.onMoveContentBanner = onMoveContentBanner.isChecked
            SauceCollectionViewActivity.onMoveBroadcast = onMoveBroadcast.isChecked
            SauceCollectionViewActivity.onMoveProduct = onMoveProduct.isChecked
            SauceCollectionViewActivity.onClickCuration = onClickCuration.isChecked
            SauceCollectionViewActivity.onApplyForNotification = onApplyForNotification.isChecked
            SauceCollectionViewActivity.onCancelForNotification = onCancelForNotification.isChecked
            startActivity(intent)
        }
    }
}