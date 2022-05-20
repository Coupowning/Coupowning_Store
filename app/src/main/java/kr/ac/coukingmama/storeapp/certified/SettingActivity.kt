package kr.ac.coukingmama.storeapp.certified

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kr.ac.coukingmama.storeapp.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    lateinit var binding : ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}