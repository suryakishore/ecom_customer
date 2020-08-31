package com.appscrip.stripe

import android.content.Context
import com.appscrip.stripe.Constants.CARD
import com.appscrip.stripe.Constants.DATA
import com.appscrip.stripe.Constants.DELETE_CARD
import com.appscrip.stripe.Constants.LANGUAGE
import com.appscrip.stripe.Constants.MESSAGE
import com.appscrip.stripe.Constants.STRIPE_ID
import com.appscrip.stripe.Constants.USER_ID
import com.stripe.android.model.CardBrand
import okhttp3.Call
import okhttp3.Callback
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

/*Contains all the methods related to users accounts like get, update and delete*/
object UserAccounts {
    private val httpClient = OkHttpClient().newBuilder()

    /**
     * returns all cards stored on file for user
     * @param language language of application
     * @param userId user id of user
     * @param accountsDelegate delegate for methods
     */
    fun getCards(context: Context, token: String, language: String, userId: String, accountsDelegate: AccountsDelegate) {
        // Create a SetupIntent by calling the sample server's /create-setup-intent endpoint.
        httpClient.sslSocketFactory(SSLCertificate.initSSL(context)!!.socketFactory,
            SSLCertificate.systemDefaultTrustManager())

        val urlBuilder: HttpUrl.Builder =
            (BuildConfig.STRIPE_SERVER + CARD).toHttpUrlOrNull()!!.newBuilder()
        urlBuilder.addQueryParameter(USER_ID, userId)
        val url = urlBuilder.build().toString()
        val request = Request.Builder()
            .addHeader(Constants.AUTHORIZATION, token)
            .addHeader(LANGUAGE, language)
            .url(url)
            .build()
        httpClient.build().newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.message?.let { accountsDelegate.onFailure(it) }
                }

                override fun onResponse(call: Call, response: Response) {
                    if (!response.isSuccessful) {
                        response.message.let { accountsDelegate.onFailure(it) }
                    } else {
                        val responseData = response.body?.string()
                        val json = JSONObject(responseData)
                        val cardObject = json.getJSONArray(DATA)
                        accountsDelegate.onSuccess(cardObject)
                    }
                }
            })
    }

    /**
     * deletes the particular card stored in user
     * @param language language of application
     * @param cardId card id stored
     * @param accountsDelegate delegate for methods
     */
    fun deleteCard(context: Context, token: String, language: String, cardId: String, accountsDelegate: AccountsDelegate) {
        // Create a SetupIntent by calling the sample server's /create-setup-intent endpoint.

        httpClient.sslSocketFactory(SSLCertificate.initSSL(context)!!.socketFactory,
            SSLCertificate.systemDefaultTrustManager())
        val urlBuilder: HttpUrl.Builder =
            (BuildConfig.STRIPE_SERVER + DELETE_CARD).toHttpUrlOrNull()!!.newBuilder()
        urlBuilder.addQueryParameter(STRIPE_ID, cardId)
        val url = urlBuilder.build().toString()
        val request = Request.Builder()
            .addHeader(Constants.AUTHORIZATION, token)
            .addHeader(LANGUAGE, language)
            .delete()
            .url(url)
            .build()
        httpClient.build().newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.message?.let { accountsDelegate.onFailure(it) }
                }

                override fun onResponse(call: Call, response: Response) {
                    if (!response.isSuccessful) {
                        response.message.let { accountsDelegate.onFailure(it) }
                    } else {
                        val responseData = response.body?.string()
                        val json = JSONObject(responseData)
                        val message = json.get(MESSAGE)
                        accountsDelegate.onSuccess(message)
                    }
                }
            })
    }

    /**
     * updated the particular card stored in user
     * @param language language of application
     * @param cardId card id stored
     * @param accountsDelegate delegate for methods
     */
    fun updateCard(
        language: String,
        userId: String,
        cardId: String,
        accountsDelegate: AccountsDelegate
    ) {
        // Create a SetupIntent by calling the sample server's /create-setup-intent endpoint.
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val jsonObject = JSONObject()
        jsonObject.put(USER_ID, userId)
        jsonObject.put(STRIPE_ID, cardId)
        val body = jsonObject.toString().toRequestBody(mediaType)
        val request = language.let {
            Request.Builder()
                .url(BuildConfig.STRIPE_SERVER + CARD)
                .addHeader(LANGUAGE, it)
                .patch(body)
                .build()
        }

        httpClient.build().newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.message?.let { accountsDelegate.onFailure(it) }
                }

                override fun onResponse(call: Call, response: Response) {
                    if (!response.isSuccessful) {
                        response.message.let { accountsDelegate.onFailure(it) }
                    } else {
                        val responseData = response.body?.string()
                        val json = JSONObject(responseData)
                        val message = json.get(MESSAGE)
                        accountsDelegate.onSuccess(message)
                    }
                }
            })
    }

    fun getBrandLogo(brandName: String): Int {
        val list = CardBrand.values()
        for (i in 0..CardBrand.values().size - 1) {
            val brand = list.get(i)
            if (brand.code.toLowerCase().equals(brandName.toLowerCase())) {
                return CardBrand.valueOf(brand.name).icon
            }
        }
        return 0
    }
}