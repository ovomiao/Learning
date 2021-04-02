package moe.maonaing.customview.p1.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import moe.maonaing.customview.R


class SurfaceViewFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_surface_view, container, false)
    }

    companion object {
        const val TITLE = "SurfaceView"

        fun newInstance() = SurfaceViewFragment()
    }
}