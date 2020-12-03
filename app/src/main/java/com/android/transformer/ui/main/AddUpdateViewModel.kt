package com.android.transformer.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.transformer.model.Transformer
import com.android.transformer.model.TransformerRepository

/**
 * View Model for AddUpdateFragment
 */
class AddUpdateViewModel : ViewModel() {
    var transformerSuccess = MutableLiveData<String>()
    var transformerError = MutableLiveData<String>()


    fun addTransformer(transformer: Transformer) {
        TransformerRepository.addTransformer(transformer, onSuccess = {
            transformerSuccess.postValue("Success")
        }, onError = {
            transformerError.postValue(it)
        }
        )
    }

    fun updateTransformer(latestTransformer: Transformer) {
        TransformerRepository.updateTransformer(latestTransformer, onSuccess = {
            transformerSuccess.postValue("Success")
        }, onError = {
            transformerError.postValue(it)
        }
        )
    }
}