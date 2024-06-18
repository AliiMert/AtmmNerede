package ali.mert.atmmnerede

import ali.mert.atmmnerede.databinding.LayoutBilgiKuveytBinding
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

class Bilgi_kuveyt_activity : ComponentActivity(){

    lateinit var binding : LayoutBilgiKuveytBinding
    lateinit var secilenlati : String
    lateinit var secilenlong : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutBilgiKuveytBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Toast.makeText(applicationContext, "Bilgiler alınıyor, lütfen bekleyin..", Toast.LENGTH_SHORT).show()

        val secilensube : String? = intent.getStringExtra("secilensube")

        var rf4 = Retrofit.Builder()
            .baseUrl(RetrofitInterface_kuveyt.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var API4 = rf4.create(RetrofitInterface_kuveyt::class.java)
        var call4 = API4.post

        call4?.enqueue(object : Callback<List<PostModel_kuveyt?>?> {
            override fun onResponse(
                call: Call<List<PostModel_kuveyt?>?>,
                response: Response<List<PostModel_kuveyt?>?>
            ) {
                var postlist4 : List<PostModel_kuveyt>? = response.body() as List<PostModel_kuveyt>

                for (i in postlist4!!.indices){
                    if (postlist4!![i]!!.Name == secilensube){
                        binding.textViewKuveytSehir.text = "Şehir: " + postlist4!![i]!!.CityName
                        binding.textViewKuveytIlce.text = "İlçe: " + postlist4!![i]!!.CountyName
                        binding.textViewKuveytAtmad.text = "ATM İsmi: " + postlist4!![i]!!.Name
                        binding.textViewKuveytAdres.text = "Adres: " + postlist4!![i]!!.Address
                        var exchange = postlist4!![i]!!.IsExchange?.toBoolean()
                        if (exchange == true){
                            binding.textViewKuveytExchange.text = "Exchange: Mevcut"
                        }else
                            binding.textViewKuveytExchange.text = "Exchange: Mevcut Değil"
                        var dolar = postlist4!![i]!!.IsDollarDispensible?.toBoolean()
                        if (dolar == true){
                            binding.textViewKuveytDolar.text = "Dolarla İşlem: Mevcut"
                        }else
                            binding.textViewKuveytDolar.text = "Dolarla İşlem: Mevcut Değil"
                        var gold = postlist4!![i]!!.IsGoldDispensible?.toBoolean()
                        if (gold == true){
                            binding.textViewKuveytGold.text = "Altınla İşlem: Mevcut"
                        }else
                            binding.textViewKuveytGold.text = "Altınla İşlem: Mevcut Değil"
                        secilenlati = postlist4!![i]!!.Latitude.toString()
                        secilenlong = postlist4!![i]!!.Longitude.toString()
                    }
                }
            }
            override fun onFailure(call: Call<List<PostModel_kuveyt?>?>, t: Throwable) {
            }
        })
        binding.buttonBilgiKuveytYoltarifial.setOnClickListener(){
            val atmadres : String = secilenlati + ", " + secilenlong
            val gmmIntentUri = Uri.parse("geo:0,0?q=$atmadres")

            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
        binding.buttonBilgiKuveytAnasayfa.setOnClickListener(){
            val intent = Intent(this@Bilgi_kuveyt_activity, BankaSec_activity::class.java)
            startActivity(intent)
            finish()
        }

    }
}