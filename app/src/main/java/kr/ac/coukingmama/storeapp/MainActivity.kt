package kr.ac.coukingmama.storeapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ExperimentalGetImage
import com.kakao.sdk.user.UserApiClient
import kr.ac.coukingmama.storeapp.before.LoginActivity
import kr.ac.coukingmama.storeapp.before.RegisterActivity
import kr.ac.coukingmama.storeapp.certified.InquireActivity
import kr.ac.coukingmama.storeapp.databinding.ActivityMainBinding

@ExperimentalGetImage
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var isRegistered: Boolean = false
    private var storeId : String? = null

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        storeId = intent.getStringExtra("storeId")
        isRegistered = intent.getBooleanExtra("registered", false)
        if(!isRegistered) {
            loadData()
        }
        isToken()
    }
    private fun isToken(){
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) { // 캐시에 토큰이 존재하지 않은 경우
                Log.e("login", "토큰 정보 보기 실패", error)
                saveData(false)
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            else if (tokenInfo != null) { // 캐시에 토큰이 존재하는 경우
                Log.i("login", "토큰 정보 보기 성공" +
                        "\n회원번호: ${tokenInfo.id}" +
                        "\n만료시간: ${tokenInfo.expiresIn} 초")
                saveData(isRegistered)
                if(isRegistered){
                    val intent = Intent(this, InquireActivity::class.java)
                    if(storeId != null){
                        intent.putExtra("storeId", storeId)
                    }
                    startActivity(intent)
                    finish()
                }
                else{
                    val intent = Intent(this, RegisterActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
    private fun loadData(){
        val pref = applicationContext.getSharedPreferences("isRegistered", Context.MODE_PRIVATE)
        isRegistered = pref.getBoolean("isRegistered", false)
    }
    private fun saveData(flag : Boolean){
        val pref = applicationContext.getSharedPreferences("isRegistered", Context.MODE_PRIVATE)
        pref.edit().putBoolean("isRegistered", flag).apply()
    }
}