package ali.mert.atmmnerede

import ali.mert.atmmnerede.databinding.LayoutAramaHsbcBinding
import android.R
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class Ara_hsbc_activity : ComponentActivity(), AdapterView.OnItemClickListener {
    private lateinit var checkNetworkConnection: InternetConnection //internet bağlantısı
    lateinit var binding : LayoutAramaHsbcBinding
    lateinit var arananilisim : String
    lateinit var arananilce : String
    lateinit var enyakinlati : String
    lateinit var enyakinlong : String
    lateinit var bulunanenyakinadres : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutAramaHsbcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val intent = Intent(this@Ara_hsbc_activity, BankalarListesi_activity::class.java)
                startActivity(intent)
                finish()
            }
        })
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
        try {
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
        } catch (e: Exception) {
            Toast.makeText(
                applicationContext,
                "Bilgiler alınırken hata oluştu: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
        }


        binding.spinnerHsbcIl.onItemSelectedListener = object :
        AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                try {
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
                } catch (e: Exception) {
                    Toast.makeText(
                        applicationContext,
                        "Bilgiler alınırken hata oluştu: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        binding.spinnerHsbcIlce.onItemSelectedListener = object :
        AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                try {
                    var secilenilisim = binding.spinnerHsbcIl.selectedItem
                    var secilenil = PlakaToCity.reverseMap[secilenilisim]
                    var secilenilce = binding.spinnerHsbcIlce.selectedItem

                    var rf3 = Retrofit.Builder()
                        .baseUrl(RetrofitInterface_hsbc.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                    var API3 = rf3.create(RetrofitInterface_hsbc::class.java)
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
                } catch (e: Exception) {
                    Toast.makeText(
                        applicationContext,
                        "Bilgiler alınırken hata oluştu: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        binding.buttonHsbcEnyakinatm.setOnClickListener(){
            try {
                if (checkNetworkConnection.getValue()!!) {
                    getLocation()

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
                            val postList: List<PostModel_banka>? = response.body() as List<PostModel_banka>
                            val currentLatitude = enyakinlati.toDouble() // Mevcut konumunuzun enlem değeri
                            val currentLongitude = enyakinlong.toDouble() // Mevcut konumunuzun boylam değeri

                            var nearestATM: PostModel_banka? = null
                            var minDistance = Double.MAX_VALUE

                            postList?.forEach { atm ->
                                val distance = haversine(currentLatitude, currentLongitude, atm.latitude!!.toDouble(), atm.longitude!!.toDouble())
                                if (distance < minDistance) {
                                    minDistance = distance
                                    nearestATM = atm
                                }
                            }

                            // En yakın ATM bilgilerini kullanın
                            nearestATM?.let {
                                // Yapmak istediğiniz işlemler
                                println("En yakın ATM: ${it.address}, Mesafe: $minDistance km")
                                bulunanenyakinadres = nearestATM!!.address.toString()
                                val intent = Intent(this@Ara_hsbc_activity, Bilgi_hsbc_activity::class.java)
                                intent.putExtra("bulunanenyakinadres",bulunanenyakinadres)
                                startActivity(intent)
                                finish()
                            }
                        }

                        override fun onFailure(call: Call<List<PostModel_banka?>?>, t: Throwable) {
                            // Hata durumunda yapılacak işlemler
                        }
                    })
                } else {
                    Toast.makeText(
                        this,
                        "İnternet bağlantınız yok. Lütfen kontrol ediniz.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    applicationContext,
                    "Bilgiler alınırken hata oluştu: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

    }
    private val LOCATION_PERMISSION_REQUEST_CODE = 101 // You can choose any unique integer code

    private fun getLocation() {
        try {
            val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                val locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                val locationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                var location: Location? = null

                if (locationGPS != null) {
                    location = locationGPS
                } else if (locationNetwork != null) {
                    location = locationNetwork
                }

                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude

                    enyakinlong = longitude.toString()
                    enyakinlati = latitude.toString()

                } else {
                    // Konum bilgisi alınamadı (GPS veya Network sağlayıcılarından)
                    Toast.makeText(this, "Konum bilgisi alınamadı. Lütfen GPS'inizi aktifleştirin veya konum servislerinin açık olduğundan emin olun.", Toast.LENGTH_LONG).show()
                }
            } else {
                // İzin reddedildi
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            }
        } catch (e: Exception) {
            Toast.makeText(
                applicationContext,
                "Konum bilgisi alınırken hata oluştu: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
        }

    }
    fun haversine(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val R = 6371.0 // Dünya'nın yarıçapı (kilometre)
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2) * sin(dLon / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return R * c // Kilometre cinsinden mesafe
    }


    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        try {
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
        } catch (e: Exception){
            Toast.makeText(
                applicationContext,
                "Hata oluştu: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

}