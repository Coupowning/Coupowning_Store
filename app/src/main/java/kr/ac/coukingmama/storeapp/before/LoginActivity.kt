package kr.ac.coukingmama.storeapp.before

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.kakao.util.helper.Utility
import kr.ac.coukingmama.storeapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() { // 로그인 페이지

    lateinit var binding : ActivityLoginBinding
    lateinit var storeId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val keyHash = Utility.getKeyHash(this)
        Log.d("hash", keyHash)

        binding.kakaoLogin.setOnClickListener{
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
//                storeId = user!!.id.toString()
            }
            Log.d("login","로그인성공")
            startActivity(Intent(application, RegisterActivity::class.java));
            finish()
        }
    }
}