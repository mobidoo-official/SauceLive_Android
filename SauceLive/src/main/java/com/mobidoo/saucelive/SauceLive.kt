package com.mobidoo.saucelive

import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SauceLive {
    companion object {
        fun openPipActivity(
            context: Context,
            broadcastId: String,
            pip: Boolean = false,
            accessToken: String? = null,
            member: Member? = null,
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
            intent.putExtra("broadcastId", broadcastId)
            intent.putExtra("accessToken", accessToken)
            intent.putExtra("pip", pip)

            SauceLivePipActivity.member = member
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

        fun createPaymentTracker(
            orderCallBackId: String,
            orderId: String,
            payClickTime: String,
            productQuantity: String,
            amount: String,
            memberId: String,
            callback: PaymentTrackerCallback
        ) {
            val retrofit = Retrofit.Builder().baseUrl("https://api.sauceFlex.com/V1/front/")
                .addConverterFactory(
                    GsonConverterFactory.create()
                ).build()
            val service = retrofit.create(SauceAPI::class.java)
            service.createPaymentTracker(
                orderCallBackId,
                orderId,
                payClickTime,
                productQuantity,
                amount,
                memberId
            ).enqueue(object : Callback<ErrorResponse> {
                override fun onResponse(
                    call: Call<ErrorResponse>,
                    response: Response<ErrorResponse>
                ) {
                    if (response.isSuccessful) {
                        callback.onSuccess()
                        val body = response.body()
                        Log.d("SauceLive", "createPaymentTracker: $body")
                    } else {
                        // 에러 응답 처리
                        val body = response.body()
                        callback.onError(body)
                    }
                }

                override fun onFailure(call: Call<ErrorResponse>, t: Throwable) {
                    Log.e("SauceLive", "createPaymentTracker: ${t.message}")
                    callback.onError(null)
                }
            })
        }

        fun createPaymentListTracker(
            payments: List<PaymentData>,
            callback: PaymentTrackerCallback
        ) {
            val retrofit = Retrofit.Builder().baseUrl("https://api.sauceFlex.com/V1/front/")
                .addConverterFactory(
                    GsonConverterFactory.create()
                ).build()
            val service = retrofit.create(SauceAPI::class.java)
            service.createPaymentListTracker(payments).enqueue(object : Callback<ErrorResponse> {
                override fun onResponse(
                    call: Call<ErrorResponse>,
                    response: Response<ErrorResponse>
                ) {
                    if (response.isSuccessful) {
                        callback.onSuccess()
                        val body = response.body()
                        Log.d("SauceLive", "createPaymentListTracker: $body")
                    } else {
                        // 에러 응답 처리
                        val body = response.body()
                        callback.onError(body)
                    }
                }

                override fun onFailure(call: Call<ErrorResponse>, t: Throwable) {
                    Log.e("SauceLive", "createPaymentListTracker: ${t.message}")
                    callback.onError(null)
                }
            })
        }

        fun updatePaymentTracker(
            orderCallBackId: String,
            orderId: String,
            findProductQuantity: String,
            findAmount: String,
            updateProductQuantity: String,
            updateAmount: String,
            callback: PaymentTrackerCallback
        ) {
            val retrofit = Retrofit.Builder().baseUrl("https://api.sauceFlex.com/V1/front/")
                .addConverterFactory(
                    GsonConverterFactory.create()
                ).build()
            val service = retrofit.create(SauceAPI::class.java)
            service.updatePaymentTracker(
                orderCallBackId,
                orderId,
                findProductQuantity,
                findAmount,
                updateProductQuantity,
                updateAmount
            ).enqueue(object : Callback<ErrorResponse> {
                override fun onResponse(
                    call: Call<ErrorResponse>,
                    response: Response<ErrorResponse>
                ) {
                    if (response.isSuccessful) {
                        callback.onSuccess()
                        val body = response.body()
                        Log.d("SauceLive", "updatePaymentTracker: $body")
                    } else {
                        // 에러 응답 처리
                        val body = response.body()
                        callback.onError(body)
                    }
                }

                override fun onFailure(call: Call<ErrorResponse>, t: Throwable) {
                    Log.e("SauceLive", "updatePaymentTracker: ${t.message}")
                    callback.onError(null)
                }
            })
        }

        fun deletePaymentTracker(
            orderCallBackId: String,
            orderId: String,
            payCancelTime: String,
            callback: PaymentTrackerCallback
        ) {
            val retrofit = Retrofit.Builder().baseUrl("https://api.sauceFlex.com/V1/front/")
                .addConverterFactory(
                    GsonConverterFactory.create()
                ).build()
            val service = retrofit.create(SauceAPI::class.java)
            service.deletePaymentTracker(
                orderCallBackId,
                orderId,
                payCancelTime
            ).enqueue(object : Callback<ErrorResponse> {
                override fun onResponse(
                    call: Call<ErrorResponse>,
                    response: Response<ErrorResponse>
                ) {
                    if (response.isSuccessful) {
                        callback.onSuccess()
                        val body = response.body()
                        Log.d("SauceLive", "deletePaymentTracker: $body")
                    } else {
                        // 에러 응답 처리
                        val body = response.body()
                        callback.onError(body)
                    }
                }

                override fun onFailure(call: Call<ErrorResponse>, t: Throwable) {
                    Log.e("SauceLive", "deletePaymentTracker: ${t.message}")
                    callback.onError(null)
                }
            })
        }

        fun createUserAccessToken(
            member: Member,
            callback: UserAccessTokenCallback
        ) {

            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            val retrofit = Retrofit.Builder().baseUrl("https://api.sauceflex.com/V1/")
                .client(client)
                .addConverterFactory(
                    GsonConverterFactory.create()
                ).build()
            val service = retrofit.create(SauceAPI::class.java)
            service.createUserAccessToken(
                member
            ).enqueue(object : Callback<UserAccessTokenResponse> {
                override fun onResponse(
                    call: Call<UserAccessTokenResponse>,
                    response: Response<UserAccessTokenResponse>
                ) {

                    if (response.isSuccessful) {
                        val body = response.body()
                        callback.onSuccess(body?.response?.accessToken ?: "")
                        Log.d("SauceLive", "createUserAccessToken: $body")
                    } else {
                        // 에러 응답 처리
                        val body = response.body()
                        Log.e("SauceLive", "createUserAccessToken: $body")
                        callback.onError(null)
                    }
                }

                override fun onFailure(call: Call<UserAccessTokenResponse>, t: Throwable) {
                    Log.e("SauceLive", "createUserAccessToken: ${t.message}")
                    callback.onError(null)
                }
            })
        }
    }
}