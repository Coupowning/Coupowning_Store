package kr.ac.coukingmama.storeapp.before

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kr.ac.coukingmama.storeapp.certified.SettingActivity
import kr.ac.coukingmama.storeapp.database.Store
import kr.ac.coukingmama.storeapp.database.StoreLocation
import kr.ac.coukingmama.storeapp.database.StoreService
import kr.ac.coukingmama.storeapp.databinding.ActivityRegisterBinding
import kr.ac.coukingmama.storeapp.recyclerview.ListItemAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterActivity : AppCompatActivity() { // 가게 등록 페이지

    lateinit var binding : ActivityRegisterBinding
    private lateinit var listAdapter: ListItemAdapter
    private var uriList : ArrayList<Uri> = arrayListOf<Uri>()
    private var uri : Uri? = null
    private var num : Int = 1
    private val GET_GALLERY_IMAGE : Int = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listAdapter = ListItemAdapter(this)
        binding.listItem.adapter = listAdapter
        binding.listItem.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        binding.settingimage.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java) // 설정 페이지
            startActivity(intent)
        }
        binding.addimage.setOnClickListener {
            val intent: Intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            startActivityForResult(intent, GET_GALLERY_IMAGE) // uri 에 사진 대입해야함
        }
        binding.modifystore.setOnClickListener {
            var storeInfo: Store? = null
            if (binding.intro.text.toString().trim().isNotEmpty() && binding.stampsum.text.toString().trim().isNotEmpty() && binding.num.text.toString().trim().isNotEmpty() && binding.award.text.toString().trim().isNotEmpty() &&
                binding.phonenum.text.toString().trim().isNotEmpty() && binding.etstorename.text.toString().trim().isNotEmpty()
            ) {
//                storeInfo = Store(
//                    binding.etstorename.text.toString(),
//                    StoreLocation("경기도", "2,1", "3.2"),
//                    binding.phonenum.text.toString(),
//                    binding.intro.text.toString() + "/" +  binding.stampsum.text.toString().toInt() + "/" + binding.award.text.toString(),
//                    "ohksj77@naver.com", arrayListOf("www.a.a", "www.b.b")
                var map : MutableMap<String, String> = mutableMapOf()
                map["1"] = "www.minwoong.com"
                storeInfo = Store(
                    "라떼는말이야",
                    StoreLocation("경기도", "2,1", "3.2"),
                    "01012345678",
                    "민웅이를 드립니다.",
                    "ohksj77@naver.com", map
                )
                val gson : Gson = GsonBuilder().setLenient().create()
                val retrofit = Retrofit.Builder().baseUrl("https://us-central1-coupowning.cloudfunctions.net/widgets/").addConverterFactory(GsonConverterFactory.create(gson)).build()
                val api = retrofit.create(StoreService::class.java)
                val callPost = api.postStore(storeInfo).enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        val string = response.body()
                        Log.d("response", " 전송> $string")
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Log.d("error", "에러>" + t.message.toString())
                    }
                })
                val storage = FirebaseStorage.getInstance()
                val storageRef = storage.reference
                val fileName = "store${num++}.jpg"
                val imageStorageRef = storageRef.child("image").child(fileName)
                uriList.forEach {
                    val uploadTask = imageStorageRef.putFile(it)
                    uploadTask.addOnFailureListener {
                        it.printStackTrace()
                    }.addOnSuccessListener {
                        Toast.makeText(this, "가게가 등록/수정 되었습니다!", Toast.LENGTH_SHORT).show()
                        finish()
                    }.addOnCanceledListener {
                        Toast.makeText(this, "<취소>", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data!!.data != null){
            uri = data.data
            uriList.add(uri!!)
            try {
                uri?.let {
                    if(Build.VERSION.SDK_INT < 28) {
                        val bitmap = MediaStore.Images.Media.getBitmap(
                            contentResolver,
                            uri
                        )
                        listAdapter.setListData(bitmap)
                    } else {
                        val source = ImageDecoder.createSource(contentResolver, uri!!)
                        val bitmap = ImageDecoder.decodeBitmap(source)
                        listAdapter.setListData(bitmap)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun uriToBitmap(uri : Uri) : Bitmap {
        var bitmap : Bitmap? = null
        try {
            uri.let {
                if(Build.VERSION.SDK_INT < 28) {
                    bitmap = MediaStore.Images.Media.getBitmap(
                        contentResolver,
                        uri
                    )
                    listAdapter.setListData(bitmap!!)
                } else {
                    val source = ImageDecoder.createSource(contentResolver, uri!!)
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