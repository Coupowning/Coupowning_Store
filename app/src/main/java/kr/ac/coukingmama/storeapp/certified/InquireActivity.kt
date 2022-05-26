package kr.ac.coukingmama.storeapp.certified

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ExperimentalGetImage
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.storage.FirebaseStorage
import kr.ac.coukingmama.storeapp.before.RegisterActivity
import kr.ac.coukingmama.storeapp.databinding.ActivityInquireBinding
import kr.ac.coukingmama.storeapp.recyclerview.ImageDTO
import kr.ac.coukingmama.storeapp.recyclerview.ListItemAdapter

@ExperimentalGetImage
class InquireActivity : AppCompatActivity() { // 조회 페이지

    lateinit var binding: ActivityInquireBinding
    private lateinit var listAdapter: ListItemAdapter
    private var num : Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInquireBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listAdapter = ListItemAdapter(this)
        binding.listItem.adapter = listAdapter
        binding.listItem.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference

        binding.modifystore.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java) // 가게 등록 페이지
            startActivity(intent)
        }
        binding.camerabutton.setOnClickListener {
            val intent = Intent(this, QRActivity::class.java) // QR 인식 페이지
            startActivity(intent)
        }
        binding.settingimage.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java) // 가게 수정 페이지
            startActivity(intent)
        }
        val imageStorageRef = storageRef.child("image")

        imageStorageRef.listAll().addOnSuccessListener{
            it.items.forEach{
                it.downloadUrl.addOnSuccessListener{
                    var image : ImageDTO = ImageDTO()
                    image.uri = it
                    listAdapter.setListData(image)
                }
            }
        }.addOnCanceledListener {

        }.addOnFailureListener {

        }
    }
    private fun uriToBitmap(uri : Uri) : Bitmap {
        var bitmap : Bitmap? = null
        var u = Uri.parse(uri.toString())
        try {
            uri.let {
                if(Build.VERSION.SDK_INT < 28) {
                    bitmap = MediaStore.Images.Media.getBitmap(
                        contentResolver,
                        u
                    )
                    listAdapter.setListData(bitmap!!)
                } else {
                    val source = ImageDecoder.createSource(contentResolver, u)
                    bitmap = ImageDecoder.decodeBitmap(source)
                    listAdapter.setListData(bitmap!!)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bitmap!!
    }
}
