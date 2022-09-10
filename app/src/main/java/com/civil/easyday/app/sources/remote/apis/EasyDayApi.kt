package com.civil.easyday.app.sources.remote.apis

import com.civil.easyday.app.sources.ApiResponse
import com.civil.easyday.app.sources.remote.model.OTPRespModel
import com.civil.easyday.app.sources.remote.model.ProjectRespModel
import com.civil.easyday.app.sources.remote.model.UserModel
import com.civil.easyday.app.sources.remote.model.VerifyOTPRespModel
import retrofit2.http.*
import rx.Observable

interface EasyDayApi {

    @FormUrlEncoded
    @POST("user/send-otp")
    fun sendOTP(@Field("phone_number") phone_number: String): Observable<ApiResponse<OTPRespModel>>

    @FormUrlEncoded
    @POST("user/verify-otp")
    fun verifyOTP(
        @Field("phone_number") phone_number: String,
        @Field("otp") otp: String
    ): Observable<ApiResponse<VerifyOTPRespModel>>

    @FormUrlEncoded
    @POST("user/create-user")
    fun createUser(
        @Field("fullname") fullname: String,
        @Field("profession") profession: String,
        @Field("phone_number") phone_number: String
    ): Observable<ApiResponse<UserModel>>

    @GET("user/get-profile")
    fun getProfile(): Observable<ApiResponse<UserModel>>

    @GET("project/get-project")
    fun getAllProject(): Observable<ApiResponse<ArrayList<ProjectRespModel>>>

    @GET("project/get-project")
    fun getProject(@Query("project_id") project_id : Int): Observable<ApiResponse<ProjectRespModel>?>
}