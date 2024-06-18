package ali.mert.atmmnerede

import ali.mert.atmmnerede.databinding.LayoutAramaHsbcBinding
import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.ComponentActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Ara_hsbc_activity : ComponentActivity(), AdapterView.OnItemSelectedListener,
    AdapterView.OnItemClickListener {
    private lateinit var checkNetworkConnection: InternetConnection //internet bağlantısı
    lateinit var binding : LayoutAramaHsbcBinding
    lateinit var arananilisim : String
    lateinit var arananilce : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutAramaHsbcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.listviewHsbc?.choiceMode = ListView.CHOICE_MODE_SINGLE
        binding.listviewHsbc?.onItemClickListener = this

        checkNetworkConnection = InternetConnection(application)
        checkNetworkConnection.observe(this) { isConnected ->
            if (isConnected) {
                Toast.makeText(
                    applicationContext,
                    "İnternet bağlantınız başarılı.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    applicationContext,
                    "İnternet bağlantınız yok, Lütfen kontrol ediniz..",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

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
                var post = arrayOfNulls<String>(postlist!!.size)

                Toast.makeText(
                    applicationContext,
                    "Bilgiler alınıyor..",
                    Toast.LENGTH_SHORT
                ).show()

                for (i in postlist!!.indices){
                    val plakaKod = postlist[i]!!.city //plaka kodunu al
                    post[i] = PlakaToCity.map[plakaKod] //plakayı şehir adına değiştir
                }
                var adapter = ArrayAdapter<String>(applicationContext,
                    R.layout.simple_dropdown_item_1line, post.distinct()
                        .sortedBy { it.toString() })
                binding.spinnerHsbcIl.adapter = adapter
            }

            override fun onFailure(call: Call<List<PostModel_banka?>?>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

        binding.spinnerHsbcIl.onItemSelectedListener = object :
        AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var secilenilisim = binding.spinnerHsbcIl.selectedItem
                var secilenil = PlakaToCity.reverseMap[secilenilisim]

                var rf2 = Retrofit.Builder()
                    .baseUrl(RetrofitInterface_hsbc.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                var API2 = rf2.create(RetrofitInterface_hsbc::class.java)
                var call2 = API2.post

                call2?.enqueue(object : Callback<List<PostModel_banka?>?>{
                    override fun onResponse(
                        call: Call<List<PostModel_banka?>?>,
                        response: Response<List<PostModel_banka?>?>
                    ) {
                        var postlist2 : List<PostModel_banka>? = response.body() as List<PostModel_banka>
                        var post2 = arrayOfNulls<String>(postlist2!!.size)
                        var post3 = arrayOfNulls<String>(postlist2!!.size)

                        for (i in postlist2!!.indices) {
                            if (postlist2!![i]!!.city == secilenil) {
                                post2[i] = postlist2!![i]!!.district
                                post3[i] = postlist2!![i]!!.neighborhood
                            }
                        }

                        var adapter2 = ArrayAdapter<String>(applicationContext,
                            R.layout.simple_dropdown_item_1line, post2.distinct().filterNotNull().sortedBy { it.toString() })
                        binding.spinnerHsbcIlce.adapter = adapter2

                        var adapter3 = ArrayAdapter<String>(applicationContext,
                            R.layout.simple_list_item_1,
                            post3.distinct().filterNotNull().sortedBy { it.toString() })
                        binding.listviewHsbc.adapter = adapter3
                    }

                    override fun onFailure(call: Call<List<PostModel_banka?>?>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        binding.spinnerHsbcIlce.onItemSelectedListener = object :
        AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var secilenilisim = binding.spinnerHsbcIl.selectedItem
                var secilenil = PlakaToCity.reverseMap[secilenilisim]
                var secilenilce = binding.spinnerHsbcIlce.selectedItem

                var rf3 = Retrofit.Builder()
                    .baseUrl(RetrofitInterface_hsbc.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                var API3 = rf.create(RetrofitInterface_hsbc::class.java)
                var call3 = API3.post

                call3?.enqueue(object : Callback<List<PostModel_banka?>?>{
                    override fun onResponse(
                        call: Call<List<PostModel_banka?>?>,
                        response: Response<List<PostModel_banka?>?>
                    ) {
                        var postlist3 : List<PostModel_banka>? = response.body() as List<PostModel_banka>
                        var post3 = arrayOfNulls<String>(postlist3!!.size)

                        for (i in postlist3!!.indices){
                            if (postlist3!![i]!!.city == secilenil && postlist3!![i]!!.district == secilenilce){
                                post3[i] = postlist3!![i]!!.neighborhood
                            }
                        }
                        var adapter3 = ArrayAdapter<String>(applicationContext,
                            R.layout.simple_list_item_1, post3.distinct()
                                .filterNotNull().sortedBy { it.toString() })
                        binding.listviewHsbc.adapter = adapter3

                    }

                    override fun onFailure(call: Call<List<PostModel_banka?>?>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        TODO("Not yet implemented")
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        var secilensube : String = p0?.getItemAtPosition(p2) as String
        arananilisim = binding.spinnerHsbcIl.selectedItem.toString()
        var arananil = PlakaToCity.reverseMap[arananilisim]
        arananilce = binding.spinnerHsbcIlce.selectedItem.toString()

        val intent = Intent(this@Ara_hsbc_activity, Bilgi_hsbc_activity::class.java)
        intent.putExtra("secilensube",secilensube)
        intent.putExtra("arananil", arananil)
        intent.putExtra("arananilce", arananilce)
        startActivity(intent)
        finish()
    }

}