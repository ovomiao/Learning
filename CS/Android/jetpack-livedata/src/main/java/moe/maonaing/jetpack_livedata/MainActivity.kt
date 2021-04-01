package moe.maonaing.jetpack_livedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ViewModelLiveData

    private lateinit var like: ImageButton
    private lateinit var dislike: ImageButton
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        like = findViewById(R.id.like)
        dislike = findViewById(R.id.dislike)
        textView = findViewById(R.id.textView)
        textView.text = 0.toString()

        viewModel = ViewModelProvider(this).get(ViewModelLiveData::class.java)


        /**
         * LiveData#void observe(LifecycleOwner owner, Observer<? super T> observer)
         *      * LifecycleOwner -> 拥有 Lifecycle 功能的对象
         *
         *    public interface Observer<T> {
         *           void onChanged(T t);
         *   }
         *
         *  * 给 LiveData 设置一个观察者，数据变化是 LiveData 自动回调
         *    onChang() 方法。这时我们就可以在这里刷新 UI 了
         */
        viewModel.mutableLiveData
                .observe(this) { //给 LiveData 添加一个观察者
            textView.text = it.toString()
                    Log.i("TAG", "onCreate: $it")
        }

        like.setOnClickListener {
            viewModel.add(1)
            Log.i("TAG", "onCreate: click like")
        }

        dislike.setOnClickListener {
            viewModel.add(-1)
        }
    }

}