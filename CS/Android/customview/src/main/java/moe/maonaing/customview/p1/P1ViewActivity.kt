package moe.maonaing.customview.p1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import moe.maonaing.customview.R

class P1ViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_p1_view)
        supportFragmentManager.beginTransaction()
                .replace(R.id.clContent, P1HomeFragment.newInstance())
                .addToBackStack(TAG)
                .commit()
    }

}