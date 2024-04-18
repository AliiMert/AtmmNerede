package ali.mert.atmmnerede

import retrofit2.Call
import retrofit2.http.GET

interface RetrofitInterface_finansbank {
    @get:GET("AliiMert/bankdatas/main/finansbank_data")
    val post : Call<List<PostModel_finansbank?>?>?
    companion object{const val BASE_URL = "https://raw.githubusercontent.com/"
    }
}
