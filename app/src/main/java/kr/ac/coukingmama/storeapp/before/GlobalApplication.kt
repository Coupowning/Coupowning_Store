package kr.ac.coukingmama.storeapp.before

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import kr.ac.coukingmama.storeapp.R

class GlobalApplication : Application() {
    override fun onCreate(){
        super.onCreate()
        KakaoSdk.init(this, getString(R.string.kakao_app_key))
    }
}