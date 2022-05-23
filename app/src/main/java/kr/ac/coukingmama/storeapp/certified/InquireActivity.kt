package kr.ac.coukingmama.storeapp.certified

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ExperimentalGetImage
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kr.ac.coukingmama.storeapp.before.RegisterActivity
import kr.ac.coukingmama.storeapp.database.ListViewModel
import kr.ac.coukingmama.storeapp.databinding.ActivityInquireBinding
import kr.ac.coukingmama.storeapp.recyclerview.ListItemAdapter

@ExperimentalGetImage
class InquireActivity : AppCompatActivity() { // 조회 페이지

    lateinit var binding : ActivityInquireBinding
    private lateinit var listAdapter: ListItemAdapter
    private val viewModel by lazy { ViewModelProvider(this).get(ListViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInquireBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listAdapter = ListItemAdapter(this)
        binding.storeimage.adapter = listAdapter
        binding.storeimage.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        binding.settingimage.setOnClickListener{
            val intent = Intent(this, SettingActivity::class.java) // 가게 수정 페이지
            startActivity(intent)
        }

        binding.modifystore.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java) // 가게 등록 페이지
            startActivity(intent)
        }
        binding.camerabutton.setOnClickListener{
            val intent = Intent(this, QRActivity::class.java) // QR 인식 페이지
            startActivity(intent)
        }
    }

    fun observeData(){
        viewModel.fetchData().observe(this, Observer{
            listAdapter.setListData(it)
            listAdapter.notifyDataSetChanged()
        })
    }
}