package kr.ac.coukingmama.storeapp.before

import android.annotation.SuppressLint
import android.content.Context
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
    private var uriList : ArrayList<Uri> = arrayListOf()
    private var uri : Uri? = null
    private var num : Int = 1
    private var cnt : Int = 0
    private val GET_GALLERY_IMAGE : Int = 200
    private var strings : ArrayList<String>? = null
    private var longitude : Double? = null
    private var latitude : Double? = null
    private var storeId : String? = null

    @SuppressLint("UnsafeOptInUsageError")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listAdapter = ListItemAdapter(this)
        getExtra()
        if(storeId == null)
            loadData()

        if(intent.getStringArrayListExtra("inform") != null){
            binding.modifystore.text = "가게 수정"
            strings = intent.getStringArrayListExtra("inform")!!
            if(strings!!.lastIndex == 6){
                binding.etstorename.setText(strings!![0])
                binding.etaddress.setText(strings!![1])
                binding.phonenum.setText(strings!![2])
                binding.intro.setText(strings!![3])
                binding.stampsum.setText(strings!![4])
                binding.num.setText(strings!![5])
                binding.award.setText(strings!![6])
            }
            val storage = FirebaseStorage.getInstance()
            val storageRef = storage.reference
            val imageStorageRef = storageRef.child(storeId!!)

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
                if (binding.intro.text.toString().trim().isNotEmpty() && binding.stampsum.text.toString().trim().isNotEmpty() && binding.num.text.toString().trim().isNotEmpty() && binding.award.text.toString().trim().isNotEmpty() &&
                    binding.phonenum.text.toString().trim().isNotEmpty() && binding.etstorename.text.toString().trim().isNotEmpty() && binding.etaddress.text.toString().trim().isNotEmpty()
                ) {
                    val images = arrayListOf<String>()
                    for(i in 1..listAdapter.getSize()){
                        images.add("https://firebasestorage.googleapis.com/v0/b/coupowning.appspot.com/o/${storeId}%2Fstore${i}.jpg?alt=media")
                    }
                    val storeInfo = Store(
                        binding.etstorename.text.toString(),
                        StoreLocation(binding.etaddress.text.toString(), "$latitude", "$longitude"),
                        binding.phonenum.text.toString(),
                        binding.intro.text.toString() + "/" +  binding.stampsum.text.toString().toInt() + "/" + binding.num.text.toString() + "/" + binding.award.text.toString(),
                        storeId!!, images)
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
                    var flag = true
                    uriList.forEach {
                        val fileName = "store${num++}.jpg"
                        val imageStorageRef = storageRef.child(storeInfo.storeId).child(fileName)
                        val uploadTask = imageStorageRef.putFile(it)
                        uploadTask.addOnFailureListener {
                            it.printStackTrace()
                            flag = false
                        }.addOnSuccessListener {

                        }.addOnCanceledListener {
                            Toast.makeText(this, "<오류>", Toast.LENGTH_SHORT).show()
                        }
                    }
                    if(flag){
                        Toast.makeText(this, "가게가 등록/수정 되었습니다!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java).putExtra("registered", true).putExtra("storeId", storeInfo.storeId)
                        Thread.sleep(3000L)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
        binding.search.setOnClickListener{
            val intent = Intent(this, AddressActivity::class.java)
            startActivityForResult(intent,0)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data!!.data != null){
            uri = data.data
            uriList.add(uri!!)
            listAdapter.setListData(ImageDTO(uri!!))
            cnt++
        }
        else if (requestCode == 0){
            latitude = data?.getDoubleExtra("x", -1.0)
            longitude = data?.getDoubleExtra("y", -1.0)
        }
    }
    private fun loadData(){
        val pref = applicationContext.getSharedPreferences("storeId", Context.MODE_PRIVATE)
        storeId = pref.getString("storeId", "")
    }
    private fun saveData(storeId : String){
        val pref = applicationContext.getSharedPreferences("storeId", Context.MODE_PRIVATE)
        pref.edit().putString("storeId", storeId).apply()
    }
    private fun getExtra(){
        storeId = intent.getStringExtra("storeId")
        if(storeId != null) {
            saveData(storeId!!)
        }
    }
}