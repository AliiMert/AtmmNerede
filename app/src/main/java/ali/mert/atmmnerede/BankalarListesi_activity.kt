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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//tasarım ekranındaki nesnelere erişim
        binding = LayoutBankalarListBinding.inflate(layoutInflater)
        setContentView(binding.root)

//listeye banka logolarını ve isimlerini ekleme
        constlist.add(Bankalar("İş Bankası",R.drawable.isbankasilogosu,"İş Bankası"))
        constlist.add(Bankalar("Akbank",R.drawable.akbanklogosu, "Akbank"))
        constlist.add(Bankalar("Garanti",R.drawable.garantibankasilogosu,"Garanti"))
        constlist.add(Bankalar("Ziraat Bankası",R.drawable.ziraatbankasilogosu, "Ziraat Bankası"))
        constlist.add(Bankalar("Denizbank",R.drawable.denizbanklogosu,"Denizbank"))
        constlist.add(Bankalar("QNB Finansbank", R.drawable.qnblogosu, "QNB Finansbank"))
        constlist.add(Bankalar("KuveytTürk", R.drawable.kuveytlogosu, "KuveytTürk"))
        constlist.add(Bankalar("Halkbank", R.drawable.halkbanklogosu, "Halkbank"))

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

                if (secilenbanka == "İş Bankası"){
                    var intent = Intent(context,Ara_isbank_activity::class.java)
                    startActivity(intent)
                    finish()
                }else if (secilenbanka == "KuveytTürk"){
                    var intent = Intent(context,Ara_kuveyt_activity::class.java)
                    startActivity(intent)
                    finish()
                }else if (secilenbanka == "Halkbank"){
                    var intent = Intent(context,Ara_halkbank_activity::class.java)
                    startActivity(intent)
                    finish()
                }else if(secilenbanka == "QNB Finansbank"){
                    var intent = Intent(context,Ara_finansbank_activity::class.java)
                    startActivity(intent)
                    finish()
                }else if (secilenbanka == "Denizbank"){
                    var intent = Intent(context, Ara_denizbank_activity::class.java)
                    startActivity(intent)
                    finish()
                }else if(secilenbanka == "Ziraat Bankası"){
                    var intent = Intent(context, Ara_ziraat_activity::class.java)
                    startActivity(intent)
                    finish()
                }

            }
            binding.textViewKartAdi.setOnClickListener{
                var secilenbanka : String = binding.textViewKartAdi.text.toString()
                if (secilenbanka == "İş Bankası"){
                    var intent = Intent(context,Ara_isbank_activity::class.java)
                    startActivity(intent)
                    finish()
                }else if (secilenbanka == "KuveytTürk"){
                    var intent = Intent(context,Ara_kuveyt_activity::class.java)
                    startActivity(intent)
                    finish()
                }else if (secilenbanka == "Halkbank"){
                    var intent = Intent(context,Ara_halkbank_activity::class.java)
                    startActivity(intent)
                    finish()
                }else if(secilenbanka == "QNB Finansbank"){
                    var intent = Intent(context,Ara_finansbank_activity::class.java)
                    startActivity(intent)
                    finish()
                }else if (secilenbanka == "Denizbank"){
                    var intent  = Intent(context, Ara_denizbank_activity::class.java)
                    startActivity(intent)
                    finish()
                }else if(secilenbanka == "Ziraat Bankası"){
                    var intent = Intent(context, Ara_ziraat_activity::class.java)
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