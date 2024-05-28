package ali.mert.atmmnerede

import ali.mert.atmmnerede.databinding.LayoutBankasecBinding
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity


class BankaSec_activity : ComponentActivity() {
    lateinit var binding: LayoutBankasecBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LayoutBankasecBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding


//        if (response.isSuccessful) {
//            val responseString = response.body?.string()
//            val jsonArray = JSONArray(responseString)
//            val bankList = ArrayList<String>()
//
//            for (i in 0 until jsonArray.length()) {
//                val jsonObject = jsonArray.getJSONObject(i)
//                val bankName = jsonObject.getString("name")
//                bankList.add(bankName)
//            }
//            val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, bankList)
//            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
//            binding.spinnerBankasec.adapter = adapter
//        }else{
//            println("Hata: ${response.code}")
//        }


        binding.buttonBankasecBankalistele.setOnClickListener(){

            val intent = Intent(this@BankaSec_activity, BankalarListesi_activity::class.java)
            startActivity(intent)
            finish()

        }
    }
}