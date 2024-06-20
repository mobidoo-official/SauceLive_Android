package com.mobidoo.saucelive

import android.content.Context
import android.graphics.Bitmap
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout

/**
 * SauceLiveCollection 클래스는 FrameLayout을 상속받아 WebView를 추가하는 클래스입니다.
 * @param context 앱의 현재 상황을 나타내는 Context 객체입니다.
 * @param attrs XML에서 전달된 속성을 나타내는 AttributeSet 객체입니다.
 * @param defStyleAttr 스타일 속성을 나타내는 Int 값입니다.
 */
class SauceLiveCollection @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val webView: ObservableWebView
    private var stageMode: Boolean = false
    private var partnerId: String? = null
    private var curationId: String? = null
    private var type: SauceLiveCollectionType? = null
    private var userAccessToken: String? = null

    init {
        // Create a WebView and set its settings
        webView = ObservableWebView(context)
        addView(webView, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))

        init()
    }

    // webview init 메소드
    private fun init() {

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.userAgentString = webView.settings.userAgentString + " sauce-sdk-android"

        webView.webChromeClient = WebChromeClient()

        webView.setOnTouchListener { v, ev ->
            when (ev.getAction()) {
                MotionEvent.ACTION_DOWN -> {
                    false
                }
                MotionEvent.ACTION_MOVE -> {
                    // X축 이동이 Y축 이동보다 클 때 true를 반환하여 수평 스크롤 이벤트를 가로챈다
                    // v.parent.requestDisallowInterceptTouchEvent(deltaX > deltaY)
                    if (webView.isCurrentlyScrolling()){
                        v.parent.requestDisallowInterceptTouchEvent(true)
                    }else {
                        v.parent.requestDisallowInterceptTouchEvent(false)
                    }
                }
            }
            false
        }
    }

    fun setInit(partnerId: String, type: SauceLiveCollectionType, curationId: String? = null) {
        this.partnerId = partnerId
        this.type = type
        this.curationId = curationId
    }

    fun setStageMode(on: Boolean) {
        this.stageMode = on
    }

    // webview load 메소드
    fun load() {
        if (type == null) {
            throw Error("SauceLiveCollectionType is required")
        }

        if (partnerId == null) {
            throw Error("partnerId is required")
        }

        val stage = if (stageMode) "stage." else ""
        val topBanner = type == SauceLiveCollectionType.topBanner
        val broadcast = type == SauceLiveCollectionType.broadcast
        val schedule = type == SauceLiveCollectionType.schedule
        val broadcastTable = type == SauceLiveCollectionType.broadcastTable
        val curation = type == SauceLiveCollectionType.curation
        val curationDetail = if (type == SauceLiveCollectionType.curationDetail) "&curationDetail=true&curationId=$curationId" else "&curationDetail=false"

        val collectionUrl = "https://${stage}collection.sauceflex.com/index.html?partnerId=$partnerId&searchBar=false&topBanner=$topBanner&broadcast=$broadcast&curation=$curation&schedule=$schedule&broadcastTable=$broadcastTable$curationDetail"

        webView.loadUrl(collectionUrl)
        return
    }

    fun setOnMoveTopBanner(callback: ((message: String) -> Unit)?) {
        webView.removeJavascriptInterface("flexCollectMoveTopBanner")
        if (callback != null) {
            webView.addJavascriptInterface(
                SauceflexMoveTopBannerJavaScriptInterface(callback),
                "flexCollectMoveTopBanner"
            )
        }
    }

    /**
     * Set & Save member object (Token)
     */
    fun setMemberObject(
        member: Member,
        partnerId: String,
        callback: MemberObjectCallback
    ) {
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

    /**
     * Set & Save user access token
     * @param userAccessToken
     */
    fun setMemberToken(userAccessToken: String) {
        this.userAccessToken = userAccessToken

        val sharedPreference = context.getSharedPreferences("sauceflex", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("userAccessToken", userAccessToken)
        editor.apply()
    }

    /**
     * Get user access token
     * @return userAccessToken
     */
    fun getMemberToekn(): String? {
        val sharedPreference = context.getSharedPreferences("sauceflex", Context.MODE_PRIVATE)
        val userAccessToken = sharedPreference.getString("userAccessToken", null)
        return userAccessToken
    }

    private class SauceflexMoveTopBannerJavaScriptInterface(
        val flexCollectMoveTopBanner: ((message: String) -> Unit),
    ) {
        private val handler = Handler()

        @JavascriptInterface   // 처음 플레이어 진입시
        fun flexCollectMoveTopBanner(message: String) {
            handler.post {
                flexCollectMoveTopBanner.invoke(message)
            }
        }
    }

    fun setOnMoveContentBanner(callback: ((message: String) -> Unit)?) {
        webView.removeJavascriptInterface("flexCollectMoveContentBanner")
        if (callback != null) {
            webView.addJavascriptInterface(
                SauceflexMoveContentBannerJavaScriptInterface(callback),
                "flexCollectMoveContentBanner"
            )
        }
    }

    private class SauceflexMoveContentBannerJavaScriptInterface(
        val flexCollectMoveContentBanner: ((message: String) -> Unit),
    ) {
        private val handler = Handler()

        @JavascriptInterface   // 처음 플레이어 진입시
        fun flexCollectMoveContentBanner(message: String) {
            handler.post {
                flexCollectMoveContentBanner.invoke(message)
            }
        }
    }

    fun setOnMoveBroadcast(callback: ((message: String) -> Unit)?) {
        webView.removeJavascriptInterface("flexCollectMoveBroadcast")
        if (callback != null) {
            webView.addJavascriptInterface(
                SauceflexMoveBroadcastJavaScriptInterface(callback),
                "flexCollectMoveBroadcast"
            )
        }
    }

    private class SauceflexMoveBroadcastJavaScriptInterface(
        val flexCollectMoveBroadcast: ((message: String) -> Unit),
    ) {
        private val handler = Handler()

        @JavascriptInterface   // 처음 플레이어 진입시
        fun flexCollectMoveBroadcast(message: String) {
            handler.post {
                flexCollectMoveBroadcast.invoke(message)
            }
        }
    }

    fun setOnMoveProduct(callback: ((message: String) -> Unit)?) {
        webView.removeJavascriptInterface("flexCollectMoveProduct")
        if (callback != null) {
            webView.addJavascriptInterface(
                SauceflexMoveProductJavaScriptInterface(callback),
                "flexCollectMoveProduct"
            )
        }
    }

    private class SauceflexMoveProductJavaScriptInterface(
        val flexCollectMoveProduct: ((message: String) -> Unit),
    ) {
        private val handler = Handler()

        @JavascriptInterface   // 처음 플레이어 진입시
        fun flexCollectMoveProduct(message: String) {
            handler.post {
                flexCollectMoveProduct.invoke(message)
            }
        }
    }

    fun setOnClickCuration(callback: ((message: String) -> Unit)?) {
        webView.removeJavascriptInterface("flexCollectClickCuration")
        if (callback != null) {
            webView.addJavascriptInterface(
                SauceflexClickCurationJavaScriptInterface(callback),
                "flexCollectClickCuration"
            )
        }
    }

    private class SauceflexClickCurationJavaScriptInterface(
        val flexCollectClickCuration: ((message: String) -> Unit),
    ) {
        private val handler = Handler()

        @JavascriptInterface   // 처음 플레이어 진입시
        fun flexCollectClickCuration(message: String) {
            handler.post {
                flexCollectClickCuration.invoke(message)
            }
        }
    }

    fun setOnApplyForNotification(callback: ((message: String) -> Unit)?) {
        webView.removeJavascriptInterface("flexCollectApplyForNotification")
        if (callback != null) {
            webView.addJavascriptInterface(
                SauceflexApplyForNotificationJavaScriptInterface(callback),
                "flexCollectApplyForNotification"
            )
        }
    }

    private class SauceflexApplyForNotificationJavaScriptInterface(
        val flexCollectApplyForNotification: ((message: String) -> Unit),
    ) {
        private val handler = Handler()

        @JavascriptInterface   // 처음 플레이어 진입시
        fun flexCollectApplyForNotification(message: String) {
            handler.post {
                flexCollectApplyForNotification.invoke(message)
            }
        }
    }

    fun setOnCancelForNotification(callback: ((message: String) -> Unit)?) {
        webView.removeJavascriptInterface("flexCollectCancelForNotification")
        if (callback != null) {
            webView.addJavascriptInterface(
                SauceflexCancelForNotificationJavaScriptInterface(callback),
                "flexCollectCancelForNotification"
            )
        }
    }

    private class SauceflexCancelForNotificationJavaScriptInterface(
        val flexCollectCancelForNotification: ((message: String) -> Unit),
    ) {
        private val handler = Handler()

        @JavascriptInterface   // 처음 플레이어 진입시
        fun flexCollectCancelForNotification(message: String) {
            handler.post {
                flexCollectCancelForNotification.invoke(message)
            }
        }
    }



}