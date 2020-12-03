package com.android.transformer.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.transformer.model.Transformer
import com.android.transformer.model.TransformerRepository

/**
 * View Model for Landing Fragment
 */
class LandingViewModel : ViewModel() {
    var accessToken = MutableLiveData<String>()
    var transformerList = MutableLiveData<ArrayList<Transformer>>()
    var transformersError = MutableLiveData<String>()
    var deleteSuccess = MutableLiveData<Transformer>()
    var deleteError = MutableLiveData<String>()

    fun getAccessToken() {
        TransformerRepository.getToken {
            accessToken.postValue(it ?: "")
        }
    }

    fun getTransformers() {
        TransformerRepository.getTransformersList(onSuccess = {
            transformerList.postValue(it?.transformers)
        }, onError = {
            transformersError.postValue(it)
        })

    }

    fun deleteTransformer(transformer: Transformer, position: Int) {
        TransformerRepository.deleteTransformer(transformer.id, onSuccess = {
            deleteSuccess.postValue(transformer)
        }, onError = {
            deleteError.postValue(it)
        })
    }

}