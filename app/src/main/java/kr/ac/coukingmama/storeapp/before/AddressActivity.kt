package kr.ac.coukingmama.storeapp.before

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kr.ac.coukingmama.storeapp.databinding.ActivityAddressBinding


class AddressActivity : AppCompatActivity() { // 주소 검색 페이지

    lateinit var binding : ActivityAddressBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}