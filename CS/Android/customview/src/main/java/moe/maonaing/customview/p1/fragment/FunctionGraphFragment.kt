package moe.maonaing.customview.p1.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import moe.maonaing.customview.R
import moe.maonaing.customview.p1.view.SineFunctionView
import kotlin.math.sin

class FunctionGraphFragment : Fragment() {
    private lateinit var sineFunctionView: SineFunctionView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_function_graph, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sineFunctionView = view.findViewById(R.id.functionView)

        // 让 SineFunction 动画跟随 Fragment 的生命周期
        lifecycle.addObserver(sineFunctionView)
    }

    companion object {
        const val TITLE = "FunctionGraph"

        fun newInstance() = FunctionGraphFragment()
    }

}
