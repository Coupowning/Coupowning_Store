package kr.ac.coukingmama.storeapp.certified

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ExperimentalGetImage
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.storage.FirebaseStorage
import kr.ac.coukingmama.storeapp.before.RegisterActivity
import kr.ac.coukingmama.storeapp.database.Store
import kr.ac.coukingmama.storeapp.database.StoreService
import kr.ac.coukingmama.storeapp.databinding.ActivityInquireBinding
import kr.ac.coukingmama.storeapp.recyclerview.ImageDTO
import kr.ac.coukingmama.storeapp.recyclerview.ListItemAdapter
import kr.ac.coukingmama.storeapp.recyclerview.RecyclerviewDecoration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@ExperimentalGetImage
class InquireActivity : AppCompatActivity() { // 조회 페이지

    lateinit var binding: ActivityInquireBinding
    private lateinit var listAdapter: ListItemAdapter
    private var num : Int = 1
    var strings : ArrayList<String> = arrayListOf()
    var list: MutableList<ImageDTO> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInquireBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listAdapter = ListItemAdapter(this)
        binding.listItem.adapter = listAdapter
        binding.listItem.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.listItem.addItemDecoration(RecyclerviewDecoration(30))
        binding.modifystore.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java) // 가게 등록 페이지
            intent.putExtra("inform", strings)
            startActivity(intent)
        }
        binding.camerabutton.setOnClickListener {
            val intent = Intent(this, QRActivity::class.java).putExtra("max", binding.stampsum.text) // QR 인식 페이지
            startActivity(intent)
        }
        binding.settingimage.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java) // 가게 수정 페이지
            startActivity(intent)
        }
        val storeId : String = "cafe302" // 이부분은 수정해야 함
        val api = StoreService.create()
        val callGet = api.getStore(storeId).enqueue(object : Callback<Store> {
            override fun onResponse(call: Call<Store>, response: Response<Store>) {
                if(response.isSuccessful){
                    Log.d("response", " HTTP Status Code > ${response.code()} \n ${response.body()}")
                    binding.storename.text = response.body()!!.storeName
                    binding.addr.text = response.body()!!.storeLocation.locationKr
                    binding.phone.text = response.body()!!.storePhone
                    val str = response.body()!!.storeDesc.split("/")
                    binding.intro.text = str[0]
                    binding.stampsum.text = str[1]
                    binding.num.text = str[2]
                    binding.award.text = str[3]
                    strings.add(response.body()!!.storeName)
                    strings.add(response.body()!!.storeLocation.locationKr)
                    strings.add(response.body()!!.storePhone)
                    strings.add(str[0])
                    strings.add(str[1])
                    strings.add(str[2])
                    strings.add(str[3])
                }
                else
                    Log.d("response", " HTTP Status Code > ${response.code()}")
            }
            override fun onFailure(call: Call<Store>, t: Throwable) {
                Log.d("error", "ERROR message>" + t.message.toString())
            }
        })
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference
        val imageStorageRef = storageRef.child("image")

        imageStorageRef.listAll().addOnSuccessListener{
            it.items.forEach{
                it.downloadUrl.addOnSuccessListener{
                    listAdapter.setListData(ImageDTO(it))
                }
            }
        }.addOnCanceledListener {

        }.addOnFailureListener {

        }
    }
}
