package kr.ac.coukingmama.storeapp.before

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.storage.FirebaseStorage
import kr.ac.coukingmama.storeapp.MainActivity
import kr.ac.coukingmama.storeapp.certified.SettingActivity
import kr.ac.coukingmama.storeapp.database.Store
import kr.ac.coukingmama.storeapp.database.StoreLocation
import kr.ac.coukingmama.storeapp.database.StoreService
import kr.ac.coukingmama.storeapp.databinding.ActivityRegisterBinding
import kr.ac.coukingmama.storeapp.recyclerview.ImageDTO
import kr.ac.coukingmama.storeapp.recyclerview.ListItemAdapter
import kr.ac.coukingmama.storeapp.recyclerview.RecyclerviewDecoration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() { // 가게 등록 페이지

    lateinit var binding : ActivityRegisterBinding
    private lateinit var listAdapter: ListItemAdapter
    private var uriList : ArrayList<Uri> = arrayListOf<Uri>()
    private var uri : Uri? = null
    private var num : Int = 1
    private val GET_GALLERY_IMAGE : Int = 200
    private var strings : ArrayList<String>? = null
    private var longitude : Double? = null
    private var latitude : Double? = null

    @SuppressLint("UnsafeOptInUsageError")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listAdapter = ListItemAdapter(this)
        if(intent.getStringArrayListExtra("inform") != null){
            binding.modifystore.text = "가게 수정"
            strings = intent.getStringArrayListExtra("inform")!!
            binding.etstorename.setText(strings!![0])
            binding.etaddress.setText(strings!![1])
            binding.phonenum.setText(strings!![2])
            binding.intro.setText(strings!![3])
            binding.stampsum.setText(strings!![4])
            binding.num.setText(strings!![5])
            binding.award.setText(strings!![6])
            val storage = FirebaseStorage.getInstance()
            val storageRef = storage.reference
            val imageStorageRef = storageRef.child("image")

            imageStorageRef.listAll().addOnSuccessListener{
                it.items.forEach{
                    it.downloadUrl.addOnSuccessListener{
                        listAdapter.setListData(ImageDTO(it))
                        num++;
                    }
                }
            }.addOnCanceledListener {

            }.addOnFailureListener {

            }
        } else {
            binding.modifystore.text = "가게 등록"
        }

        latitude = intent.getDoubleExtra("x", -1.0)
        longitude = intent.getDoubleExtra("y", -1.0)

        binding.listItem.adapter = listAdapter
        binding.listItem.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.listItem.addItemDecoration(RecyclerviewDecoration(30))
        binding.settingimage.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java) // 설정 페이지
            startActivity(intent)
        }
        binding.addimage.setOnClickListener {
            val intent: Intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            startActivityForResult(intent, GET_GALLERY_IMAGE)
        }
        binding.modifystore.setOnClickListener {
            if(latitude == -1.0 && longitude == -1.0){
                Toast.makeText(applicationContext, "지도에 가게를 표시해주세요.", Toast.LENGTH_SHORT).show()
            }
            else{
                var storeInfo: Store? = null
                if (binding.intro.text.toString().trim().isNotEmpty() && binding.stampsum.text.toString().trim().isNotEmpty() && binding.num.text.toString().trim().isNotEmpty() && binding.award.text.toString().trim().isNotEmpty() &&
                    binding.phonenum.text.toString().trim().isNotEmpty() && binding.etstorename.text.toString().trim().isNotEmpty()
                ) {
                    val images = hashMapOf<Int, String>() // 이미지 정보는 추후에 DB에서 뺄 것
                    images[1] = "www.a.b"
                    storeInfo = Store(
                        binding.etstorename.text.toString(),
                        StoreLocation("경기도 시흥시 산기대학로...", "$latitude", "$longitude"),
                        binding.phonenum.text.toString(),
                        binding.intro.text.toString() + "/" +  binding.stampsum.text.toString().toInt() + "/" + binding.num.text.toString() + "/" + binding.award.text.toString(),
                        "cafe302", // id 변경해줄 것
                        images)

                    val api = StoreService.create()
                    val callPost = api.postStore(storeInfo).enqueue(object : Callback<Store> {
                        override fun onResponse(call: Call<Store>, response: Response<Store>) {
                            if(response.isSuccessful)
                                Log.d("response", " HTTP Status Code > ${response.code()} \n ${response.body()}")
                            else
                                Log.d("response", " HTTP Status Code > ${response.code()}")
                        }
                        override fun onFailure(call: Call<Store>, t: Throwable) {
                            Log.d("error", "ERROR message > " + t.message.toString())
                        }
                    })
                    val storage = FirebaseStorage.getInstance()
                    val storageRef = storage.reference
                    val fileName = "store${num++}.jpg"
                    val imageStorageRef = storageRef.child("image").child(fileName) // "image" 변경하기
                    uriList.forEach {
                        val uploadTask = imageStorageRef.putFile(it)
                        uploadTask.addOnFailureListener {
                            it.printStackTrace()
                        }.addOnSuccessListener {
                            Toast.makeText(this, "가게가 등록/수정 되었습니다!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, MainActivity::class.java).putExtra("registered", true)
                            startActivity(intent)
                            finish()
                        }.addOnCanceledListener {
                            Toast.makeText(this, "<오류>", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
        binding.search.setOnClickListener{
            val intent = Intent(this, AddressActivity::class.java)
            if(strings != null) {
                strings!![0] = binding.etstorename.text.toString()
                strings!![1] = binding.etaddress.text.toString()
                strings!![2] = binding.phonenum.text.toString()
                strings!![3] = binding.intro.text.toString()
                strings!![4] = binding.stampsum.text.toString()
                strings!![5] = binding.num.text.toString()
                strings!![6] = binding.award.text.toString()
                intent.putExtra("inform", strings)
            }
            startActivity(intent)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data!!.data != null){
            uri = data.data
            uriList.add(uri!!)
            listAdapter.setListData(ImageDTO(uri!!))
        }
    }
}