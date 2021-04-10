# ViewBinding（视图绑定）
* **需要 Android Studio 3.6+ 才能使用**

* **参考文章**

  * **[Google 的文档](https://developer.android.google.cn/topic/libraries/view-binding?hl=zh-cn)**
  * **[大佬的使用指南](https://mp.weixin.qq.com/s/keR7bO-Nu9bBr5Nhevbe1Q)**

* **关于  ViewBinding**

  * **如果一个模块启用了 ViewBinding，那么该模块下所有的 XML 都会生成一个 ViewBinding 类**

  * **生成的 ViewBinding 类名为布局文件驼峰命名+ Binding**

    * 例如：activity_main -> ActivityMainBinding

  * **ViewBinding 可以同时应用于 Kotlin 和 Java**

  * **如果某一个布局文件不需要生成 ViewBinding 类，可以在根标签使用此 `tools:viewBindingIgnore="true"` 配置**

    * ```xml
      <FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
          xmlns:tools="http://schemas.android.com/tools"
          android:layout_width="match_parent"
          android:layout_height="match_parent"
          tools:viewBindingIgnore="true"
          tools:context=".ViewBindingFragment">
          <!-- 其他内容 -->
      </FrameLayout>
      ```

* **启用 ViewBinding**

  * ```groovy
    // 4.0 之前的版本
    android {
        viewBinding {
            enabled true
        }
        //dataBinding {
        //    enabled true
        //}
    }
    
    //app/build.gradle
    // Android Studio 4.0 开始的配置
    android {
        buildFeatures {
            viewBinding true
            // dataBinding true -> DataBinding 也是在这里配置
        }
    }
    ```

* **在 Activity 中使用**

  * ```java
    //Kotlin
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            // 获取 binding 对象
            binding = ActivityMainBinding.inflate(layoutInflater)
            // 设置 View
            setContentView(binding.root)
            // 使用
            binding.changeText.setOnClickListener {
            binding.textView.text = "你好，世界！"
        }
    }
    
    // Java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 获取 ViewBinding 对象
        mBinding = ActivityJavaMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot()); // 替换原来的 R.layout.xml
    
        // 访问控件
        mBinding.changeText.setOnClickListener((view) -> {
            mBinding.textView.setText("你好，世界！");
        });
    }
    ```

* **在 Fragment 中使用**

  * **在 Fragment 中使用 ViewBinding ，需要在 `onDestroyView()` 中置空，否则 ViewBinding 不会在 Fragment 视图被移除时清除**

    * ```kotlin
      class ViewBindingFragment : Fragment() {
          private var _fragmentBinding: FragmentViewBindingBinding? = null
          private val fragmentBinding get() = _fragmentBinding!!
      
          override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                    savedInstanceState: Bundle?): View? {
              // 这里是一样的
              _fragmentBinding = FragmentViewBindingBinding.inflate(layoutInflater)
              // 返回 Fragment 的根 View
              return fragmentBinding.root
          }
      
          override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
              super.onViewCreated(view, savedInstanceState)
      
              val str = fragmentBinding.textView.text
              Toast.makeText(context, str, Toast.LENGTH_SHORT).show()
          }
      
          override fun onDestroyView() {
              super.onDestroyView()
              // 记得在这里置空
              _fragmentBinding = null
          }
      }
      ```

* **在 Adapter 中使用：只需要给 `ViewHolder` 传入 `ViewBinding` 实例即可，而父 `ViewHolder` 的构造只需要从 `ViewHolder.root` 得到 `ItemView` 实例传入即可.**

  * ```kotlin
    /**
         * 使用 ViewBinding 之后，ViewHolder 只需要传递一个 ViewBinding 对象即可，
         * 对于父 ViewHolder 直接传入 ViewBinding.root 即可将 ItemView 传递给他
         */
    class RVHolder(viewBinding: ItemIvTextBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        val imageView = viewBinding.iv
        val textView = viewBinding.text
    }
    ```

* **【代码】**

  * [Kotlin 版](https://github.com/ovomiao/Learning/tree/main/CS/Android/viewbinding/src/main/java/moe/maonaing/viewbinding/MainActivity.kt)
  * [Java 版](https://github.com/ovomiao/Learning/tree/main/CS/Android/viewbinding/src/main/java/moe/maonaing/viewbinding/JavaMainActivity.java)
  * [Fragment 中使用](https://github.com/ovomiao/Learning/tree/main/CS/Android/viewbinding/src/main/java/moe/maonaing/viewbinding/ViewBindingFragment.kt)
  * [RecyclerView.Adapter 中使用](https://github.com/ovomiao/Learning/tree/main/CS/Android/viewbinding/src/main/java/moe/maonaing/viewbinding/rv)
