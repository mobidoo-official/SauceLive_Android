package com.mobidoo.saucelive

import android.app.Activity
import android.app.PictureInPictureParams
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.util.Rational
import android.webkit.WebView
import android.webkit.WebViewClient

class SauceLivePipActivity : Activity() {
    private lateinit var mContext: Context
    private lateinit var sauceliveView: SauceLiveView
    private lateinit var linkUrl: String
    private var pip = false

    companion object {
        var sauceflexEnter: (() -> Unit)? = null
        var sauceflexMoveExit: (() -> Unit)? = null
        var sauceflexMoveLogin: (() -> Unit)? = null
        var sauceflexOnShare: ((message: String) -> Unit)? = null
        var sauceflexPictureInPicture: (() -> Unit)? = null
        var sauceflexMoveBanner: ((message: String) -> Unit)? = null
        var sauceflexWebviewReloading: ((message: String) -> Unit)? = null
        var sauceflexMoveReward: ((message: String) -> Unit)? = null
        var sauceflexMoveProduct: ((message: String) -> Unit)? = null
        var sauceflexTokenError: ((message: String) -> Unit)? = null
        var onPipModeOn: (() -> Unit)? = null
        var onPipModeOff: (() -> Unit)? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saucelive)
        linkUrl = intent.getStringExtra("linkUrl") ?: ""
        pip = intent.getBooleanExtra("pip", false)
        init()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null) {
            pip = intent.getBooleanExtra("pip", false)
            if (pip) {
                pipOn()
            }
        }

    }

    private fun init() {
        mContext = this
        sauceliveView = findViewById(R.id.saucelive)
        sauceliveView.loadUrl(linkUrl)
        sauceliveView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return false
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {


            }

            override fun onPageFinished(view: WebView?, url: String?) {
                if (url?.contains(linkUrl) == true && pip) {
                    sauceliveView.hidePlayerUi()
                }
            }

        }
        initPlayerCallback()

        if (pip) {
            pipOn()
        }
    }

    private fun initPlayerCallback() {
        sauceliveView.setOnEnterListener(sauceflexEnter)
        sauceliveView.setOnMoveExitListener({
            sauceflexMoveExit?.invoke()
            finish()
        })
        sauceliveView.setOnMoveLoginListener(sauceflexMoveLogin)
        sauceliveView.setOnShareListener(sauceflexOnShare)
        sauceliveView.setOnPictureInPictureListener({
            pipOn()
            sauceflexPictureInPicture?.invoke()
        })
        sauceliveView.setOnMoveBannerListener(sauceflexMoveBanner)
        sauceliveView.setOnWebviewReloadingListener(sauceflexWebviewReloading)
        sauceliveView.setOnMoveRewardListener(sauceflexMoveReward)
        sauceliveView.setOnMoveProductListener(sauceflexMoveProduct)
        sauceliveView.setOnTokenErrorListener(sauceflexTokenError)
    }


    // PIP 모드 활성화
    private fun pipOn() {
        var pipWidth = 9
        var pipHeight = 16

        var pipBuilder = PictureInPictureParams.Builder()
        pipBuilder.setAspectRatio(Rational(pipWidth, pipHeight))

        enterPictureInPictureMode(pipBuilder.build())
    }

    override fun onPictureInPictureModeChanged(isInPictureInPictureMode: Boolean) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode)
        if (isInPictureInPictureMode) {
            onPipModeOn?.invoke()
            sauceliveView.hidePlayerUi()
            pip = true
        } else {
            onPipModeOff?.invoke()
            sauceliveView.showPlayerUi()
            pip = false
        }
    }
}