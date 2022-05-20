package kr.ac.coukingmama.storeapp.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import kr.ac.coukingmama.storeapp.R


class ListItemAdapter(val context: Context,  private val imageDTOList: List<ImageDTO> = ArrayList(),
        private val uidList: List<String> = ArrayList(),
        private val storage: FirebaseStorage? = null): RecyclerView.Adapter<ListItemAdapter.ListViewHolder>() { // 리사이클러뷰 어댑터

    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var imageView = itemView.findViewById<ImageView>(R.id.list_item)!!

        fun onBind(data: ImageDTO){
//            imageView = data.imageUrl
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.store_image, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.onBind(imageDTOList[position])
    }

    override fun getItemCount(): Int {
        return imageDTOList.size
    }
}