package ali.mert.atmmnerede

import ali.mert.atmmnerede.databinding.LayoutBilgiHalkbankBinding
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

class Bilgi_halkbank_activity : ComponentActivity(){
    lateinit var binding : LayoutBilgiHalkbankBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutBilgiHalkbankBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Toast.makeText(applicationContext, "Bilgiler alınıyor, lütfen bekleyin..", Toast.LENGTH_SHORT).show()
        //şube seçim ekranından seçilen şubenin bigisinin alınması
        val secilensube: String? = intent.getStringExtra("secilensube")

        var rf4 = Retrofit.Builder()
            .baseUrl(RetrofitInterface_halkbank.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var API4 = rf4.create(RetrofitInterface_halkbank::class.java)
        var call4 = API4.post
        binding.textViewHalkbankSehir.text = secilensube

        call4?.enqueue(object : Callback<List<PostModel_halkbank?>?>{
            override fun onResponse(
                call: Call<List<PostModel_halkbank?>?>,
                response: Response<List<PostModel_halkbank?>?>
            ) {
                var postlist4 : List<PostModel_halkbank>? = response.body() as List<PostModel_halkbank>

                for (i in postlist4!!.indices){
                    if (postlist4!![i]!!.dc_BANKA_SUBE == secilensube){
                        binding.textViewHalkbankSehir.text = "Şehir: " + postlist4!![i]!!.dc_SEHIR
                        binding.textViewHalkbankIlce.text = "İlçe: " + postlist4!![i]!!.dc_ILCE
                        binding.textViewHallkbankAdresadi.text = "Adres Adı: " + postlist4!![i]!!.dc_ADRES_ADI
                        binding.textViewHalkbankAdres.text = "Adres: " + postlist4!![i]!!.dc_ADRES
                        binding.textViewHalkbankSubeadi.text = "Şube Kodu: " + postlist4!![i]!!.dc_BANKA_SUBE
                        binding.textViewHalkbakPostakodu.text = "Posta Kodu: " + postlist4!![i]!!.dc_POSTA_KODU
                        binding.textViewHalkbankBolgekoor.text = "Bölge Koordinatörlüğü: " + postlist4!![i]!!.dc_BOLGE_KOORDINATORLUGU

                    }
                }
            }
            override fun onFailure(call: Call<List<PostModel_halkbank?>?>, t: Throwable) {
            }
        })

        binding.buttonBilgiHalkbankYoltarifi.setOnClickListener(){
            val subeadres: String = binding.textViewHalkbankAdres.text.toString()
            val adres: String = subeadres.replace("Adres: ","")
            val gmmIntentUri = Uri.parse("geo:0,0?q=$adres")

            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
        binding.buttonBilgiHalkbankAnasayfayadon.setOnClickListener(){
            val intent = Intent(this@Bilgi_halkbank_activity, BankaSec_activity::class.java)
            startActivity(intent)
            finish()
        }
    }
}