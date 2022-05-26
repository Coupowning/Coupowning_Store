package kr.ac.coukingmama.storeapp.recyclerview

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import kr.ac.coukingmama.storeapp.R

@GlideModule
class ListItemAdapter(val context: Context, private var imageDTOList: MutableList<ImageDTO> = ArrayList()): RecyclerView.Adapter<ListItemAdapter.ListViewHolder>() { // 리사이클러뷰 어댑터

    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var imageView: ImageView? = itemView.findViewById<ImageView>(R.id.item)

        fun onBind(data: ImageDTO) {
            if(data.uri != null)
                Glide.with(context).asBitmap().load(data.uri).centerCrop().into(imageView!!)
            else
                imageView!!.setImageBitmap(data.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.onBind(imageDTOList[position])
    }

    override fun getItemCount(): Int {
        return imageDTOList.size
    }

    fun setListData(it: Bitmap) {
        imageDTOList.add(ImageDTO(it))
        notifyDataSetChanged()
    }
    fun setListData(it: ImageDTO){
        imageDTOList.add(it)
        notifyDataSetChanged()
    }
}