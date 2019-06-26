package di2k.csi.androloginv15k.network

import di2k.csi.androloginv15k.model.CSignUp
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiService {

    @FormUrlEncoded
    @POST("login")
    fun userlogin(
        @Field("email") email: String,
        @Field("password") password: String): Call<ResponseBody>

    @FormUrlEncoded
    @POST("register")
    fun createUser(
        @Field("name") nama: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("c_password") cpassword: String): Call<CSignUp>

    @FormUrlEncoded
    @GET("logout")
    fun userLogout(): Call<ResponseBody>

    @FormUrlEncoded
    @GET("getDetail")
    fun getPosts(): Call<List<ResponseBody>>

}
