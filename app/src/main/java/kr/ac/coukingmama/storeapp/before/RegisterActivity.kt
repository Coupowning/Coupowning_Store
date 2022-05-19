package kr.ac.coukingmama.storeapp.before

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kr.ac.coukingmama.storeapp.databinding.ActivityRegisterBinding
import kr.ac.coukingmama.storeapp.recyclerview.ListItemAdapter

class RegisterActivity : AppCompatActivity() { // 가게 등록 페이지

    lateinit var binding : ActivityRegisterBinding
    private lateinit var listAdapter: ListItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listAdapter = ListItemAdapter(this)
        binding.storeimage.adapter = listAdapter
        binding.storeimage.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

    }
}