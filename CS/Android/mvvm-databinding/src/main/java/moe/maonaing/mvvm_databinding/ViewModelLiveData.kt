package moe.maonaing.mvvm_databinding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModelLiveData : ViewModel() {

    val mutableLiveData = MutableLiveData<Int>()

    fun add(n: Int) {
        val num = mutableLiveData.value?:0
        mutableLiveData.value = num + n
    }


}