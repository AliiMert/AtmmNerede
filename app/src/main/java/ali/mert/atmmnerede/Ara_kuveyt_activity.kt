package ali.mert.atmmnerede

import ali.mert.atmmnerede.databinding.LayoutAramaKuveytBinding
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

class Ara_kuveyt_activity : ComponentActivity(),AdapterView.OnItemClickListener {
    private lateinit var checkNetworkConnection: InternetConnection
    lateinit var binding : LayoutAramaKuveytBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LayoutAramaKuveytBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.listviewKuveyt?.choiceMode = ListView.CHOICE_MODE_SINGLE
        binding.listviewKuveyt?.onItemClickListener = this

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
            .baseUrl(RetrofitInterface_kuveyt.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var API = rf.create(RetrofitInterface_kuveyt::class.java)
        var call = API.post

        call?.enqueue(object : Callback<List<PostModel_kuveyt?>?>{
            override fun onResponse(
                call: Call<List<PostModel_kuveyt?>?>,
                response: Response<List<PostModel_kuveyt?>?>
            ) {
                var postlist : List<PostModel_kuveyt>? = response.body() as List<PostModel_kuveyt>
                var post = arrayOfNulls<String>(postlist!!.size)

                Toast.makeText(
                    applicationContext,
                    "Bilgiler alınıyor..",
                    Toast.LENGTH_SHORT
                ).show()

                for (i in postlist!!.indices){
                    post[i] = postlist!![i]!!.CityName
                }
                var adapter = ArrayAdapter<String>(applicationContext,
                    R.layout.simple_dropdown_item_1line, post.distinct()
                        .sortedBy { it.toString() })
                binding.spinnerKuveytIl.adapter = adapter
            }
            override fun onFailure(call: Call<List<PostModel_kuveyt?>?>, t: Throwable) {
            }
        })

        binding.spinnerKuveytIl.onItemSelectedListener = object :
            AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var secilenil = binding.spinnerKuveytIl.selectedItem

                var rf2 = Retrofit.Builder()
                    .baseUrl(RetrofitInterface_kuveyt.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                var API2 = rf2.create(RetrofitInterface_kuveyt::class.java)
                var call2 = API2.post

                call2?.enqueue(object : Callback<List<PostModel_kuveyt?>?>{
                    override fun onResponse(
                        call: Call<List<PostModel_kuveyt?>?>,
                        response: Response<List<PostModel_kuveyt?>?>
                    ) {
                        var postlist2 : List<PostModel_kuveyt>? = response.body() as List<PostModel_kuveyt>
                        var post2 = arrayOfNulls<String>(postlist2!!.size)
                        var post3 = arrayOfNulls<String>(postlist2!!.size)

                        for (i in postlist2!!.indices){
                            if (postlist2!![i]!!.CityName == secilenil){
                                post2[i] = postlist2!![i]!!.CountyName
                                post3[i] = postlist2!![i]!!.Name
                            }
                        }
                        var adapter2 = ArrayAdapter<String>(applicationContext,
                            R.layout.simple_dropdown_item_1line,
                            post2.distinct().filterNotNull().sortedBy { it.toString() })
                        binding.spinnerKuveytIlce.adapter = adapter2

                        var adapter3 = ArrayAdapter<String>(applicationContext,
                            R.layout.simple_list_item_1,post3.distinct()
                                .filterNotNull().sortedBy { it.toString() })
                        binding.listviewKuveyt.adapter = adapter3
                    }
                    override fun onFailure(call: Call<List<PostModel_kuveyt?>?>, t: Throwable) {
                    }
                })
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            }
        }
        binding.spinnerKuveytIlce.onItemSelectedListener = object :
        AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var secilenil = binding.spinnerKuveytIl.selectedItem
                var secilenilce = binding.spinnerKuveytIlce.selectedItem

                var rf3 = Retrofit.Builder()
                    .baseUrl(RetrofitInterface_kuveyt.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                var API3 = rf3.create(RetrofitInterface_kuveyt::class.java)
                var call3 = API3.post

                call3?.enqueue(object : Callback<List<PostModel_kuveyt?>?>{
                    override fun onResponse(
                        call: Call<List<PostModel_kuveyt?>?>,
                        response: Response<List<PostModel_kuveyt?>?>
                    ) {
                        var postlist3 : List<PostModel_kuveyt>? = response.body() as List<PostModel_kuveyt>
                        var post3 = arrayOfNulls<String>(postlist3!!.size)

                        for (i in postlist3!!.indices){
                            if (postlist3!![i]!!.CityName == secilenil && postlist3!![i]!!.CountyName == secilenilce){
                                post3[i] = postlist3!![i]!!.Name
                            }
                        }
                        var adapter3 = ArrayAdapter<String>(applicationContext,
                            R.layout.simple_list_item_1, post3.distinct()
                                .filterNotNull().sortedBy { it.toString() })
                        binding.listviewKuveyt.adapter = adapter3
                    }

                    override fun onFailure(call: Call<List<PostModel_kuveyt?>?>, t: Throwable) {
                    }
                })
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }
    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

        var secilensube : String = p0?.getItemAtPosition(p2) as String
        val intent = Intent(this@Ara_kuveyt_activity, Bilgi_kuveyt_activity::class.java)
        intent.putExtra("secilensube",secilensube)
        startActivity(intent)
        finish()
    }

}