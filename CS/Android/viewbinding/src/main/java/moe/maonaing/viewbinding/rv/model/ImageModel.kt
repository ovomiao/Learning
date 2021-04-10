package moe.maonaing.viewbinding.rv

import androidx.annotation.DrawableRes
import moe.maonaing.viewbinding.R

data class Face(val name: String, @DrawableRes val resId: Int)

class ImageModel {
    val faces = arrayListOf(
            Face("滑稽", R.drawable.ic_1),
            Face("吐舌", R.drawable.ic_2),
            Face("乖", R.drawable.ic_3),
            Face("小乖", R.drawable.ic_4),
            Face("笑眼", R.drawable.ic_5),
            Face("喷", R.drawable.ic_6),
            Face("滑稽", R.drawable.ic_1),
            Face("吐舌", R.drawable.ic_2),
            Face("乖", R.drawable.ic_3),
            Face("小乖", R.drawable.ic_4),
            Face("笑眼", R.drawable.ic_5),
            Face("喷", R.drawable.ic_6),
            Face("滑稽", R.drawable.ic_1),
            Face("吐舌", R.drawable.ic_2),
            Face("乖", R.drawable.ic_3),
            Face("小乖", R.drawable.ic_4),
            Face("笑眼", R.drawable.ic_5),
            Face("喷", R.drawable.ic_6),
            Face("滑稽", R.drawable.ic_1),
            Face("吐舌", R.drawable.ic_2),
            Face("乖", R.drawable.ic_3),
            Face("小乖", R.drawable.ic_4),
            Face("笑眼", R.drawable.ic_5),
            Face("喷", R.drawable.ic_6)
    )
}