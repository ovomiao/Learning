package moe.maonaing.jetpack_livedata

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModelLiveData() : ViewModel() {
    // 可以观察数据变换的 LiveData，并自动回调观察者的 onChange()
    val mutableLiveData: MutableLiveData<Int> = MutableLiveData()

        fun add(n: Int) {
            val num = mutableLiveData.value?:0
            mutableLiveData.value = n + num
        }
}