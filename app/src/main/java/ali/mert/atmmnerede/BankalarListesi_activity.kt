package ali.mert.atmmnerede

import ali.mert.atmmnerede.databinding.LayoutBankaKartBinding
import ali.mert.atmmnerede.databinding.LayoutBankalarListBinding
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import androidx.activity.ComponentActivity

class BankalarListesi_activity : ComponentActivity(), AdapterView.OnItemClickListener{
    lateinit var binding: LayoutBankalarListBinding
    var constlist = ArrayList<Bankalar>()
    var adapter : BankalarAdapter ?= null

   /* fun onGetBankListFailed(call: Call, exception: IOException)
    {

    }
    fun onGetBankListSuccess(response: GetBankListResponseModel)
    {
        for (banka in response.Bankalar)
        {
            constlist.add(Bankalar(banka.name, R.drawable.isbankasilogosu,banka.id))
        }
        //listViewin verileri kullanabilmesi için adapter değişkeni

        runOnUiThread({
            adapter = BankalarAdapter(this,constlist)
            binding.listView.adapter = adapter
        })
    }*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//tasarım ekranındaki nesnelere erişim
        binding = LayoutBankalarListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /*var bkmService = BKMService2()
        bkmService.getBankList(::onGetBankListFailed, ::onGetBankListSuccess)*/

//listeye banka logolarını ve isimlerini ekleme
        constlist.add(Bankalar("İŞ BANKASI",R.drawable.isbankasilogosu,"İŞ BANKASI"))
        constlist.add(Bankalar("AKBANK",R.drawable.akbanklogosu, "AKBANK"))
        constlist.add(Bankalar("GARANTİ",R.drawable.garantibankasilogosu,"GARANTİ"))
        constlist.add(Bankalar("ZİRAAT BANKASI",R.drawable.ziraatbankasilogosu, "ZİRAAT BANKASI"))
        constlist.add(Bankalar("DENİZBANK",R.drawable.denizbanklogosu,"DENİZBANK"))
        constlist.add(Bankalar("QNB FİNANSBANK", R.drawable.qnblogosu, "QNB FİNANSBANK"))
        constlist.add(Bankalar("KUVEYT TÜRK", R.drawable.kuveytlogosu, "KUVEYT TÜRK"))
        constlist.add(Bankalar("HALKBANK", R.drawable.halkbanklogosu, "HALKBANK"))
        constlist.add(Bankalar("PTT", R.drawable.pttlogosu, "PTT"))
        constlist.add(Bankalar("HSBC", R.drawable.hsbclogosu, "HSBC"))
        constlist.add(Bankalar("İNG", R.drawable.ingbanklogosu, "İNG BANK"))
        //listViewin verileri kullanabilmesi için adapter değişkeni
        adapter = BankalarAdapter(this,constlist)
        binding.listView.adapter = adapter

    }
    inner class BankalarAdapter(private val context: Context, private val constList: ArrayList<Bankalar>) : BaseAdapter() {

        val inflater: LayoutInflater = LayoutInflater.from(context)

        override fun getCount(): Int {
            return constList.size
        }

        override fun getItem(position: Int): Any {
            return constList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val binding: LayoutBankaKartBinding
            val convertView = convertView

            if (convertView == null) {
                binding = LayoutBankaKartBinding.inflate(inflater, parent, false)
                binding.root.tag = binding
            } else {
                binding = convertView.tag as LayoutBankaKartBinding
            }

            val banka = constList[position]

            // LayoutBankaKartBinding sınıfındaki view nesnelerine erişme
            binding.textViewKartAdi.text = banka.constBankaAdi
            binding.imageViewKartresim.setImageResource(banka.constLogo!!)
            binding.imageViewKartresim.tag = banka.constTag
            binding.imageViewKartresim.setOnClickListener{
                var secilenbanka  = binding.imageViewKartresim.getTag()

                if (secilenbanka == "İŞ BANKASI"){
                    var intent = Intent(context,Ara_isbank_activity::class.java)
                    startActivity(intent)
                    finish()
                }else if (secilenbanka == "KUVEYT TÜRK"){
                    var intent = Intent(context,Ara_kuveyt_activity::class.java)
                    startActivity(intent)
                    finish()
                }else if (secilenbanka == "HALKBANK"){
                    var intent = Intent(context,Ara_halkbank_activity::class.java)
                    startActivity(intent)
                    finish()
                }else if(secilenbanka == "QNB FİNANSBANK"){
                    var intent = Intent(context,Ara_finansbank_activity::class.java)
                    startActivity(intent)
                    finish()
                }else if (secilenbanka == "DENİZBANK"){
                    var intent = Intent(context, Ara_denizbank_activity::class.java)
                    startActivity(intent)
                    finish()
                }else if(secilenbanka == "ZİRAAT BANKASI"){
                    var intent = Intent(context, Ara_ziraat_activity::class.java)
                    startActivity(intent)
                    finish()
                }else if(secilenbanka == "GARANTİ"){
                    var intent = Intent(context, Ara_garanti_activity::class.java)
                    startActivity(intent)
                    finish()
                }else if(secilenbanka == "PTT"){
                    var intent = Intent(context, Ara_ptt_activity::class.java)
                    startActivity(intent)
                    finish()
                }else if(secilenbanka == "HSBC"){
                    var intent = Intent(context, Ara_hsbc_activity::class.java)
                    startActivity(intent)
                    finish()
                }else if(secilenbanka == "İNG BANK"){
                    var intent = Intent(context, Ara_ing_activity::class.java)
                    startActivity(intent)
                    finish()
                }else if(secilenbanka == "AKBANK"){
                    var intent = Intent(context, Ara_akbank_activity::class.java)
                    startActivity(intent)
                    finish()
                }

            }
            binding.textViewKartAdi.setOnClickListener{
                var secilenbanka : String = binding.textViewKartAdi.text.toString()
                if (secilenbanka == "İŞ BANKASI"){
                    var intent = Intent(context,Ara_isbank_activity::class.java)
                    startActivity(intent)
                    finish()
                }else if (secilenbanka == "KUVEYT TÜRK"){
                    var intent = Intent(context,Ara_kuveyt_activity::class.java)
                    startActivity(intent)
                    finish()
                }else if (secilenbanka == "HALK BANKASI"){
                    var intent = Intent(context,Ara_halkbank_activity::class.java)
                    startActivity(intent)
                    finish()
                }else if(secilenbanka == "QNB FİNANSBANK"){
                    var intent = Intent(context,Ara_finansbank_activity::class.java)
                    startActivity(intent)
                    finish()
                }else if (secilenbanka == "DENİZBANK"){
                    var intent  = Intent(context, Ara_denizbank_activity::class.java)
                    startActivity(intent)
                    finish()
                }else if(secilenbanka == "ZİRAAT BANKASI"){
                    var intent = Intent(context, Ara_ziraat_activity::class.java)
                    startActivity(intent)
                    finish()
                }else if(secilenbanka == "PTT"){
                    var intent = Intent(context, Ara_ptt_activity::class.java)
                    startActivity(intent)
                    finish()
                }else if(secilenbanka == "GARANTİ"){
                    var intent = Intent(context, Ara_garanti_activity::class.java)
                    startActivity(intent)
                    finish()
                }else if(secilenbanka == "İNG BANK"){
                    var intent = Intent(context, Ara_ing_activity::class.java)
                    startActivity(intent)
                    finish()
                }else if(secilenbanka == "HSBC"){
                    var intent = Intent(context, Ara_hsbc_activity::class.java)
                    startActivity(intent)
                    finish()
                }else if(secilenbanka == "AKBANK"){
                    var intent = Intent(context, Ara_akbank_activity::class.java)
                    startActivity(intent)
                    finish()
                }

            }
            return binding.root
        }
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        var secilenbanka : String = p0?.getItemAtPosition(p2) as String
        val intent = Intent(this@BankalarListesi_activity, Ara_isbank_activity::class.java)
        startActivity(intent)
        finish()
    }
}