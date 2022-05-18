package kr.ac.coukingmama.storeapp.certified

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.ac.coukingmama.storeapp.before.RegisterActivity
import kr.ac.coukingmama.storeapp.databinding.ActivityInquireBinding

class InquireActivity : AppCompatActivity() { // 조회 페이지

    lateinit var binding : ActivityInquireBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInquireBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var recyclerView : RecyclerView = binding.storeimage
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.modifystore.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java) // 가게 등록 페이지
            startActivity(intent)
        }
        binding.camerabutton.setOnClickListener{
            val intent = Intent(this, QRActivity::class.java) // 가게 등록 페이지
            startActivity(intent)
        }
    }
}