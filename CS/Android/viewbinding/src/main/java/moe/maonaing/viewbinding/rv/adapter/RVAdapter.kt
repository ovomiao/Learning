package moe.maonaing.viewbinding.rv.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import moe.maonaing.viewbinding.databinding.ItemIvTextBinding
import moe.maonaing.viewbinding.rv.ImageModel

class RVAdapter() : RecyclerView.Adapter<RVAdapter.RVHolder>() {

    private val faces = ImageModel().faces

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVHolder {
        val inflater = LayoutInflater.from(parent.context)
        // 创建 ViewBinding 对象
        val viewBinding = ItemIvTextBinding.inflate(inflater, parent, false)
        return RVHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: RVHolder, position: Int) {
        holder.textView.text = faces[position].name
        holder.imageView.setImageResource(faces[position].resId)
    }

    override fun getItemCount() = faces.size

    /**
     * 使用 ViewBinding 之后，ViewHolder 只需要传递一个 ViewBinding 对象即可，
     * 对于父 ViewHolder 直接传入 ViewBinding.root 即可将 ItemView 传递给他
     */
    class RVHolder(viewBinding: ItemIvTextBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        val imageView = viewBinding.iv
        val textView = viewBinding.text
    }
}