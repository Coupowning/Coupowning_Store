package kr.ac.coukingmama.storeapp.before

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kr.ac.coukingmama.storeapp.databinding.ActivityCertificateBinding

class CertificateActivity : AppCompatActivity() { // 사업자 인증 페이지

    lateinit var binding : ActivityCertificateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCertificateBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}