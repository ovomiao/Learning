package moe.maonaing.customview.p1.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import moe.maonaing.customview.R
import moe.maonaing.customview.p1.view.CartesianCardioidView


class CartesianCardioidFragmentFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cartesian_cardioid_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.addObserver(view.findViewById<CartesianCardioidView>(R.id.view))
    }

    companion object {
        const val TITLE = "Cartesian Cardioid"

        fun newInstance() = CartesianCardioidFragmentFragment()
    }

}