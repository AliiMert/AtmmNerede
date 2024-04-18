package ali.mert.atmmnerede

import retrofit2.Call
import retrofit2.http.GET

interface RetrofitInterface_kuveyt {
    @get:GET("AliiMert/bankdatas/main/kuveyt_data")
    val post : Call<List<PostModel_kuveyt?>?>?
    companion object{const val BASE_URL = "https://raw.githubusercontent.com/"
    }
}
