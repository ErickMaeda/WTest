package pt.wtest.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET


interface RestApi {

    @GET("/centraldedados/codigos_postais/e9982d5b6b2bc0084d3115da473bc24d72901a35/data/codigos_postais.csv")
    fun getPostalCodes(): Call<ResponseBody>

}