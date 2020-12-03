package com.android.transformer.ui.main

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.transformer.LandingActivity
import com.android.transformer.R
import com.android.transformer.model.Transformer
import kotlinx.android.synthetic.main.addupdate_transformer_fragment.*

/**
 * Class to provide feature of Add or Edit Transformer
 */
class AddUpdateTransformerFragment : Fragment() {

    companion object {
        fun newInstance() = AddUpdateTransformerFragment()
    }

    private lateinit var viewModel: AddUpdateViewModel
    private var dialog: ProgressDialog? = null
    private var transformer: Transformer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.addupdate_transformer_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initilaiseListener()
        addObserver()
        updateData()
    }

    private fun updateData() {
        if (transformer == null) {
            (activity as LandingActivity).supportActionBar?.setTitle(getString(R.string.add_new_transformer))
            return
        }
        (activity as LandingActivity).supportActionBar?.setTitle(getString(R.string.edit_transformer))

        transformer?.let {
            et_name.setText(it.name)
            spinner_strength.setSelection(it.strength - 1)
            spinner_intelligence.setSelection(it.intelligence - 1)
            spinner_speed.setSelection(it.speed - 1)
            spinner_endurance.setSelection(it.endurance - 1)

            spinner_rank.setSelection(it.rank - 1)
            spinner_courage.setSelection(it.courage - 1)
            spinner_firepower.setSelection(it.firepower - 1)
            spinner_skill.setSelection(it.skill - 1)
            when (it.team) {
                "A" -> spinner_team.setSelection(0)
                "D" -> spinner_team.setSelection(1)
            }
        }
    }

    private fun addObserver() {
        viewModel = ViewModelProvider(this).get(AddUpdateViewModel::class.java)

        viewModel.transformerSuccess.observe(viewLifecycleOwner, Observer {
            if (transformer == null)
                Toast.makeText(
                    activity,
                    getString(R.string.transformer_added_success),
                    Toast.LENGTH_SHORT
                )
                    .show()
            else
                Toast.makeText(
                    activity,
                    getString(R.string.transformer_update_success),
                    Toast.LENGTH_SHORT
                )
                    .show()
            dialog?.dismiss()
            activity?.onBackPressed()
        })

        viewModel.transformerError.observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
            dialog?.dismiss()
        })
    }

    private fun initilaiseListener() {
        btn_done.setOnClickListener {
            if (et_name.text.isEmpty()) {
                et_name.setError(getString(R.string.please_enter_name))
                return@setOnClickListener
            }
            showLoader()
            var latestTransformer = getLatestTansformer()
            if (transformer == null) {
                viewModel.addTransformer(latestTransformer)
                return@setOnClickListener
            }
            latestTransformer.id = transformer!!.id
            latestTransformer.team_icon = transformer!!.team_icon
            viewModel.updateTransformer(latestTransformer)
        }
    }

    private fun getLatestTansformer(): Transformer {
        return Transformer(
            name = et_name.text.toString().trim(),
            strength = (spinner_strength.selectedItem as String).toInt(),
            intelligence = (spinner_intelligence.selectedItem as String).toInt(),
            skill = (spinner_skill.selectedItem as String).toInt(),
            courage = (spinner_courage.selectedItem as String).toInt(),
            endurance = (spinner_endurance.selectedItem as String).toInt(),
            firepower = (spinner_firepower.selectedItem as String).toInt(),
            rank = (spinner_rank.selectedItem as String).toInt(),
            speed = (spinner_speed.selectedItem as String).toInt(),
            team = (spinner_team.selectedItem as String).substring(0, 1)
        )
    }

    private fun showLoader() {
        dialog = ProgressDialog(activity)
        dialog?.setTitle(getString(R.string.please_wait))
        dialog?.setCancelable(false) // disable dismiss by tapping outside of the dialog
        dialog?.show()
    }

    fun setTransformerData(updatedTransformer: Transformer?) {
        transformer = updatedTransformer
    }

}