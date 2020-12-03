package com.android.transformer.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.transformer.LandingActivity
import com.android.transformer.R
import com.android.transformer.model.Transformer
import kotlinx.android.synthetic.main.battel_fragment.*

class BattelFragment : Fragment() {

    companion object {
        fun newInstance() = BattelFragment()
    }

    private lateinit var viewModel: BattelViewModel
    private var transformerList = ArrayList<Transformer>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.battel_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as LandingActivity).supportActionBar?.setTitle(getString(R.string.auto_vs_decp))
        addObserver()
    }

    private fun addObserver() {
        viewModel = ViewModelProvider(this).get(BattelViewModel::class.java)
        viewModel.startBattel(transformerList)
        viewModel.battelResult.observe(viewLifecycleOwner, Observer {
            tvResult.setText(it)
        })

    }

    fun setTransformerList(transformers: ArrayList<Transformer>) {
        transformerList = transformers
    }

}