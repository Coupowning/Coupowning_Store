package kr.ac.coukingmama.storeapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ExperimentalGetImage
import kr.ac.coukingmama.storeapp.before.LoginActivity
import kr.ac.coukingmama.storeapp.certified.InquireActivity
import kr.ac.coukingmama.storeapp.databinding.ActivityMainBinding

@ExperimentalGetImage
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var isCertified : Boolean? = null // 인증 여부

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        isCertified = true // 테스트용 (임시)
        when(isCertified!!) {
            true -> {
                val intent = Intent(this, InquireActivity::class.java) // 조회 페이지
                startActivity(intent)
                finish()
            }
            false -> {
                val intent = Intent(this, LoginActivity::class.java) // 로그인 페이지
                startActivity(intent)
                finish()
            }
        }
    }
}