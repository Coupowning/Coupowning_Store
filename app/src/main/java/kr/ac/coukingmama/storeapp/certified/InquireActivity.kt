package kr.ac.coukingmama.storeapp.certified

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ExperimentalGetImage
import androidx.recyclerview.widget.LinearLayoutManager
import kr.ac.coukingmama.storeapp.before.RegisterActivity
import kr.ac.coukingmama.storeapp.databinding.ActivityInquireBinding
import kr.ac.coukingmama.storeapp.recyclerview.ListItemAdapter

@ExperimentalGetImage
class InquireActivity : AppCompatActivity() { // 조회 페이지

    lateinit var binding: ActivityInquireBinding
    private lateinit var listAdapter: ListItemAdapter
    private var num : Int = 1
    private var uri : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInquireBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listAdapter = ListItemAdapter(this)
        binding.listItem.adapter = listAdapter
        binding.listItem.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        binding.settingimage.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java) // 가게 수정 페이지
            startActivity(intent)
        }

        binding.modifystore.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java) // 가게 등록 페이지
            startActivity(intent)
        }
        binding.camerabutton.setOnClickListener {
            val intent = Intent(this, QRActivity::class.java) // QR 인식 페이지
            startActivity(intent)
        }
//        observeData()
//        downloadImg(num++)
    }

//    private fun observeData() {
//        listAdapter.notifyDataSetChanged()
//    }
//    fun getFireBaseImage(num: Int) {
//        val file: File =
//            applicationContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES + "/store_img")!!
//        if (!file.isDirectory) {
//            file.mkdir()
//        }
//        downloadImg(num)
//    }
//    private fun downloadImg(num : Int) {
//        val storage: FirebaseStorage = FirebaseStorage.getInstance()
//        val storageRef: StorageReference = storage.reference
//        storageRef.child("store_img/store$num.jpg").downloadUrl
//            .addOnSuccessListener {
//                Glide.with(applicationContext).load(it).into(binding.storeimage.img)
//                uri = it
//            }
//    }
}
