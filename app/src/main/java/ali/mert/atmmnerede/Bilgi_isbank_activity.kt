package ali.mert.atmmnerede

import ali.mert.atmmnerede.databinding.LayoutBilgiIsbankBinding
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Bilgi_isbank_activity : ComponentActivity() {
    lateinit var binding : LayoutBilgiIsbankBinding //tasarım ekranı erişimi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutBilgiIsbankBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Toast.makeText(applicationContext, "Bilgiler alınıyor, lütfen bekleyin..", Toast.LENGTH_LONG).show()
        //şube seçim ekranından seçilen şubenin bigisinin alınması
        val secilensube: String? = intent.getStringExtra("secilensube")

        var rf4 = Retrofit.Builder()
            .baseUrl(RetrofitInterface_isbank.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var API4 = rf4.create(RetrofitInterface_isbank::class.java)
        var call4 = API4.post
        binding.textViewIsbankSehir.text = secilensube

        call4?.enqueue(object : Callback<List<PostModel_isbank?>?>{
            override fun onResponse(
                call: Call<List<PostModel_isbank?>?>,
                response: Response<List<PostModel_isbank?>?>
            ) {
                var postlist4 : List<PostModel_isbank>? = response.body() as List<PostModel_isbank>


                for (i in postlist4!!.indices){
                    if (postlist4!![i]!!.MarkerID == secilensube){
                        binding.textViewIsbankSehir.text = "Şehir: " + postlist4!![i]!!.CityName
                        binding.textViewIsbankIlce.text = "İlçe: " + postlist4!![i]!!.Province
                        binding.textViewIsbankAtmisim.text = "ATM İsmi: " + postlist4!![i]!!.MarkerID
                        binding.textViewIsbankAdres.text = "Adres: " + postlist4!![i]!!.Address
                        var exchange = postlist4!![i]!!.exchange?.toInt()
                        if (exchange == 1){
                            binding.textViewIsbankExchange.text = "Exchange: Mevcut"
                        }else
                            binding.textViewIsbankExchange.text = "Exchange: Mevcut Değil"
                        var EUR = postlist4!![i]!!.dovizCekmeEUR?.toInt()
                        if (EUR == 1){
                            binding.textViewIsbankEurcekme.text = "EUR Çekme: Mevcut"
                        }else
                            binding.textViewIsbankEurcekme.text = "EUR Çekme: Mevcut Değil"
                        var USD = postlist4!![i]!!.dovizCekmeEUR?.toInt()
                        if (USD == 1){
                            binding.textViewIsbankUsdcekme.text = "USD Çekme: Mevcut"
                        }else
                            binding.textViewIsbankUsdcekme.text = "USD Çekme: Mevcut Değil"

                    }
                }
            }

            override fun onFailure(call: Call<List<PostModel_isbank?>?>, t: Throwable) {
            }

        })

        binding.buttonBilgiIsbankYoltarifi.setOnClickListener() {
            val subeadres: String = binding.textViewIsbankAdres.text.toString()
            val adres: String = subeadres.replace("Adres: ","iş bankası ")
            val gmmIntentUri = Uri.parse("geo:0,0?q=$adres")

            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

        binding.buttonBilgiIsbankAnasayfayadon.setOnClickListener(){
            val intent = Intent(this@Bilgi_isbank_activity, BankaSec_activity::class.java)
            startActivity(intent)
            finish()
        }
    }
}