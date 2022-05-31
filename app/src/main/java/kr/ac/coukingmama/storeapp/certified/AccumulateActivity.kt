package kr.ac.coukingmama.storeapp.certified

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ExperimentalGetImage
import kr.ac.coukingmama.storeapp.database.Coupon
import kr.ac.coukingmama.storeapp.database.StoreService
import kr.ac.coukingmama.storeapp.databinding.ActivityAccumulateBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@ExperimentalGetImage
class AccumulateActivity : AppCompatActivity() { // 적립 페이지

    lateinit var binding : ActivityAccumulateBinding
    private var userId : String? = null
    private lateinit var max : String

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccumulateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        max = intent.getStringExtra("max")!!
        userId = intent.getStringExtra("userId")

        binding.back.setOnClickListener{
            val intent = Intent(this, InquireActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.minus.setOnClickListener{
            if(binding.numofstamp.text.toString().toInt() > 1){
                binding.numofstamp.text = (binding.numofstamp.text.toString().toInt() - 1).toString()
            }
        }

        binding.plus.setOnClickListener{
            if(binding.numofstamp.text.toString().toInt() < max.toInt()){
                binding.numofstamp.text = (binding.numofstamp.text.toString().toInt() + 1).toString()
            }
        }

        binding.finish.setOnClickListener{
            var str = "적립"
            if(binding.rdminus.isChecked){
                str = "차감"
            }
            val storeId = "kevinkim3"
            var coupon : Coupon? = null
            if(binding.rdminus.isChecked)
                coupon = Coupon(storeId, "-" + binding.numofstamp.text.toString())
            else if(binding.rdplus.isChecked)
                coupon =  Coupon(storeId, binding.numofstamp.text.toString())
            val api = StoreService.create()
            val callPost = api.addCoupon(userId!!, coupon!!).enqueue(object : Callback<Coupon> {
                override fun onResponse(call: Call<Coupon>, response: Response<Coupon>) {
                    if(response.isSuccessful)
                        Log.d("response", " HTTP Status Code > ${response.code()} \n ${response.body()}")
                    else
                        Log.d("response", " HTTP Status Code > ${response.code()}")
                }
                override fun onFailure(call: Call<Coupon>, t: Throwable) {
                    Log.d("error", "ERROR message > " + t.message.toString())
                }
            })
            val alert : AlertDialog = AlertDialog.Builder(this)
                .setTitle("도장이 ${str}되었습니다!")
                .setMessage("\n꾸준한 쿠포닝으로 단골 손님을 모아봅시다 :)")
                .setPositiveButton(">") { dialog, _ -> dialog.dismiss()
                    val intent = Intent(this, InquireActivity::class.java)
                    startActivity(intent)
                    finishActivity(0)
                }.create()
            alert.setOnShowListener {
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
            }
            alert.show()
        }
    }
}