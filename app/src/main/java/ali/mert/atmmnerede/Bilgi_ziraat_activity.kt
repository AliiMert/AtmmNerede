package ali.mert.atmmnerede

import ali.mert.atmmnerede.databinding.LayoutBilgiZiraatBinding
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

class Bilgi_ziraat_activity : ComponentActivity() {
    lateinit var binding: LayoutBilgiZiraatBinding
    lateinit var secilenlati: String
    lateinit var secilenlong: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent(this@Bilgi_ziraat_activity, Ara_ziraat_activity::class.java)
                startActivity(intent)
                finish()
            }
        })

        binding = LayoutBilgiZiraatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Toast.makeText(applicationContext, "Bilgiler alınıyor, lütfen bekleyin..", Toast.LENGTH_SHORT).show()
        //şube seçim ekranından seçilen şubenin bigisinin alınması
        val secilensube: String? = intent.getStringExtra("secilensube")
        val arananil: String? = intent.getStringExtra("arananil")
        val arananilce: String? = intent.getStringExtra("arananilce")
        val bulunanenyakinadres: String? = intent.getStringExtra("bulunanenyakinadres")

        try {
            if (bulunanenyakinadres != null) {
                var rf = Retrofit.Builder()
                    .baseUrl(RetrofitInterface_ziraat.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                var API = rf.create(RetrofitInterface_ziraat::class.java)
                var call = API.post

                call?.enqueue(object : Callback<List<PostModel_banka?>?> {
                    override fun onResponse(
                        call: Call<List<PostModel_banka?>?>,
                        response: Response<List<PostModel_banka?>?>
                    ) {
                        var postlist: List<PostModel_banka>? = response.body() as List<PostModel_banka>
                        for (i in postlist!!.indices) {
                            if (postlist!![i]!!.address == bulunanenyakinadres) {
                                val plakaKod = postlist[i]!!.city
                                val sehirisim = PlakaToCity.map[plakaKod]
                                binding.textViewZiraatSehir.text = "Şehir: " + sehirisim
                                binding.textViewZiraatIlce.text = "İlçe: " + postlist!![i]!!.district
                                binding.textViewZiraatMahalle.text = "Mahalle: " + postlist!![i]!!.neighborhood
                                binding.textViewZiraatAdres.text = "Adres: " + postlist!![i]!!.address

                                secilenlati = postlist!![i]!!.latitude.toString()
                                secilenlong = postlist!![i]!!.longitude.toString()
                            }
                        }
                    }

                    override fun onFailure(call: Call<List<PostModel_banka?>?>, t: Throwable) {
                        Toast.makeText(
                            applicationContext,
                            "Bilgiler alınırken hata oluştu: ${t.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            } else {
                var rf = Retrofit.Builder()
                    .baseUrl(RetrofitInterface_ziraat.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                var API = rf.create(RetrofitInterface_ziraat::class.java)
                var call = API.post

                call?.enqueue(object : Callback<List<PostModel_banka?>?> {
                    override fun onResponse(
                        call: Call<List<PostModel_banka?>?>,
                        response: Response<List<PostModel_banka?>?>
                    ) {
                        var postlist: List<PostModel_banka>? = response.body() as List<PostModel_banka>
                        for (i in postlist!!.indices) {
                            if (postlist!![i]!!.city == arananil && postlist!![i]!!.district == arananilce && postlist!![i]!!.neighborhood == secilensube) {
                                val plakaKod = postlist[i]!!.city
                                val sehirisim = PlakaToCity.map[plakaKod]
                                binding.textViewZiraatSehir.text = "Şehir: " + sehirisim
                                binding.textViewZiraatIlce.text = "İlçe: " + postlist!![i]!!.district
                                binding.textViewZiraatMahalle.text = "Mahalle: " + postlist!![i]!!.neighborhood
                                binding.textViewZiraatAdres.text = "Adres: " + postlist!![i]!!.address

                                secilenlati = postlist!![i]!!.latitude.toString()
                                secilenlong = postlist!![i]!!.longitude.toString()
                            }
                        }
                    }

                    override fun onFailure(call: Call<List<PostModel_banka?>?>, t: Throwable) {
                        Toast.makeText(
                            applicationContext,
                            "Bilgiler alınırken hata oluştu: ${t.message}",
                            Toast.LENGTH_SHORT
                        ).show()
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

        binding.buttonZiraatYoltarifial.setOnClickListener() {
            //butona basıldığında gerçekleşek kodlar
            try {
                //seçilen ATM'nin enlem boylam bilgileri
                val atmadres: String = secilenlati + ", " + secilenlong
                val gmmIntentUri = Uri.parse("geo:0,0?q=$atmadres")
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
        binding.buttonZiraatAnasayfayadon.setOnClickListener() {
            // butona basıldığında gerçekleşecek kod
            //ana sayfaya intent oluşturuldu ve ana sayfanın açılması sağlandı

            try {
                val intent = Intent(this@Bilgi_ziraat_activity, BankaSec_activity::class.java)
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
