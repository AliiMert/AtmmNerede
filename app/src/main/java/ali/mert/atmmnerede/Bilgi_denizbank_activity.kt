package ali.mert.atmmnerede

import ali.mert.atmmnerede.databinding.LayoutBilgiDenizbankBinding
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

class Bilgi_denizbank_activity : ComponentActivity(){
    lateinit var binding:LayoutBilgiDenizbankBinding
    lateinit var secilenlati : String
    lateinit var secilenlong : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutBilgiDenizbankBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Toast.makeText(applicationContext, "Bilgiler alınıyor, lütfen bekleyin..", Toast.LENGTH_SHORT).show()
        //şube seçim ekranından seçilen şubenin bigisinin alınması
        val secilensube: String? = intent.getStringExtra("secilensube")
        val arananil : String? = intent.getStringExtra("arananil")
        val arananilce : String? = intent.getStringExtra("arananilce")
        var rf = Retrofit.Builder()
            .baseUrl(RetrofitInterface_denizbank.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var API = rf.create(RetrofitInterface_denizbank::class.java)
        var call = API.post

        call?.enqueue(object : Callback<List<PostModel_banka?>?> {
            override fun onResponse(
                call: Call<List<PostModel_banka?>?>,
                response: Response<List<PostModel_banka?>?>
            ) {
                var postlist: List<PostModel_banka>? =response.body() as List<PostModel_banka>
                for (i in postlist!!.indices){
                    if (postlist!![i]!!.neighborhood == secilensube && postlist!![i]!!.city == arananil && postlist!![i]!!.district == arananilce){
                        val plakaKod = postlist[i]!!.city
                        val sehirisim = PlakaToCity.map[plakaKod]
                        binding.textViewDenizbankSehir.text = "Şehir: " + sehirisim
                        binding.textViewDenizbankIlce.text = "İlçe: " + postlist!![i]!!.district
                        binding.textViewDenizbankMahalle.text = "Mahalle: " + postlist!![i]!!.neighborhood
                        binding.textViewDenizbankAdres.text = "Adres: " + postlist!![i]!!.address

                        secilenlati = postlist!![i]!!.latitude.toString()
                        secilenlong = postlist!![i]!!.longitude.toString()
                    }
                }
            }

            override fun onFailure(call: Call<List<PostModel_banka?>?>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
        binding.buttonDenizbankYoltarifial.setOnClickListener(){
            val atmadres : String = secilenlati + ", " + secilenlong
            val gmmIntentUri = Uri.parse("geo:0,0?q=$atmadres")
            //secilenlati degiskenini kullanabilrisin
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
        binding.buttonDenizbankAnasayfayadon.setOnClickListener(){
            val intent = Intent(this@Bilgi_denizbank_activity, BankaSec_activity::class.java)
            startActivity(intent)
            finish()
        }
    }
}