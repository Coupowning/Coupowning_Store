package kr.ac.coukingmama.storeapp.certified

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ExperimentalGetImage
import kotlinx.android.synthetic.main.activity_accumulate.*
import kr.ac.coukingmama.storeapp.databinding.ActivityAccumulateBinding

@ExperimentalGetImage
class AccumulateActivity : AppCompatActivity() { // 적립 페이지

    lateinit var binding : ActivityAccumulateBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccumulateBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            binding.numofstamp.text = (binding.numofstamp.text.toString().toInt() + 1).toString()
        }

        binding.finish.setOnClickListener{
            var str = "적립"
            if(rdminus.isChecked){
                str = "차감"
            }
            AlertDialog.Builder(this)
                .setTitle("도장이 ${str}되었습니다!")
                .setMessage("꾸준한 쿠포닝으로 단골 손님을 모아봅시다 :)")
                .setPositiveButton(">") { dialog, _ -> dialog.dismiss()
                    val intent = Intent(this, InquireActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                .show()
        }
    }
}