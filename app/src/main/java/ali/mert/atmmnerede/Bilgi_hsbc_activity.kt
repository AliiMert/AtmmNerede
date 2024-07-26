package ali.mert.atmmnerede

import ali.mert.atmmnerede.databinding.LayoutBilgiHsbcBinding
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Bilgi_hsbc_activity : ComponentActivity(){
    lateinit var binding: LayoutBilgiHsbcBinding
    lateinit var secilenlati : String
    lateinit var secilenlong : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LayoutBilgiHsbcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val intent = Intent(this@Bilgi_hsbc_activity, Ara_hsbc_activity::class.java)
                startActivity(intent)
                finish()
            }
        })

        Toast.makeText(applicationContext, "Bilgiler alınıyor, lütfen bekleyin..", Toast.LENGTH_SHORT).show()
        //şube seçim ekranından seçilen şubenin bigisinin alınması
        val secilensube: String? = intent.getStringExtra("secilensube")
        val arananil : String? = intent.getStringExtra("arananil")
        val arananilce : String? = intent.getStringExtra("arananilce")
        val bulunanenyakinadres : String? = intent.getStringExtra("bulunanenyakinadres")
        try {
            if (bulunanenyakinadres != null){
                var rf = Retrofit.Builder()
                    .baseUrl(RetrofitInterface_hsbc.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                var API = rf.create(RetrofitInterface_hsbc::class.java)
                var call = API.post

                call?.enqueue(object : Callback<List<PostModel_banka?>?> {
                    override fun onResponse(
                        call: Call<List<PostModel_banka?>?>,
                        response: Response<List<PostModel_banka?>?>
                    ) {
                        var postlist : List<PostModel_banka>? = response.body() as List<PostModel_banka>
                        for (i in postlist!!.indices){
                            if (postlist!![i]!!.address == bulunanenyakinadres){
                                val plakaKod = postlist[i]!!.city
                                val sehirisim = PlakaToCity.map[plakaKod]
                                binding.textViewHsbcSehir.text = "Şehir: " + sehirisim
                                binding.textViewHsbcIlce.text = "İlçe: " + postlist!![i]!!.district
                                binding.textViewHsbcMahalle.text = "Mahalle: " + postlist!![i]!!.neighborhood
                                binding.textViewHsbcAdres.text = "Adres: " + postlist!![i]!!.address

                                secilenlati = postlist!![i]!!.latitude.toString()
                                secilenlong = postlist!![i]!!.longitude.toString()
                            }
                        }
                    }

                    override fun onFailure(call: Call<List<PostModel_banka?>?>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })
            }else {
                var rf = Retrofit.Builder()
                    .baseUrl(RetrofitInterface_hsbc.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                var API = rf.create(RetrofitInterface_hsbc::class.java)
                var call = API.post

                call?.enqueue(object : Callback<List<PostModel_banka?>?> {
                    override fun onResponse(
                        call: Call<List<PostModel_banka?>?>,
                        response: Response<List<PostModel_banka?>?>
                    ) {
                        var postlist : List<PostModel_banka>? = response.body() as List<PostModel_banka>

                        for (i in postlist!!.indices){
                            if (postlist!![i]!!.city == arananil && postlist!![i]!!.district == arananilce && postlist!![i]!!.neighborhood == secilensube){
                                val plakaKod = postlist[i]!!.city
                                val sehirisim = PlakaToCity.map[plakaKod]
                                binding.textViewHsbcSehir.text = "Şehir: " + sehirisim
                                binding.textViewHsbcSehir.text = "Şehir: " + postlist!![i]!!.city
                                binding.textViewHsbcIlce.text = "İlçe: " + postlist!![i]!!.district
                                binding.textViewHsbcMahalle.text = "Mahalle: " + postlist!![i]!!.neighborhood
                                binding.textViewHsbcAdres.text = "Adres: " + postlist!![i]!!.address

                                secilenlati = postlist!![i]!!.latitude.toString()
                                secilenlong = postlist!![i]!!.longitude.toString()
                            }
                        }
                    }

                    override fun onFailure(call: Call<List<PostModel_banka?>?>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })
        }
        } catch (e: Exception) {
            Toast.makeText(
                applicationContext,
                "Bilgiler alınırken hata oluştu: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.buttonHsbcYoltarifial.setOnClickListener(){
            try {
                val atmadres : String = secilenlati + ", " + secilenlong
                val gmmIntentUri = Uri.parse("geo:0,0?q=$atmadres")
                //secilenlati degiskenini kullanabilrisin
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
            } catch (e: Exception) {
                Toast.makeText(
                    applicationContext,
                    "Yol tarifi alınırken hata oluştu: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.buttonHsbcAnasayfayadon.setOnClickListener(){
            try {
                val intent = Intent(this@Bilgi_hsbc_activity, BankaSec_activity::class.java)
                startActivity(intent)
                finish()
            } catch (e: Exception) {
                Toast.makeText(
                    applicationContext,
                    "Ana sayfaya dönülürken hata oluştu: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}