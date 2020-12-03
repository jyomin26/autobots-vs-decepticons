package com.android.transformer.ui.main

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.transformer.LandingActivity
import com.android.transformer.R
import com.android.transformer.model.Transformer
import com.android.transformer.ui.main.adapter.TransformerAdapter
import com.android.transformer.util.PreferenceHelper
import kotlinx.android.synthetic.main.landing_fragment.*

/**
 * Landing Scrreen which displays list of transformers and two buttons to add transformer and start battel
 */
class LandingFragment : Fragment(), TransformerAdapter.onItemClicked {

    companion object {
        fun newInstance() = LandingFragment()
    }

    private lateinit var viewModel: LandingViewModel
    private var dialog: ProgressDialog? = null
    private lateinit var transformerAdapter: TransformerAdapter
    private var transformerList = ArrayList<Transformer>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.landing_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initialiseListener()
        initialiseRecyclerView()
        initialiseObserver()
        checkForAccessToken()
    }

    private fun initialiseRecyclerView() {
        val layoutManager = LinearLayoutManager(this.activity)
        rv_transformers.layoutManager = layoutManager
    }

    private fun initialiseObserver() {
        viewModel = ViewModelProvider(this).get(LandingViewModel::class.java)
        viewModel.accessToken.observe(viewLifecycleOwner, Observer {
            //save access token received from api in shared pref
            PreferenceHelper.saveString(PreferenceHelper.AUTH_TOKEN, it)
        })

        viewModel.transformerList.observe(viewLifecycleOwner, Observer {
            transformerList = it
            transformerAdapter = TransformerAdapter(it, this)
            rv_transformers.adapter = transformerAdapter
            dialog?.dismiss()
            if (transformerList.isEmpty())
                Toast.makeText(
                    activity,
                    getString(R.string.no_transformer_fund),
                    Toast.LENGTH_SHORT
                ).show()
        })

        viewModel.transformersError.observe(viewLifecycleOwner, Observer {
            dialog?.dismiss()
        })

        viewModel.deleteSuccess.observe(viewLifecycleOwner, Observer { deletedTransformer ->
            transformerList.remove(deletedTransformer)
            transformerAdapter.updateList(deletedTransformer)
            dialog?.dismiss()
        })

        viewModel.deleteError.observe(viewLifecycleOwner, Observer {
            dialog?.dismiss()
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onResume() {
        super.onResume()
        (activity as LandingActivity).supportActionBar?.setTitle(getString(R.string.transformers))
        showLoader()
        viewModel.getTransformers()
    }

    /**
     * Method required to check if request token is needed to save in shared pref or not
     */
    private fun checkForAccessToken() {
        val accessToken = PreferenceHelper.getString(PreferenceHelper.AUTH_TOKEN)
        if (accessToken.isNullOrEmpty()
        ) {
            viewModel.getAccessToken()
        }
    }

    private fun initialiseListener() {
        btn_add_transformer.setOnClickListener {
            (activity as LandingActivity).replaceAddUpdateFragment()
        }
        btn_start_battel.setOnClickListener {
            (activity as LandingActivity).replaceBattelFragment(transformerList)
        }
    }

    private fun showLoader() {
        dialog = ProgressDialog(activity)
        dialog?.setTitle(getString(R.string.please_wait))
        dialog?.setCancelable(false) // disable dismiss by tapping outside of the dialog
        dialog?.show()
    }

    override fun onEditClicked(position: Int, transformer: Transformer) {
        (activity as LandingActivity).replaceAddUpdateFragment(transformer)
    }

    override fun onDeleteClicked(position: Int, transformer: Transformer) {
        showLoader()
        viewModel.deleteTransformer(transformer, position)
    }
}


