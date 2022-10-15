package com.app.easyday.app.sources.remote.apis

import com.app.easyday.app.sources.ApiResponse
import com.app.easyday.app.sources.remote.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*
import rx.Observable

interface EasyDayApi {

    @FormUrlEncoded
    @POST("user/send-otp")
    fun sendOTP(
        @Field("phone_number") phone_number: String,
        @Field("country_code") country_code: String
    ): Observable<ApiResponse<OTPRespModel>>

    @FormUrlEncoded
    @POST("user/verify-otp")
    fun verifyOTP(
        @Field("phone_number") phone_number: String,
        @Field("otp") otp: String,
        @Field("country_code") country_code: String,
        @Field("device_token") device_token: String,
        @Field("device_id") device_id: String,
        @Field("device_name") device_name: String
    ): Observable<ApiResponse<VerifyOTPRespModel>>

    @Multipart
    @POST("user/create-user")
    fun createUser(
        @Part("fullname") fullname: RequestBody,
        @Part("profession") profession: RequestBody,
        @Part("phone_number") phone_number: RequestBody,
        @Part("country_code") country_code: RequestBody,
        @Part profile_image: MultipartBody.Part?,
        @Part("device_token") device_token: String,
        @Part("device_id") device_id: String,
        @Part("device_name") device_name: String
    ): Observable<ApiResponse<UserModel>>

    @GET("user/get-profile")
    fun getProfile(): Observable<ApiResponse<UserModel>>

    @GET("project/get-projects")
    fun getAllProject(): Observable<ApiResponse<ArrayList<ProjectRespModel>>>

    @GET("project/get-project")
    fun getProject(@Query("project_id") project_id: Int): Observable<ApiResponse<ProjectRespModel>?>

    @GET("user/delete-user")
    fun deleteUser(): Observable<ApiResponse<UserModel>>


    @POST("project/create-project")
    fun createProject(
        @Body addProjectRequestModel: AddProjectRequestModel
    ): Observable<ApiResponse<ProjectRespModel>>

    @POST("task/add-task")
    fun addTask(
        @Body addTaskRequestModel: AddTaskRequestModel
    ): Observable<ApiResponse<Nothing>>

    @GET("project/get-attributes")
    fun getAttributes(
        @Query("project_id") project_id: Int,
        @Query("type") type: Int
    ): Observable<ApiResponse<ArrayList<AttributeResponse>>>


//  0 tag
//  1 zone
//  2 space
    @FormUrlEncoded
    @POST("project/add-attribute")
    fun addAttribute(
        @Field("type") type: Int,
        @Field("attribute_name") attribute_name: String,
        @Field("project_id") project_id: Int
    ): Observable<ApiResponse<AttributeResponse>>

    @GET("project/get-participants")
    fun getProjectParticipants(
        @Query("project_id") project_id: Int
    ): Observable<ApiResponse<ArrayList<ProjectParticipantsModel>>>

}

