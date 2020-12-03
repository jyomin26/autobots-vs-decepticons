package com.android.transformer.model

import android.util.Log
import com.android.transformer.util.PreferenceHelper
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.OkHttpResponseListener
import com.androidnetworking.interfaces.ParsedRequestListener
import com.androidnetworking.interfaces.StringRequestListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.Response
import org.json.JSONObject


private const val TAG = "transformer-repository"

/**
 * Repository class to create, update , fetch or delete Transformers using Rx2AndroidNetworking library
 */
object TransformerRepository {
    private var deviceBaseUrl: String? = "https://transformers-api.firebaseapp.com"
    private var contentTypeKey = "Content-Type"
    private var contentTypeValue = "application/json"
    private var authorizationKey = "Authorization"
    private var bearerString = "Bearer "
    private var transformerIdKey = "transformerId"

    private var postTransformerUrl = "/transformers"
    private var getTransformersUrl = "/transformers"
    private var deleteTransformerUrl = "/transformers/"

    fun getToken(onTokenReceived: (String?) -> Unit) {
        var tokenReceived = ""
        val url = "$deviceBaseUrl/allspark"
        Rx2AndroidNetworking.get(url)
            .responseOnlyFromNetwork
            .build()
            .getAsString(object : StringRequestListener {
                override fun onResponse(response: String) {
                    tokenReceived = response
                    Log.d(TAG, "tokenreceived $tokenReceived")
                    onTokenReceived(tokenReceived)
                }

                override fun onError(error: ANError) {
                    Log.d(TAG, "Failed to get token ${error.message}}")
                }
            })
    }


    fun addTransformer(
        transformer: Transformer,
        onSuccess: (Transformer?) -> Unit,
        onError: (String?) -> Unit
    ) {
        val url = deviceBaseUrl + postTransformerUrl
        var job: Disposable? = null
        job = Rx2AndroidNetworking.post(url)
            .addJSONObjectBody(JSONObject(Gson().toJson(transformer)))
            .addHeaders(contentTypeKey, contentTypeValue)
            .addHeaders(authorizationKey, getBearerToken())
            .build()
            .getObjectObservable(Transformer::class.java)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                Log.w(TAG, "Add transformer $url success: $response")
                onSuccess(response)
                job?.dispose()
            }, { erorr ->
                Log.w(TAG, "Add transformer $url failed: $erorr")
                job?.dispose()
                onError(erorr.toString())
            })
    }

    fun updateTransformer(
        transformer: Transformer,
        onSuccess: (Transformer?) -> Unit,
        onError: (String?) -> Unit
    ) {
        val url = deviceBaseUrl + postTransformerUrl
        var job: Disposable? = null
        job = Rx2AndroidNetworking.put(url)
            .addJSONObjectBody(JSONObject(Gson().toJson(transformer)))
            .addHeaders(contentTypeKey, contentTypeValue)
            .addHeaders(authorizationKey, getBearerToken())
            .build()
            .getObjectObservable(Transformer::class.java)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                Log.w(TAG, "Update transformer $url success: $response")
                onSuccess(response)
                job?.dispose()
            }, { erorr ->
                Log.w(TAG, "Update transformer $url failed: $erorr")
                job?.dispose()
                onError(erorr.toString())
            })
    }

    fun getTransformersList(
        onSuccess: (TransformerList?) -> Unit,
        onError: (String?) -> Unit
    ) {
        val url = deviceBaseUrl + getTransformersUrl
        var job: Disposable? = null
        job = Rx2AndroidNetworking.get(url)
            .responseOnlyFromNetwork
            .addHeaders(contentTypeKey, contentTypeValue)
            .addHeaders(authorizationKey, getBearerToken())
            .build()
            .getObjectObservable(TransformerList::class.java)
            .subscribeOn(Schedulers.io())
            .subscribe({ response ->
                Log.d(TAG, "Get transformers success ${response.transformers}")
                onSuccess(response)
                job?.dispose()
            }, { error ->
                Log.d(TAG, "Failed to get transformers ${error.message}}")
                onError(error.message)
                job?.dispose()
            })
    }

    fun deleteTransformer(id: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        val url = deviceBaseUrl + deleteTransformerUrl + "$id"
        Rx2AndroidNetworking.delete(url)
            .responseOnlyFromNetwork
            .addHeaders(contentTypeKey, contentTypeValue)
            .addHeaders(authorizationKey, getBearerToken())
            .build().getAsOkHttpResponse(object : OkHttpResponseListener {
                override fun onResponse(response: Response?) {
                    Log.d(TAG, "Delete transformer success $response")
                    onSuccess()
                }

                override fun onError(error: ANError) {
                    Log.d(TAG, "Failed to delte transformer ${error.message}}")
                    onError(error.message ?: "")
                }
            })

    }

    private fun getBearerToken(): String {
        return bearerString + PreferenceHelper.getString(PreferenceHelper.AUTH_TOKEN)
    }


}