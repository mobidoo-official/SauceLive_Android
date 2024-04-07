package com.mobidoo.saucelive

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout

class SauceLiveView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val webView: WebView
    private var broadcastId: String? = null
    private var userAccessToken: String? = null

    var webViewClient: WebViewClient = WebViewClient()
        set(value) {
            field = value
            webView.webViewClient = value
        }

    init {
        // 웹뷰를 생성하고 설정
        webView = WebView(context)
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true // JavaScript를 사용할 수 있도록 설정

        // 웹뷰를 FrameLayout에 추가
        addView(webView, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true

        webView.webChromeClient = WebChromeClient()
        webView.settings.userAgentString = webView.settings.userAgentString + " sauce-sdk-android"

    }

    fun setInit(broadcastId: String) {
        this.broadcastId = broadcastId
    }

    fun setMemberToken(userAccessToken: String) {
        this.userAccessToken = userAccessToken
    }

    fun setMemberObject(
        member: Member,
        callback: MemberObjectCallback
    ) {
        if (broadcastId == null) {
            throw Error("broadcastId is required")
        }
        val pattern = Regex("(lk|vc|vk)(.*-?)-")
        val matchResult = pattern.find(broadcastId!!)
        val partnerId = matchResult?.groupValues?.get(2) ?: ""

        SauceLive.createUserAccessToken(
            Member(
                member.memberId,
                member.nickName,
                member.age,
                member.gender,
                member.memberType,
                partnerId
            ),
            object : UserAccessTokenCallback {
                override fun onSuccess(accessToken: String) {
                    setMemberToken(accessToken)
                    callback.onSuccess()
                }

                override fun onError(error: String?) {
                    callback.onError(error)
                }
            }
        )
    }

    fun load() {
        if (broadcastId == null) {
            throw Error("broadcastId is required")
        }
        if (userAccessToken == null) {
            webView.loadUrl("https://player.sauceflex.com/broadcast/$broadcastId")
        } else {
            webView.loadUrl("https://player.sauceflex.com/broadcast/$broadcastId?accessToken=$userAccessToken")
        }

    }

    fun hidePlayerUi() {
        webView.evaluateJavascript(
            "window.postMessage(\"sauceflexPictureInPictureOn\", \"*\");",
            null
        )
    }

    fun showPlayerUi() {
        webView.evaluateJavascript(
            "window.postMessage(\"sauceflexPictureInPictureOff\", \"*\");",
            null
        )
    }

    fun setOnEnterListener(callback: (() -> Unit)?) {
        webView.removeJavascriptInterface("sauceflexEnter")
        if (callback != null) {
            webView.addJavascriptInterface(
                SauceflexEnterJavaScriptInterface(callback),
                "sauceflexEnter"
            )
        }
    }

    fun setOnMoveExitListener(callback: (() -> Unit)?) {
        webView.removeJavascriptInterface("sauceflexMoveExit")
        if (callback != null) {
            webView.addJavascriptInterface(
                SauceflexMoveExitJavaScriptInterface(callback),
                "sauceflexMoveExit"
            )
        }
    }

    fun setOnMoveLoginListener(callback: (() -> Unit)?) {
        webView.removeJavascriptInterface("sauceflexMoveLogin")
        if (callback != null) {
            webView.addJavascriptInterface(
                SauceflexMoveLoginJavaScriptInterface(callback),
                "sauceflexMoveLogin"
            )
        }
    }

    fun setOnShareListener(callback: ((message: String) -> Unit)?) {
        webView.removeJavascriptInterface("sauceflexOnShare")
        if (callback != null) {
            webView.addJavascriptInterface(
                SauceflexOnShareJavaScriptInterface(callback),
                "sauceflexOnShare"
            )
        }
    }

    fun setOnPictureInPictureListener(callback: (() -> Unit)?) {
        webView.removeJavascriptInterface("sauceflexPictureInPicture")
        if (callback != null) {
            webView.addJavascriptInterface(
                SauceflexPictureInPictureJavaScriptInterface(callback),
                "sauceflexPictureInPicture"
            )
        }
    }

    fun setOnMoveBannerListener(callback: ((message: String) -> Unit)?) {
        webView.removeJavascriptInterface("sauceflexMoveBanner")
        if (callback != null) {
            webView.addJavascriptInterface(
                SauceflexMoveBannerJavaScriptInterface(callback),
                "sauceflexMoveBanner"
            )
        }
    }

    fun setOnWebviewReloadingListener(callback: ((message: String) -> Unit)?) {
        webView.removeJavascriptInterface("sauceflexWebviewReloading")
        if (callback != null) {
            webView.addJavascriptInterface(
                SauceflexWebviewReloadingJavaScriptInterface(callback),
                "sauceflexWebviewReloading"
            )
        }
    }

    fun setOnMoveRewardListener(callback: ((message: String) -> Unit)?) {
        webView.removeJavascriptInterface("sauceflexMoveReward")
        if (callback != null) {
            webView.addJavascriptInterface(
                SauceflexMoveRewardJavaScriptInterface(callback),
                "sauceflexMoveReward"
            )
        }
    }

    fun setOnMoveProductListener(callback: ((message: String) -> Unit)?) {
        webView.removeJavascriptInterface("sauceflexMoveProduct")
        if (callback != null) {
            webView.addJavascriptInterface(
                SauceflexMoveProductJavaScriptInterface(callback),
                "sauceflexMoveProduct"
            )
        }
    }

    fun setOnTokenErrorListener(callback: ((message: String) -> Unit)?) {
        webView.removeJavascriptInterface("sauceflexTokenError")
        if (callback != null) {
            webView.addJavascriptInterface(
                SauceflexTokenErrorJavaScriptInterface(callback),
                "sauceflexTokenError"
            )
        }
    }

    private class SauceflexEnterJavaScriptInterface(
        val sauceflexEnter: (() -> Unit),
    ) {
        private val handler = Handler()

        @JavascriptInterface   // 처음 플레이어 진입시
        fun sauceflexEnter() {
            handler.post {
                sauceflexEnter.invoke()
            }
        }
    }

    private class SauceflexMoveExitJavaScriptInterface(
        val sauceflexMoveExit: (() -> Unit)
    ) {
        private val handler = Handler()

        @JavascriptInterface   // 닫기 버튼 눌러 팝업에서 나가기시
        fun sauceflexMoveExit() {
            handler.post {
                sauceflexMoveExit.invoke()
            }
        }
    }

    private class SauceflexMoveLoginJavaScriptInterface(
        val sauceflexMoveLogin: (() -> Unit)
    ) {
        private val handler = Handler()

        @JavascriptInterface   // 로그인 팝업에서 확인시
        fun sauceflexMoveLogin() {
            handler.post {
                sauceflexMoveLogin.invoke()
            }
        }
    }

    private class SauceflexOnShareJavaScriptInterface(
        val sauceflexOnShare: ((message: String) -> Unit)
    ) {
        private val handler = Handler()

        @JavascriptInterface   // 공유하기
        fun sauceflexOnShare(message: String) {
            handler.post {
                sauceflexOnShare.invoke(message)
            }
        }
    }

    private class SauceflexPictureInPictureJavaScriptInterface(
        val sauceflexPictureInPicture: (() -> Unit)
    ) {
        private val handler = Handler()

        @JavascriptInterface   // PIP 전환 버튼 클릭시, PIP 보기 전환시, 보여주고 있는 컴포넌트 GONE 처리를 자동으로 처리 합니다. (GONE 내용 ex: 좋아요 및 각정 플레이어 위에 띄워지는 버튼들)
        fun sauceflexPictureInPicture() {
            handler.post {
                sauceflexPictureInPicture.invoke()
            }
        }
    }

    private class SauceflexMoveBannerJavaScriptInterface(
        var sauceflexMoveBanner: ((message: String) -> Unit)
    ) {
        private val handler = Handler()

        @JavascriptInterface   // 배너 클릭시 bannerId (배너 고유 아이디), linkUrl (배너에 등록된 URL), broadcastIdx (방송 번호)
        fun sauceflexMoveBanner(message: String) {
            handler.post {
                sauceflexMoveBanner.invoke(message)
            }
        }
    }

    private class SauceflexWebviewReloadingJavaScriptInterface(
        var sauceflexWebviewReloading: ((message: String) -> Unit)
    ) {
        private val handler = Handler()

        @JavascriptInterface   // 웹뷰 리로딩
        fun sauceflexWebviewReloading(message: String) {
            handler.post {
                sauceflexWebviewReloading.invoke(message)
            }
        }
    }

    private class SauceflexMoveRewardJavaScriptInterface(
        var sauceflexMoveReward: ((message: String) -> Unit)
    ) {
        private val handler = Handler()

        @JavascriptInterface   // 리워드 기능
        fun sauceflexMoveReward(message: String) {
            handler.post {
                sauceflexMoveReward.invoke(message)
            }
        }
    }

    private class SauceflexMoveProductJavaScriptInterface(
        var sauceflexMoveProduct: ((message: String) -> Unit)
    ) {
        private val handler = Handler()

        @JavascriptInterface   // 상품 클릭시
        fun sauceflexMoveProduct(message: String) {
            handler.post {
                sauceflexMoveProduct.invoke(message)
            }
        }
    }

    private class SauceflexTokenErrorJavaScriptInterface(
        var sauceflexTokenError: ((message: String) -> Unit)
    ) {
        private val handler = Handler()

        @JavascriptInterface   // 상품 클릭시
        fun sauceflexTokenError(message: String) {
            handler.post {
                sauceflexTokenError.invoke(message)
            }
        }
    }

}




