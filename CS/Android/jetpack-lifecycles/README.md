[Jetpack - Lifecycles 让自己的类感知 Activity/Fragment 的声明周期](https://github.com/ovomiao/Learning/tree/main/CS/Android/jetpack-lifecycles/src/main/java/moe/maonaing/jetpack_lifecycles)

**【注意事项】**

 *  **要感知生命周期的类需要实现 `LifecycleObserver` 接口**

 *  **在使用此控件的控制器中，需要将此控件注册为生命周期观察者**

     *  ```kotlin
        class AppChronometerActivity : AppCompatActivity() {
        
            private lateinit var appChronometer: AppChronometer
        
            override fun onCreate(savedInstanceState: Bundle?) {
        
                findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
                    finish()
                }
        
                appChronometer = findViewById(R.id.chronometer)
                appChronometer.start()
        
                // 将自己的组件注册为生命周期的观察者
                lifecycle.addObserver(appChronometer)
            }
        
            override fun onDestroy() {
                super.onDestroy()
                lifecycle.removeObserver(appChronometer)
            }
        }
        ```





