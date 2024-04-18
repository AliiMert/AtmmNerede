package ali.mert.atmmnerede

import ali.mert.atmmnerede.databinding.LayoutAramaFinansbankBinding
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

class Ara_finansbank_activity: ComponentActivity(), AdapterView.OnItemClickListener {
    private lateinit var checkNetworkConnection: InternetConnection //internet bağlantısı
    lateinit var binding: LayoutAramaFinansbankBinding //tasarım ekranı erişimi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Tasarım ekranındaki elementlere erisim sağlamak için
        binding = LayoutAramaFinansbankBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.listviewFinansbank?.choiceMode = ListView.CHOICE_MODE_SINGLE
        binding.listviewFinansbank?.onItemClickListener = this

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
            .baseUrl(RetrofitInterface_finansbank.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var API = rf.create(RetrofitInterface_finansbank::class.java)
        var call = API.post

        call?.enqueue(object : Callback<List<PostModel_finansbank?>?>{
            override fun onResponse(
                call: Call<List<PostModel_finansbank?>?>,
                response: Response<List<PostModel_finansbank?>?>
            ) {
                var postlist : List<PostModel_finansbank>? = response.body() as List<PostModel_finansbank>
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
                binding.spinnerFinansbankIl.adapter = adapter
            }

            override fun onFailure(call: Call<List<PostModel_finansbank?>?>, t: Throwable) {

            }

        })

        binding.spinnerFinansbankIl.onItemSelectedListener = object :
        AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var secilenil = binding.spinnerFinansbankIl.selectedItem

                var rf2 = Retrofit.Builder()
                    .baseUrl(RetrofitInterface_finansbank.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                var API2 = rf2.create(RetrofitInterface_finansbank::class.java)
                var call2 = API2.post

                call2?.enqueue(object : Callback<List<PostModel_finansbank?>?>{
                    override fun onResponse(
                        call: Call<List<PostModel_finansbank?>?>,
                        response: Response<List<PostModel_finansbank?>?>
                    ) {

                        var postlist2 : List<PostModel_finansbank>? = response.body() as List<PostModel_finansbank>
                        var post2 = arrayOfNulls<String>(postlist2!!.size)
                        var post3 = arrayOfNulls<String>(postlist2!!.size)

                        for (i in postlist2!!.indices){
                            if (postlist2!![i]!!.CityName == secilenil){
                                post2[i] = postlist2!![i]!!.Province
                                post3[i] = postlist2!![i]!!.Name
                            }
                        }

                        var adapter2 = ArrayAdapter<String>(applicationContext,
                            R.layout.simple_dropdown_item_1line,
                            post2.distinct()
                                .filterNotNull().sortedBy { it.toString() })
                        binding.spinnerFinansbankIlce.adapter = adapter2

                        var adapter3 = ArrayAdapter<String>(applicationContext,
                            R.layout.simple_list_item_1, post3.distinct()
                                .filterNotNull().sortedBy { it.toString() })
                        binding.listviewFinansbank.adapter = adapter3

                    }
                    override fun onFailure(call: Call<List<PostModel_finansbank?>?>, t: Throwable) {
                    }
                })
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        binding.spinnerFinansbankIlce.onItemSelectedListener = object :
        AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var secilenill = binding.spinnerFinansbankIl.selectedItem
                var secilenilce = binding.spinnerFinansbankIlce.selectedItem

                var rf3 = Retrofit.Builder()
                    .baseUrl(RetrofitInterface_finansbank.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                var API3 = rf3.create(RetrofitInterface_finansbank::class.java)
                var call3 = API3.post

                call3?.enqueue(object : Callback<List<PostModel_finansbank?>?>{
                    override fun onResponse(
                        call: Call<List<PostModel_finansbank?>?>,
                        response: Response<List<PostModel_finansbank?>?>
                    ) {
                        var postlist3 : List<PostModel_finansbank>? = response.body() as List<PostModel_finansbank>
                        var post3 = arrayOfNulls<String>(postlist3!!.size)

                        for (i in postlist3!!.indices){
                            if (postlist3!![i]!!.CityName == secilenill && postlist3!![i]!!.Province == secilenilce){
                                post3[i] = postlist3!![i]!!.Name
                            }
                        }
                        var adapter3 = ArrayAdapter<String>(applicationContext,
                            R.layout.simple_list_item_1, post3.distinct()
                                .filterNotNull().sortedBy { it.toString() })
                        binding.listviewFinansbank.adapter = adapter3

                    }
                    override fun onFailure(call: Call<List<PostModel_finansbank?>?>, t: Throwable) {
                    }
                })
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }
    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

        var secilensube: String = p0?.getItemAtPosition(p2) as String
        val intent = Intent(this@Ara_finansbank_activity, Bilgi_finansbank_activity::class.java)
        intent.putExtra("secilensube",secilensube)
        startActivity(intent)
        finish()
    }
}