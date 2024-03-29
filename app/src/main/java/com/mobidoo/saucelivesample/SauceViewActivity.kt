package com.mobidoo.saucelivesample

import android.app.Activity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.mobidoo.saucelive.Member
import com.mobidoo.saucelive.MemberObjectCallback
import com.mobidoo.saucelive.SauceLiveView

class SauceViewActivity : Activity() {

    private lateinit var broadcastId: String
    private lateinit var sauceview: SauceLiveView
    private lateinit var sampleText: TextView
    private lateinit var sampleText2: TextView
    private lateinit var btnShow: Button
    private lateinit var btnHide: Button

    companion object {
        var onEnter: Boolean = false
        var onMoveExit: Boolean = false
        var onMoveLogin: Boolean = false
        var onShare: Boolean = false
        var onPictureInPicture: Boolean = false
        var onMoveBanner: Boolean = false
        var onWebviewReloading: Boolean = false
        var onMoveReward: Boolean = false
        var onMoveProduct: Boolean = false
        var onTokenError: Boolean = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sauceview)
        broadcastId = intent.getStringExtra("broadcastId") ?: ""
        init()
    }

    private fun init() {
        sauceview = findViewById(R.id.saucelive)
        sauceview.setInit(broadcastId)
        sauceview.setMemberObject(Member("memberId", "nickName", "30", "m", null), object :
            MemberObjectCallback {
            override fun onSuccess() {
                sauceview.load()
            }

            override fun onError(error: String?) {
                sauceview.load()
            }
        })

        sampleText = findViewById(R.id.sample_text)
        sampleText.text = """<com.mobidoo.saucelive.SauceLiveView
            android:id="@+id/saucelive"
            android:layout_width="180dp"
            android:layout_height="320dp"/>"""

        sampleText2 = findViewById(R.id.sample_text2)
        sampleText2.text = """"YourSauceView.setInit(\"your broadcastId\")
            YourSauceView.setMemberObject(Member("memberId", "nickName", "age", "gender", memberType), object :
                MemberObjectCallback {
                override fun onSuccess() {
                    YourSauceView.load()
                }

                override fun onError(error: String?) {
                    YourSauceView.load()
                }
            })
        """.trimMargin()

        btnShow = findViewById(R.id.btn_show)
        btnShow.setOnClickListener { sauceview.showPlayerUi() }

        btnHide = findViewById(R.id.btn_hide)
        btnHide.setOnClickListener { sauceview.hidePlayerUi() }

        if (onEnter) {
            sauceview.setOnEnterListener {
                Toast.makeText(this, "onEnter", Toast.LENGTH_SHORT).show()
            }
        }

        if (onMoveExit) {
            sauceview.setOnMoveExitListener {
                Toast.makeText(this, "onMoveExit", Toast.LENGTH_SHORT).show()
            }
        }

        if (onMoveLogin) {
            sauceview.setOnMoveLoginListener {
                Toast.makeText(
                    this,
                    "onMoveLogin",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        if (onShare) {
            sauceview.setOnShareListener { message ->
                Toast.makeText(
                    this,
                    "onShare",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        if (onPictureInPicture) {
            sauceview.setOnPictureInPictureListener {
                Toast.makeText(
                    this,
                    "onPictureInPicture",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        if (onMoveBanner) {
            sauceview.setOnMoveBannerListener { message ->
                Toast.makeText(
                    this,
                    "onMoveBanner",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        if (onWebviewReloading) {
            sauceview.setOnWebviewReloadingListener { message ->
                Toast.makeText(
                    this,
                    "onWebviewReloading",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        if (onMoveReward) {
            sauceview.setOnMoveRewardListener { message ->
                Toast.makeText(
                    this,
                    "onMoveReward",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        if (onMoveProduct) {
            sauceview.setOnMoveProductListener { message ->
                Toast.makeText(
                    this,
                    "onMoveProduct",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        if (onTokenError) {
            sauceview.setOnTokenErrorListener { message ->
                Toast.makeText(
                    this,
                    "onTokenError",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

}