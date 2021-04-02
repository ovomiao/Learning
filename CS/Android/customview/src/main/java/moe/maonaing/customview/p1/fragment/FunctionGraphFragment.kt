package moe.maonaing.customview.p1.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import moe.maonaing.customview.R

class FunctionGraphFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_function_graph, container, false)
    }

    companion object {
        const val TITLE = "FunctionGraph"

        fun newInstance() = FunctionGraphFragment()
    }

}
