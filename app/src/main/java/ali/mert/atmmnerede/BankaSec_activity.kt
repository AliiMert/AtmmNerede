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


        binding.buttonBankasecBankalistele.setOnClickListener(){

            val intent = Intent(this@BankaSec_activity, BankalarListesi_activity::class.java)
            startActivity(intent)
            finish()

        }
    }
}