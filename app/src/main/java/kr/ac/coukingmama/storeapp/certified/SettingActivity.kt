package kr.ac.coukingmama.storeapp.certified

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kr.ac.coukingmama.storeapp.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() { // 설정 페이지

    lateinit var binding : ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener{
            finish()
        }
    }
}