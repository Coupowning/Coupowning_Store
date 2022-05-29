package kr.ac.coukingmama.storeapp.before

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kr.ac.coukingmama.storeapp.certified.SettingActivity
import kr.ac.coukingmama.storeapp.databinding.ActivityCertificateBinding

class CertificateActivity : AppCompatActivity() { // 사업자 인증 페이지

    lateinit var binding : ActivityCertificateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCertificateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.settingimage.setOnClickListener{
            val intent = Intent(this, SettingActivity::class.java) // 설정 페이지
            startActivity(intent)
        }
        binding.certificate.setOnClickListener{
            if(binding.name.text.isNotEmpty() && binding.opdate.text.isNotEmpty() && binding.reginum.text.isNotEmpty()){
                val intent = Intent(this, RegisterActivity::class.java) // 가게 등록 페이지
                startActivity(intent)
                finish()
            }
        }
    }
}