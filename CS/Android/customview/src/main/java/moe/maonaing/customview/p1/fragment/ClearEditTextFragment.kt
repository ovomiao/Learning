package moe.maonaing.customview.p1.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import moe.maonaing.customview.R

class ClearEditTextFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_clear_edit_text, container, false)
    }

    companion object {

        const val TITLE = "ClearEditText"

        @JvmStatic
        fun newInstance() = ClearEditTextFragment()
    }

}