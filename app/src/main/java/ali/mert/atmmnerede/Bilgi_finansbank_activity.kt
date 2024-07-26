package ali.mert.atmmnerede

import ali.mert.atmmnerede.databinding.LayoutBilgiFinansbankBinding
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

class Bilgi_finansbank_activity : ComponentActivity(){
    lateinit var binding : LayoutBilgiFinansbankBinding
    lateinit var secilenlati : String
    lateinit var secilenlong : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutBilgiFinansbankBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val intent = Intent(this@Bilgi_finansbank_activity, Ara_finansbank_activity::class.java)
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
                var rf4 = Retrofit.Builder()
                    .baseUrl(RetrofitInterface_finansbank.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                var API4 = rf4.create(RetrofitInterface_finansbank::class.java)
                var call4 = API4.post

                call4?.enqueue(object : Callback<List<PostModel_finansbank?>?>{
                    override fun onResponse(
                        call: Call<List<PostModel_finansbank?>?>,
                        response: Response<List<PostModel_finansbank?>?>
                    ) {
                        var postlist4 : List<PostModel_finansbank>? = response.body() as List<PostModel_finansbank>

                        for (i in postlist4!!.indices){
                            if (postlist4!![i]!!.Adress == bulunanenyakinadres){
                                binding.textViewFinansbankSehir.text = "Şehir: " + postlist4!![i]!!.CityName
                                binding.textViewFinansbankIlce.text = "İlçe: " + postlist4!![i]!!.Province
                                binding.textViewFinansbankAtmisim.text = "ATM İsmi: " + postlist4!![i]!!.Name
                                binding.textViewFinansbankAdres.text = "Adres: " + postlist4!![i]!!.Adress
                                var euralma = postlist4!![i]!!.BuyEuroParity?.toInt()
                                if (euralma == 1){
                                    binding.textViewFinansbankeurcekme.text = "EUR Alma: Mevcut"
                                }else
                                    binding.textViewFinansbankeurcekme.text = "EUR Alma: Yok"
                                var eursatma = postlist4!![i]!!.SellEuroParity?.toInt()
                                if (eursatma == 1){
                                    binding.textViewfinansbankeuryatirma.text = "EUR Satma: Mevcut"
                                }else
                                    binding.textViewfinansbankeuryatirma.text = "EUR Satma: Yok"
                                var usdalma = postlist4!![i]!!.BuyUSDParity?.toInt()
                                if (usdalma == 1){
                                    binding.textViewfinansbankusdcekme.text = "USD Alma: Mevcut"
                                }else
                                    binding.textViewfinansbankusdcekme.text = "USD Alma: Yok"
                                var usdsatma = postlist4!![i]!!.SellUSDParity?.toInt()
                                if (usdsatma == 1){
                                    binding.textViewfinansbankusdyatirma.text = "USD Satma: Mevcut"
                                }else
                                    binding.textViewfinansbankusdyatirma.text = "USD Satma: Yok"
                                secilenlati = postlist4!![i]!!.Latitude.toString()
                                secilenlati = postlist4!![i]!!.Latitude.toString()
                                secilenlong = postlist4!![i]!!.Longtitude.toString()
                            }

                        }
                    }

                    override fun onFailure(call: Call<List<PostModel_finansbank?>?>, t: Throwable) {
                    }
                })
            }else{
                var rf4 = Retrofit.Builder()
                    .baseUrl(RetrofitInterface_finansbank.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                var API4 = rf4.create(RetrofitInterface_finansbank::class.java)
                var call4 = API4.post

                call4?.enqueue(object : Callback<List<PostModel_finansbank?>?>{
                    override fun onResponse(
                        call: Call<List<PostModel_finansbank?>?>,
                        response: Response<List<PostModel_finansbank?>?>
                    ) {
                        var postlist4 : List<PostModel_finansbank>? = response.body() as List<PostModel_finansbank>

                        for (i in postlist4!!.indices){
                            if (postlist4!![i]!!.Name == secilensube){
                                binding.textViewFinansbankSehir.text = "Şehir: " + postlist4!![i]!!.CityName
                                binding.textViewFinansbankIlce.text = "İlçe: " + postlist4!![i]!!.Province
                                binding.textViewFinansbankAtmisim.text = "ATM İsmi: " + postlist4!![i]!!.Name
                                binding.textViewFinansbankAdres.text = "Adres: " + postlist4!![i]!!.Adress
                                var euralma = postlist4!![i]!!.BuyEuroParity?.toInt()
                                if (euralma == 1){
                                    binding.textViewFinansbankeurcekme.text = "EUR Alma: Mevcut"
                                }else
                                    binding.textViewFinansbankeurcekme.text = "EUR Alma: Yok"
                                var eursatma = postlist4!![i]!!.SellEuroParity?.toInt()
                                if (eursatma == 1){
                                    binding.textViewfinansbankeuryatirma.text = "EUR Satma: Mevcut"
                                }else
                                    binding.textViewfinansbankeuryatirma.text = "EUR Satma: Yok"
                                var usdalma = postlist4!![i]!!.BuyUSDParity?.toInt()
                                if (usdalma == 1){
                                    binding.textViewfinansbankusdcekme.text = "USD Alma: Mevcut"
                                }else
                                    binding.textViewfinansbankusdcekme.text = "USD Alma: Yok"
                                var usdsatma = postlist4!![i]!!.SellUSDParity?.toInt()
                                if (usdsatma == 1){
                                    binding.textViewfinansbankusdyatirma.text = "USD Satma: Mevcut"
                                }else
                                    binding.textViewfinansbankusdyatirma.text = "USD Satma: Yok"
                                secilenlati = postlist4!![i]!!.Latitude.toString()
                                secilenlati = postlist4!![i]!!.Latitude.toString()
                                secilenlong = postlist4!![i]!!.Longtitude.toString()
                            }
                        }
                    }

                    override fun onFailure(call: Call<List<PostModel_finansbank?>?>, t: Throwable) {
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

        binding.buttonBilgiFinansbankYoltarifi.setOnClickListener(){
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

        binding.buttonBilgiFinansbankAnasayfayadon.setOnClickListener(){
            try {
                val intent = Intent(this@Bilgi_finansbank_activity, BankaSec_activity::class.java)
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