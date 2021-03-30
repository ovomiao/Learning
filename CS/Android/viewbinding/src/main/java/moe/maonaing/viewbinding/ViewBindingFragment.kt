package moe.maonaing.viewbinding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import moe.maonaing.viewbinding.databinding.FragmentViewBindingBinding

class ViewBindingFragment : Fragment() {
    private lateinit var fragmentBinding: FragmentViewBindingBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // 这里是一样的
        fragmentBinding = FragmentViewBindingBinding.inflate(layoutInflater)
        // 这里变化了
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val str = fragmentBinding.textView.text
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ViewBindingFragment()
    }
}