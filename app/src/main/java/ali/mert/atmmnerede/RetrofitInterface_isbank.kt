package ali.mert.atmmnerede

import retrofit2.Call
import retrofit2.http.GET

interface RetrofitInterface_isbank {
    @get:GET("AliiMert/bankdatas/main/isbank_data")
    val post : Call<List<PostModel_isbank?>?>?
    companion object{const val BASE_URL = "https://raw.githubusercontent.com/"
    }
}
