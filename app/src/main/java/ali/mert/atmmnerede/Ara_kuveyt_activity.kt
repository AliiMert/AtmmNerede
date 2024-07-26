package ali.mert.atmmnerede

import ali.mert.atmmnerede.databinding.LayoutAramaKuveytBinding
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

class Ara_kuveyt_activity : ComponentActivity(),AdapterView.OnItemClickListener {
    private lateinit var checkNetworkConnection: InternetConnection
    lateinit var binding : LayoutAramaKuveytBinding
    lateinit var enyakinlati : String
    lateinit var enyakinlong : String
    lateinit var bulunanenyakinadres : String
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
        try {
            var rf = Retrofit.Builder()
                .baseUrl(RetrofitInterface_kuveyt.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            var API = rf.create(RetrofitInterface_kuveyt::class.java)
            var call = API.post

            call?.enqueue(object : Callback<List<PostModel_kuveyt?>?> {
                override fun onResponse(
                    call: Call<List<PostModel_kuveyt?>?>,
                    response: Response<List<PostModel_kuveyt?>?>
                ) {
                    var postlist: List<PostModel_kuveyt>? = response.body() as List<PostModel_kuveyt>
                    var post = arrayOfNulls<String>(postlist!!.size)

                    Toast.makeText(
                        applicationContext,
                        "Bilgiler alınıyor..",
                        Toast.LENGTH_SHORT
                    ).show()

                    for (i in postlist!!.indices) {
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
        } catch (e: Exception) {
            Toast.makeText(
                applicationContext,
                "Bilgiler alınırken hata oluştu: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
        }


        binding.spinnerKuveytIl.onItemSelectedListener = object :
            AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                try {
                    var secilenil = binding.spinnerKuveytIl.selectedItem

                    var rf2 = Retrofit.Builder()
                        .baseUrl(RetrofitInterface_kuveyt.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                    var API2 = rf2.create(RetrofitInterface_kuveyt::class.java)
                    var call2 = API2.post

                    call2?.enqueue(object : Callback<List<PostModel_kuveyt?>?> {
                        override fun onResponse(
                            call: Call<List<PostModel_kuveyt?>?>,
                            response: Response<List<PostModel_kuveyt?>?>
                        ) {
                            var postlist2: List<PostModel_kuveyt>? =
                                response.body() as List<PostModel_kuveyt>
                            var post2 = arrayOfNulls<String>(postlist2!!.size)
                            var post3 = arrayOfNulls<String>(postlist2!!.size)

                            for (i in postlist2!!.indices) {
                                if (postlist2!![i]!!.CityName == secilenil) {
                                    post2[i] = postlist2!![i]!!.CountyName
                                    post3[i] = postlist2!![i]!!.Name
                                }
                            }
                            var adapter2 = ArrayAdapter<String>(applicationContext,
                                R.layout.simple_dropdown_item_1line,
                                post2.distinct().filterNotNull().sortedBy { it.toString() })
                            binding.spinnerKuveytIlce.adapter = adapter2

                            var adapter3 = ArrayAdapter<String>(applicationContext,
                                R.layout.simple_list_item_1, post3.distinct()
                                    .filterNotNull().sortedBy { it.toString() })
                            binding.listviewKuveyt.adapter = adapter3
                        }

                        override fun onFailure(call: Call<List<PostModel_kuveyt?>?>, t: Throwable) {
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
            }

            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            }
        }
        binding.spinnerKuveytIlce.onItemSelectedListener = object :
            AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                try {
                    var secilenil = binding.spinnerKuveytIl.selectedItem
                    var secilenilce = binding.spinnerKuveytIlce.selectedItem

                    var rf3 = Retrofit.Builder()
                        .baseUrl(RetrofitInterface_kuveyt.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                    var API3 = rf3.create(RetrofitInterface_kuveyt::class.java)
                    var call3 = API3.post

                    call3?.enqueue(object : Callback<List<PostModel_kuveyt?>?> {
                        override fun onResponse(
                            call: Call<List<PostModel_kuveyt?>?>,
                            response: Response<List<PostModel_kuveyt?>?>
                        ) {
                            var postlist3: List<PostModel_kuveyt>? =
                                response.body() as List<PostModel_kuveyt>
                            var post3 = arrayOfNulls<String>(postlist3!!.size)

                            for (i in postlist3!!.indices) {
                                if (postlist3!![i]!!.CityName == secilenil && postlist3!![i]!!.CountyName == secilenilce) {
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
                } catch (e: Exception) {
                    Toast.makeText(
                        applicationContext,
                        "Bilgiler alınırken hata oluştu: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val intent = Intent(this@Ara_kuveyt_activity, BankalarListesi_activity::class.java)
                startActivity(intent)
                finish()
            }
        })
        binding.buttonKuveytEnyakinatm.setOnClickListener() {
            try {
                if (checkNetworkConnection.getValue()!!) {
                    getLocation()

                    var rf = Retrofit.Builder()
                        .baseUrl(RetrofitInterface_kuveyt.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                    var API = rf.create(RetrofitInterface_kuveyt::class.java)
                    var call = API.post

                    call?.enqueue(object : Callback<List<PostModel_kuveyt?>?> {
                        override fun onResponse(
                            call: Call<List<PostModel_kuveyt?>?>,
                            response: Response<List<PostModel_kuveyt?>?>
                        ) {
                            val postList: List<PostModel_kuveyt>? =
                                response.body() as List<PostModel_kuveyt>
                            val currentLatitude =
                                enyakinlati.toDouble() // Mevcut konumunuzun enlem değeri
                            val currentLongitude =
                                enyakinlong.toDouble() // Mevcut konumunuzun boylam değeri

                            var nearestATM: PostModel_kuveyt? = null
                            var minDistance = Double.MAX_VALUE

                            postList?.forEach { atm ->
                                val distance = haversine(
                                    currentLatitude,
                                    currentLongitude,
                                    atm.Latitude!!.toDouble(),
                                    atm.Longitude!!.toDouble()
                                )
                                if (distance < minDistance) {
                                    minDistance = distance
                                    nearestATM = atm
                                }
                            }


                            // En yakın ATM bilgilerini kullanın
                            nearestATM?.let {
                                // Yapmak istediğiniz işlemler
                                println("En yakın ATM: ${it.Address}, Mesafe: $minDistance km")
                                bulunanenyakinadres = nearestATM!!.Address.toString()
                                val intent =
                                    Intent(this@Ara_kuveyt_activity, Bilgi_kuveyt_activity::class.java)
                                intent.putExtra("bulunanenyakinadres", bulunanenyakinadres)
                                startActivity(intent)
                                finish()
                            }
                        }

                        override fun onFailure(call: Call<List<PostModel_kuveyt?>?>, t: Throwable) {
                            TODO("Not yet implemented")
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

            val intent = Intent(this@Ara_kuveyt_activity, Bilgi_kuveyt_activity::class.java)
            intent.putExtra("secilensube",secilensube)
            startActivity(intent)
            finish()
        } catch (e: Exception) {
            Toast.makeText(
                applicationContext,
                "Hata oluştu: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

}