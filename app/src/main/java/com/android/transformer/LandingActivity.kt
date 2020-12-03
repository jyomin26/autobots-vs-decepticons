package com.android.transformer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.transformer.model.Transformer
import com.android.transformer.ui.main.AddUpdateTransformerFragment
import com.android.transformer.ui.main.BattelFragment
import com.android.transformer.ui.main.LandingFragment
import java.util.ArrayList

/**
 * Main Landing Activity which holds the container for the fragments
 */
class LandingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.landing_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, LandingFragment.newInstance())
                .commitNow()
        }
    }

    /**
     * Replace current fragment with the add update fragment
     */
    fun replaceAddUpdateFragment(transformer: Transformer? = null) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val addUpdateFragment = AddUpdateTransformerFragment.newInstance()
        addUpdateFragment.setTransformerData(transformer)
        fragmentTransaction.replace(
            R.id.container, addUpdateFragment
        )
        fragmentTransaction.addToBackStack("AddUpdateTransformerFragment")
        fragmentTransaction.commitAllowingStateLoss()
    }

    /**
     * Replace current fragment with Battel Fragment
     */
    fun replaceBattelFragment(transformerList: ArrayList<Transformer>) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val battelFragment = BattelFragment.newInstance()
        battelFragment.setTransformerList(transformerList)
        fragmentTransaction.replace(
            R.id.container, battelFragment
        )
        fragmentTransaction.addToBackStack("BattelFragment")
        fragmentTransaction.commitAllowingStateLoss()
    }
}