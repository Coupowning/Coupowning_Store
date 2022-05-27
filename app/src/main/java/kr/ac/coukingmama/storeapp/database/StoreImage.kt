package kr.ac.coukingmama.storeapp.database

data class StoreImage(
    val list : ArrayList<ImageData>
)

data class ImageData(
    val id : String,
    val uri : String
)