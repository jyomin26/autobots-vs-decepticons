package com.android.transformer.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.transformer.model.BattelRepository
import com.android.transformer.model.Transformer
import com.android.transformer.model.TransformerRepository

/**
 * View Model for BattelFragment
 */
class BattelViewModel : ViewModel() {
    var battelResult = MutableLiveData<String>()

    fun startBattel(transformers: ArrayList<Transformer>) {
        BattelRepository.clearLastBattel()
        BattelRepository.startBattel(transformers)
        battelResult.postValue(BattelRepository.generateBattelResult())
    }
}