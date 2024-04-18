package ali.mert.atmmnerede

import retrofit2.Call
import retrofit2.http.GET

interface RetrofitInterface_halkbank {
    @get:GET("AliiMert/bankdatas/main/halkbank_data")
    val post : Call<List<PostModel_halkbank?>?>?
    companion object{const val BASE_URL = "https://raw.githubusercontent.com/"
    }
}