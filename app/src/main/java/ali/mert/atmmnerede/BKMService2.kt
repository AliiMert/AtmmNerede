package ali.mert.atmmnerede

import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException


public class BKMService2 {


    public fun  getBankList(onFailureFunc: (call: Call, exception: IOException) -> Unit, onSuccesFunc: (response: GetBankListResponseModel) -> Unit) {
        val client = OkHttpClient()
        val mediaType = "application/json".toMediaType()
        val body = "".toRequestBody(mediaType)
        val request = Request.Builder()
            .url("https://enyakinatm.bkm.com.tr/web/bankList")
            .post(body)
            .addHeader("Content-Type", "application/json")
            .addHeader("Origin", "https://enyakinatm.bkm.com.tr")
            .addHeader("Cookie", "BIGipServer~Express~pool_enyakinatm.bkm.com.tr_443=901032128.47873.0000; TS01f143be=0100593e022f4e68744490e21da663b976744a214f46ad34e08ca27a9b1cbc6f9f861fd1d6cee0f67ae340832409755713dc48ac4f7ffea9bd112efaa942cda8c543e72d16")
            .build()
        val response = client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                onFailureFunc(call, e)
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    val responseBodyStr = response.body?.string()
                    val gson = Gson()

                    // JSON veriyi Bank sınıfına deserialize et
                    val bankResponseModel = gson.fromJson(responseBodyStr, GetBankListResponseModel::class.java)
                    onSuccesFunc(bankResponseModel)
                }
            }
        })


    }
}