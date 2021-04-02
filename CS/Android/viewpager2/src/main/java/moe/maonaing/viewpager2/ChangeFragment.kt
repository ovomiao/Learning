package moe.maonaing.viewpager2

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import moe.maonaing.viewpager2.java.JavaViewPager2Activity


class ChangeFragment : Fragment() {

    private lateinit var btnChange: Button
    private lateinit var btnAlbum: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnAlbum = view.findViewById(R.id.album)
        btnChange = view.findViewById(R.id.change)

        if (activity is JavaViewPager2Activity) {
            btnChange.text = "回到 Kotlin 版"
        }

        btnChange.setOnClickListener {
            val parentActivity = activity
            parentActivity?.let {
                if (it is JavaViewPager2Activity) {
                    startActivity(Intent(context, MainActivity::class.java))
                    it.finish()
                } else {
                    startActivity(Intent(context, JavaViewPager2Activity::class.java))
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ChangeFragment()
    }
}