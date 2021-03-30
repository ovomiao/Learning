package moe.maonaing.viewbinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MyFragmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_fragment)
        supportFragmentManager.beginTransaction()
                .replace(R.id.flContainer, ViewBindingFragment.newInstance())
                .commit()
    }

}
