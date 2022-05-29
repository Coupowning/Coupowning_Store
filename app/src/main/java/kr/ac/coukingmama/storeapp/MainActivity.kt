package kr.ac.coukingmama.storeapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ExperimentalGetImage
import com.kakao.sdk.user.UserApiClient
import kr.ac.coukingmama.storeapp.before.CertificateActivity
import kr.ac.coukingmama.storeapp.before.LoginActivity
import kr.ac.coukingmama.storeapp.databinding.ActivityMainBinding

@ExperimentalGetImage
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        isToken()
    }
    private fun isToken(){
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) { // 캐시에 토큰이 존재하지 않으면 로그인 페이지로 이동
                Log.e("login", "토큰 정보 보기 실패", error)
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
            }
            else if (tokenInfo != null) { // 캐시에 토큰이 존재하면 메인 페이지로 이동
                Log.i("login", "토큰 정보 보기 성공" +
                        "\n회원번호: ${tokenInfo.id}" +
                        "\n만료시간: ${tokenInfo.expiresIn} 초")
                val intent = Intent(this, CertificateActivity::class.java)
                startActivity(intent)
            }
        }
    }
}