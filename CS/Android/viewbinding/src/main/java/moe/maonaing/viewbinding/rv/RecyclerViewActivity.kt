package moe.maonaing.viewbinding.rv

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import moe.maonaing.viewbinding.databinding.ActivityRecyclerViewBinding
import moe.maonaing.viewbinding.rv.adapter.RVAdapter

class RecyclerViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecyclerViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRV()

        binding.fab.setOnClickListener {
            finish()
        }
    }

    private fun initRV() {
        val rvAdapter = RVAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(
                    this@RecyclerViewActivity,
                    LinearLayoutManager.VERTICAL,
                    false
            )

            adapter = rvAdapter
        }
    }

}