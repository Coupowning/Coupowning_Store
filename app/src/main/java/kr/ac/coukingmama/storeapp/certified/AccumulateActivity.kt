package kr.ac.coukingmama.storeapp.certified

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kr.ac.coukingmama.storeapp.databinding.ActivityAccumulateBinding

class AccumulateActivity : AppCompatActivity() {

    lateinit var binding : ActivityAccumulateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccumulateBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}