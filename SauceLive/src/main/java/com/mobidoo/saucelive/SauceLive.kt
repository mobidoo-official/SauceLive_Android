package com.mobidoo.saucelive

import android.content.Context
import android.content.Intent

class SauceLive {
    companion object {
        fun openPipActivity(
            context: Context,
            linkUrl: String,
            pip: Boolean = false,
            onEnter: (() -> Unit)? = null,
            onMoveExit: (() -> Unit)? = null,
            onMoveLogin: (() -> Unit)? = null,
            onShare: ((message: String) -> Unit)? = null,
            onPictureInPicture: (() -> Unit)? = null,
            onMoveBanner: ((message: String) -> Unit)? = null,
            onWebviewReloading: ((message: String) -> Unit)? = null,
            onMoveReward: ((message: String) -> Unit)? = null,
            onMoveProduct: ((message: String) -> Unit)? = null,
            onTokenError: ((message: String) -> Unit)? = null,
            onPipModeOn: (() -> Unit)? = null,
            onPipModeOff: (() -> Unit)? = null
        ) {
            val intent = Intent(context, SauceLivePipActivity::class.java)
            intent.putExtra("linkUrl", linkUrl)
            intent.putExtra("pip", pip)

            SauceLivePipActivity.sauceflexEnter = onEnter
            SauceLivePipActivity.sauceflexMoveExit = onMoveExit
            SauceLivePipActivity.sauceflexMoveLogin = onMoveLogin
            SauceLivePipActivity.sauceflexOnShare = onShare
            SauceLivePipActivity.sauceflexPictureInPicture = onPictureInPicture
            SauceLivePipActivity.sauceflexMoveBanner = onMoveBanner
            SauceLivePipActivity.sauceflexWebviewReloading = onWebviewReloading
            SauceLivePipActivity.sauceflexMoveReward = onMoveReward
            SauceLivePipActivity.sauceflexMoveProduct = onMoveProduct
            SauceLivePipActivity.sauceflexTokenError = onTokenError
            SauceLivePipActivity.onPipModeOn = onPipModeOn
            SauceLivePipActivity.onPipModeOff = onPipModeOff

            context.startActivity(intent)

        }
    }
}