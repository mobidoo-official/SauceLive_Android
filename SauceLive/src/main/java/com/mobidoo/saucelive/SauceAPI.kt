package com.mobidoo.saucelive

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

internal interface SauceAPI {

    @GET("product/payment")
    fun createPaymentTracker(
        @Query("orderCallBackId") orderCallBackId: String,
        @Query("orderId") orderId: String,
        @Query("payClickTime") payClickTime: String,
        @Query("productQuantity") productQuantity: String,
        @Query("amount") amount: String,
        @Query("memberId") memberId: String
    ): Call<ErrorResponse>

    @POST("product/payments")
    fun createPaymentListTracker(
        @Body payments: List<PaymentData>
    ): Call<ErrorResponse>

    @PATCH("product/payment")
    fun updatePaymentTracker(
        @Query("orderCallBackId") orderCallBackId: String,
        @Query("orderId") orderId: String,
        @Query("findProductQuantity") findProductQuantity: String,
        @Query("findAmount") findAmount: String,
        @Query("updateProductQuantity") updateProductQuantity: String,
        @Query("updateAmount") updateAmount: String
    ): Call<ErrorResponse>

    @DELETE("product/payment")
    fun deletePaymentTracker(
        @Query("orderCallBackId") orderCallBackId: String,
        @Query("orderId") orderId: String,
        @Query("payCancelTime") payCancelTime: String
    ): Call<ErrorResponse>

    @POST("internal/token")
    fun createUserAccessToken(
        @Body member: Member
    ): Call<UserAccessTokenResponse>
}

interface MemberObjectCallback {
    fun onSuccess()
    fun onError(error: String?)
}

interface UserAccessTokenCallback {
    fun onSuccess(accessToken: String)
    fun onError(error: String?)
}

interface PaymentTrackerCallback {
    fun onSuccess()
    fun onError(error: ErrorResponse?)
}

data class UserAccessTokenResponse(
    val code: String,
    val timestamp: Long,
    val requestId: String,
    val message: String,
    val response: SuccessResponseDetails
)

data class SuccessResponseDetails(
    val accessToken: String,
    val partnerId: String,
    val memberId: String,
    val nickName: String,
    val age: String?,
    val gender: String?,
    val memberType: Int?
)



data class ErrorResponse(
    val code: String,
    val timestamp: Long,
    val requestId: String,
    val message: String,
    val response: ErrorResponseDetails
)

data class ErrorResponseDetails(
    val validError: List<String>
)

data class PaymentData(
    @SerializedName("orderCallBackId")
    val orderCallBackId: String,

    @SerializedName("orderId")
    val orderId: String,

    @SerializedName("payClickTime")
    val payClickTime: String,

    @SerializedName("productQuantity")
    val productQuantity: String,

    @SerializedName("amount")
    val amount: String,

    @SerializedName("memberId")
    val memberId: String
)

data class Member(
    val memberId: String,
    val nickName: String,
    val age: String?,
    val gender: String?,
    val memberType: Int?,
    val partnerId: String? = null
)

