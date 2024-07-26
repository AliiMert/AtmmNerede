package ali.mert.atmmnerede

import retrofit2.Call
import retrofit2.http.GET

interface RetrofitInterface_ing {
    @get:GET("AliiMert/bankdatas/main/ing_data")
    val post: Call<List<PostModel_banka?>?>?
    companion object {
        const val BASE_URL = "https://raw.githubusercontent.com/"
    }
}