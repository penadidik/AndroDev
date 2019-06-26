package di2k.csi.androloginv15k.network

object UtilsAPI {
    val BASE_URL_API = "http://10.0.2.2:8000/api/"
    val apiService: ApiService
        get() = RetrofitClient.getClient(BASE_URL_API)!!.create(ApiService::class.java)

}