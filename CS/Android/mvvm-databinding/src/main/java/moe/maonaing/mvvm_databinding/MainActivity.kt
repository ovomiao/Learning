package moe.maonaing.mvvm_databinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import moe.maonaing.mvvm_databinding.databinding.ActivityMainBinding

// 更多内容参考：https://www.jianshu.com/p/bd9016418af2
// 报错问题多检查 XML ，特别是一些赋值方法
class MainActivity : AppCompatActivity() {
    // ActivityMainBinding 在执行转换 Data-Binding 时，自动生成的类
    private lateinit var dataBinding: ActivityMainBinding // 将界面 UI 对象绑定到 DataBinding
    private lateinit var viewModel: ViewModelLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        //1. 加载布局，初始化 Data-Binding 对象
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // 2. 此时 xml 中所有 UI 控件的 ID 都成为 ActivityMainBinding 类的成员
        // 需要访问控件，可以这么玩 dataBinding.textView.text = 0.toString()

        viewModel = ViewModelProvider(this).get(ViewModelLiveData::class.java)
        // 3. 为 XML 中定义的 data：ViewModelLiveData 赋值
        dataBinding.data = viewModel
        // 4. 这里也需要：监听数据变化
        dataBinding.lifecycleOwner = this
    }

}