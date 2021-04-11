# BottomNavigationView（底部导航控件）

## 底部导航栏

* **NavigationView 的几个页面应该是互相独立的，没有前后的逻辑关系。一般底部导航
推荐 2-5 个页面，不宜过多**

## 使用步骤

### 创建底部导航条所需要的菜单

* **每一个菜单项需要 `id`、`icon`、`title` 三个属性**

  * ```xml
    <!--res/menu-->
    <?xml version="1.0" encoding="utf-8"?>
    <menu xmlns:tools="http://schemas.android.com/tools"
        xmlns:android="http://schemas.android.com/apk/res/android"
        tools:ignore="HardcodedText">
    
        <!--
            * 如果使用 Jetpack-Navigation 组件，那么这里的 id
              需要和 Navigation 里面的 id 一致
        -->
    
        <item
            android:id="@+id/homeFragment"
            android:icon="@drawable/ic_baseline_home_24"
            android:title="主页" />
    
        <item
            android:id="@+id/categoryFragment"
            android:icon="@drawable/ic_baseline_category_24"
            android:title="分类" />
    
        <item
            android:id="@+id/meFragment"
            android:icon="@drawable/ic_baseline_sentiment_very_satisfied_24"
            android:title="我的" />
    
    </menu>
    ```

### 创建所需的页面以及 ViewModel(可选)

### (可选)创建 Navigation 描述文件

* **创建步骤**：res -> new -> Android Resource File -> Resource File 选 `Navigation`

* **完成 Navigation**

  * **1. 将用于 `BottomNavigationView` 所需的 `Fragment` 添加到 <font color="red">Destination</font> 中**

  * **2. 因为这种情况下，Fragment 与 Fragment 之间是互相独立的，没有跳转关系，所以无需连线设置 action；只需要保证正确设置 `startDestination` 即可**

  * **3. 要实现导航这里的 `fragment id` 需要和 `menu` 中的 id 一致**

  * **4. xml 参考文件**

    * ```xml
      <?xml version="1.0" encoding="utf-8"?>
      <navigation xmlns:android="http://schemas.android.com/apk/res/android"
          xmlns:app="http://schemas.android.com/apk/res-auto"
          xmlns:tools="http://schemas.android.com/tools"
          android:id="@+id/navigation_home"
          app:startDestination="@id/homeFragment">
      
          <!--
              * 这里的 id 值要和 menu 中 item 的 id 一致，才能完成导航
              * 者的 label 会展示在 actionbar 上（如果存在的话）
          -->
          <fragment
              android:id="@+id/homeFragment"
              android:name="moe.maonaing.ui_bottom_navigation_view.home.HomeFragment"
              android:label="主页" />
          <fragment
              android:id="@+id/categoryFragment"
              android:name="moe.maonaing.ui_bottom_navigation_view.category.CategoryFragment"
              android:label="分类" />
          <fragment
              android:id="@+id/meFragment"
              android:name="moe.maonaing.ui_bottom_navigation_view.me.MeFragment"
              android:label="我的"
              tools:layout="@layout/fragment_me" />
      
      </navigation>
      ```

### 使用 Navigation

* **1. 在 Activity 布局文件中添加 `BottomNavigationView` 控件和 `NavHostFragment` 完成导航布局**

  * ```xml
    <?xml version="1.0" encoding="utf-8"?>
    <androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".MainActivity">
    
        <!--
            * app:menu 定义底部导航按钮所需的菜单描述文件
        -->
        <com.google.android.material.bottomnavigation.BottomNavigationView
            android:id="@+id/bottomNavigationView"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:menu="@menu/menu_bottom_navigation_view" />
    
        <fragment
            android:id="@+id/fragment"
            android:name="androidx.navigation.fragment.NavHostFragment"
            android:layout_width="0dp"
            android:layout_height="0dp"
            app:defaultNavHost="true"
            app:layout_constraintBottom_toTopOf="@+id/bottomNavigationView"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            app:navGraph="@navigation/navigation_home" />
    
    </androidx.constraintlayout.widget.ConstraintLayout>
    ```

* **2. 关联 AppBar 和 Navigation ；关联 BottomNavigationView 和 Navigation 组件**

  * **使用 `AppBarConfiguration` 配置 AppBar**

  * **`NavigationUI -> static void setupActionBarWithNavController(...)` 关联 AppBar 和 Navigation 组件**

  * **`NavigationUI -> static void setupWithNavController(...)` 关联 BottomNavigationView 控件和 Navigation 组件**

  * **代码参考**

    * ```kotlin
      class MainActivity : AppCompatActivity() {
      
          override fun onCreate(savedInstanceState: Bundle?) {
              super.onCreate(savedInstanceState)
              setContentView(R.layout.activity_main)
              
              val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
              // 注意：这里的 findNavController() 不需要传递 activity 参数是因为
              //      RTX 中 Activity.kt 的扩展函数在起作用。
              //      对于 java 需要使用：
              //          public static NavController findNavController(@NonNull Activity activity, @IdRes int viewId)
              val navigationController = findNavController(R.id.fragment)
              //val navigationController = findNavController(this, R.id.fragment) // java 的方式
      
      
              // 这里传入 BottomNavigationView 的 menu 就不会显示返回按钮了
              val appBarConfiguration = AppBarConfiguration.Builder(bottomNavigationView.menu)
                      .build()
      
              // 关联 AppBar 和导航组件
              NavigationUI.setupActionBarWithNavController(
                      this,
                      navigationController,
                      appBarConfiguration
              )
              // 关联 BottomNavigationView 和 Navigation 组件
              // 如果需要使用 Toolbar 自定义 AppBar 也可以使用这个方法组装
              NavigationUI.setupWithNavController(
                      bottomNavigationView,
                      navigationController
              )
          }
      
      }
      ```

* **3. 可能出现的问题**

  * 3.1 在 Activity 中正确关联 BottomNavigationView 和 Navigation 组件后，点击 BottomNavigationView 的按钮依然无法实现导航控制。

    * **可能的原因：/res/menu/menu.xml 文件中的 `item id` 和 res/navigation/navigation.xml 中的 `fragment id` 不一致.**

      * 错误案例

        * ```xml
          <?xml version="1.0" encoding="utf-8"?>
          <menu xmlns:tools="http://schemas.android.com/tools"
              xmlns:android="http://schemas.android.com/apk/res/android"
              tools:ignore="HardcodedText">
          
              <!--
                  * 如果使用 Jetpack-Navigation 组件，那么这里的 id
                    需要和 Navigation 里面的 id 一致
              -->
          
              <item
                  android:id="@+id/menuHomeFragment"
                  android:icon="@drawable/ic_baseline_home_24"
                  android:title="主页" />
          
          </menu>
          
          <?xml version="1.0" encoding="utf-8"?>
          <navigation xmlns:android="http://schemas.android.com/apk/res/android"
              xmlns:app="http://schemas.android.com/apk/res-auto"
              xmlns:tools="http://schemas.android.com/tools"
              android:id="@+id/navigation_home"
              app:startDestination="@id/homeFragment">
          
              <!-- 
          		这里的 id = homeFragment ，而 meun 中的 id 是 menuHomeFragment
          		最终不能完成导航
              -->
              
              <fragment
                  android:id="@+id/homeFragment"
                  android:name="moe.maonaing.ui_bottom_navigation_view.home.HomeFragment"
                  android:label="主页" />
          
          </navigation>
          ```

  * 3.2 能正确实现导航，但是在 `BottomNavigationView` 上切换 `NavHostFragment` 中的页面时，出现了上一级图标按钮（即返回按钮）

    * **错误原因，在 AppBar 配置时，传参错误；以下是错误的传参代码**

      * ```kotlin
        // 配置 Appbar
        //  这里传入 navigationController.graph 会导致 返回按钮显示出来
        val appBarConfiguration = AppBarConfiguration.Builder(navigationController.graph)
        	.build()
        ```

    * **`BottomNavigation + Navigation` 时， AppBar 的正确配置代码**

      * ```kotlin
        // 这里传入 BottomNavigationView 的 menu 就不会显示返回按钮了
        val appBarConfiguration = AppBarConfiguration.Builder(bottomNavigationView.menu)
        	.build()
        ```

      * ```kotlin
        // 之间传入三个页面的 id 也可以
        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.homeFragment,
            R.id.categoryFragment,
            R.id.meFragment
        ).build()
        ```

## 使用自定义的 Toolbar



