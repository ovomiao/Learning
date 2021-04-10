package moe.maonaing.viewbinding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import moe.maonaing.viewbinding.databinding.FragmentViewBindingBinding

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

    companion object {
        @JvmStatic
        fun newInstance() = ViewBindingFragment()
    }
}