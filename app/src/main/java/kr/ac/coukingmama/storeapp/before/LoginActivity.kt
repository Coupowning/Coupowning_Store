package kr.ac.coukingmama.storeapp.before

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import kr.ac.coukingmama.storeapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() { // 로그인 페이지

    lateinit var binding : ActivityLoginBinding
    private var storeId : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.kakaoLogin.setOnClickListener {
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this@LoginActivity)) {
                UserApiClient.instance.loginWithKakaoTalk(this@LoginActivity, callback = callback)
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this@LoginActivity, callback = callback)
            }
        }
    }
    private val callback : (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e("login","로그인 실패- $error")
        } else if (token != null) {
            UserApiClient.instance.me { user, error ->
                if (user != null) {
                    storeId = user.id.toString()
                    register(storeId!!)
                }
                if (error != null) {
                    Log.e("error", "사용자 정보 요청 실패", error)
                }
            }
            Log.d("login", "로그인성공")
        }
    }
    private fun register(storeId : String){
        val intent = Intent(application, RegisterActivity::class.java).putExtra("storeId", storeId)
        startActivity(intent)
        finish()
    }
}