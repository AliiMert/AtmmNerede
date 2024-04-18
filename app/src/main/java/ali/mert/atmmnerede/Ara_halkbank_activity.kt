package ali.mert.atmmnerede

import ali.mert.atmmnerede.databinding.LayoutAramaHalkbankBinding
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

class Ara_halkbank_activity : ComponentActivity(), AdapterView.OnItemClickListener {
    private lateinit var checkNetworkConnection: InternetConnection //internet bağlantısı
    lateinit var binding : LayoutAramaHalkbankBinding //tasarım ekranı erişimi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Tasarım ekranındaki elementlere erisim sağlamak için
        binding = LayoutAramaHalkbankBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.listViewHalkbank?.choiceMode = ListView.CHOICE_MODE_SINGLE
        binding.listViewHalkbank?.onItemClickListener = this

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
        //retrofit ile URL bağlantısı
        var rf = Retrofit.Builder()
            .baseUrl(RetrofitInterface_halkbank.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var API = rf.create(RetrofitInterface_halkbank::class.java)
        var call = API.post

        call?.enqueue(object : Callback<List<PostModel_halkbank?>?> {
            override fun onResponse(
                call: Call<List<PostModel_halkbank?>?>,
                response: Response<List<PostModel_halkbank?>?>
            ) {
                var postlist: List<PostModel_halkbank>? =
                    response.body() as List<PostModel_halkbank>
                var post = arrayOfNulls<String>(postlist!!.size)

                Toast.makeText(
                    applicationContext,
                    "Bilgiler alınıyor..",
                    Toast.LENGTH_SHORT
                ).show()

                for (i in postlist!!.indices) { //veri sayısı kadar çalışır ve illeri alır
                    post[i] = postlist!![i]!!.dc_SEHIR
                }
                //spinner içine il listeleme
                var adapter = ArrayAdapter<String>(applicationContext,
                    R.layout.simple_dropdown_item_1line, post.distinct() //aynı il 1 kere yazdırılır
                        .sortedBy { it.toString() })
                binding.spinnerHalkbankIl.adapter = adapter
            }
            override fun onFailure(call: Call<List<PostModel_halkbank?>?>, t: Throwable) {
            }
        })
        binding.spinnerHalkbankIl.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var secilenil = binding.spinnerHalkbankIl.selectedItem

                var rf2 = Retrofit.Builder()
                    .baseUrl(RetrofitInterface_halkbank.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                var API2 = rf2.create(RetrofitInterface_halkbank::class.java)
                var call2 = API2.post

                call2?.enqueue(object : Callback<List<PostModel_halkbank?>?> {
                    override fun onResponse(
                        call: Call<List<PostModel_halkbank?>?>,
                        response: Response<List<PostModel_halkbank?>?>
                    ) {
                        var postlist2: List<PostModel_halkbank>? =
                            response.body() as List<PostModel_halkbank>
                        var post2 = arrayOfNulls<String>(postlist2!!.size)
                        var post3 = arrayOfNulls<String>(postlist2!!.size)

                        for (i in postlist2!!.indices) {
                            if (postlist2!![i]!!.dc_SEHIR == secilenil) {
                                post2[i] = postlist2!![i]!!.dc_ILCE
                                post3[i] = postlist2!![i]!!.dc_BANKA_SUBE
                            }
                        }
                        //seçilen ilin ilcelerinin spinner içinde listelenmesi
                        var adapter2 = ArrayAdapter<String>(applicationContext,
                            R.layout.simple_dropdown_item_1line, post2.distinct()
                                .filterNotNull().sortedBy { it.toString() })
                        binding.spinnerHalkbankIlce.adapter = adapter2
                        //seçilen ilçedeki şubelerin listView içinde listelenmesi
                        var adapter3 = ArrayAdapter<String>(applicationContext,
                            R.layout.simple_list_item_1, post3.distinct()
                                .filterNotNull().sortedBy { it.toString() })
                        binding.listViewHalkbank.adapter = adapter3
                    }
                    override fun onFailure(call: Call<List<PostModel_halkbank?>?>, t: Throwable) {
                    }
                })
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        //ilçe seçildikten sonra atmler listesi ekrana yazdırılır
        binding.spinnerHalkbankIlce.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var secilenil = binding.spinnerHalkbankIl.selectedItem
                var secilenilce = binding.spinnerHalkbankIlce.selectedItem
                var rf3 = Retrofit.Builder()
                    .baseUrl(RetrofitInterface_halkbank.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                var API3 = rf3.create(RetrofitInterface_halkbank::class.java)
                var call3 = API3.post

                call3?.enqueue(object : Callback<List<PostModel_halkbank?>?> {
                    override fun onResponse(
                        call: Call<List<PostModel_halkbank?>?>,
                        response: Response<List<PostModel_halkbank?>?>
                    ) {
                        var postlist3: List<PostModel_halkbank>? =
                            response.body() as List<PostModel_halkbank>
                        var post3 = arrayOfNulls<String>(postlist3!!.size)

                        for (i in postlist3!!.indices) {
                            if (postlist3!![i]!!.dc_SEHIR == secilenil && postlist3!![i]!!.dc_ILCE == secilenilce) {
                                post3[i] = postlist3!![i]!!.dc_BANKA_SUBE
                            }
                        }
                        var adapter3 = ArrayAdapter<String>(
                            applicationContext,
                            R.layout.simple_list_item_1,
                            post3.distinct().filterNotNull().sortedBy { it.toString() })
                        binding.listViewHalkbank.adapter = adapter3
                    }
                    override fun onFailure(call: Call<List<PostModel_halkbank?>?>, t: Throwable) {
                    }
                })
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }


    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        var secilensube: String = p0?.getItemAtPosition(p2) as String
        val intent = Intent(this@Ara_halkbank_activity, Bilgi_halkbank_activity::class.java)
        intent.putExtra("secilensube", secilensube)
        startActivity(intent)
        finish()
    }
}