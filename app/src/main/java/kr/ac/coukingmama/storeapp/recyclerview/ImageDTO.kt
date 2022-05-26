package kr.ac.coukingmama.storeapp.recyclerview

import android.graphics.Bitmap
import android.net.Uri

class ImageDTO(val image: Bitmap? = null) { // 이미지 DTO
    var uri : Uri? = null
}