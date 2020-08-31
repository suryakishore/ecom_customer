package com.appscrip.stripe

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.appscrip.stripe.Constants.AUTHORIZATION
import com.appscrip.stripe.Constants.CARD
import com.appscrip.stripe.Constants.CLIENT_KEY
import com.appscrip.stripe.Constants.DATA
import com.appscrip.stripe.Constants.ENGLISH
import com.appscrip.stripe.Constants.LANGUAGE
import com.appscrip.stripe.Constants.PUBLIC_KEY
import com.appscrip.stripe.Constants.SET_UP_INTENT
import com.appscrip.stripe.Constants.STRIPE_ID
import com.appscrip.stripe.Constants.USER_ID
import com.stripe.android.ApiResultCallback
import com.stripe.android.SetupIntentResult
import com.stripe.android.Stripe
import com.stripe.android.model.ConfirmSetupIntentParams
import com.stripe.android.model.PaymentMethod
import com.stripe.android.model.PaymentMethodCreateParams
import com.stripe.android.model.StripeIntent
import com.stripe.android.view.CardInputWidget
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.lang.ref.WeakReference

/*Adds the card on file which user enters into the card*/
class StripePaymentIntentActivity : AppCompatActivity() {

    /**
     * To run this app, you'll need to first run the sample server locally.
     * Follow the "How to run locally" instructions in the root directory's README.md to get started.
     * Once you've started the server, open http://localhost:4242 in your browser to check that the
     * server is running locally.
     * After verifying the sample server is running locally, build and run the app using the
     * Android emulator.
     */
    private val httpClient = OkHttpClient().newBuilder()
    private lateinit var setupIntentClientSecret: String
    private lateinit var stripe: Stripe
    private lateinit var progressBar: ProgressBar
    private var userId: String? = null
    private var language: String? = null
    private var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stripe)
        language = intent.getStringExtra(LANGUAGE)
        userId = intent.getStringExtra(USER_ID)
        token = intent.getStringExtra(AUTHORIZATION)

        if (language == null) {
            language = ENGLISH
        }
        if (userId == null) {
            userId = "5ca5dd046f3689c676b3ca6a"
        }
        loadStripePage()
    }

    /**
     * loads the stripe add card page
     */
    private fun loadStripePage() {
        // Create a SetupIntent by calling the sample server's /create-setup-intent endpoint.
        val request = language?.let {
            Request.Builder()
                .url(BuildConfig.STRIPE_SERVER + SET_UP_INTENT)
                .addHeader(AUTHORIZATION, token)
                .addHeader(LANGUAGE, it)
                .get()
                .build()
        }

        httpClient.sslSocketFactory(SSLCertificate.initSSL(this)!!.socketFactory,
            SSLCertificate.systemDefaultTrustManager())

        request?.let {
            httpClient.build().newCall(it)
                .enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        runOnUiThread {
                            Toast.makeText(applicationContext, "Error: $e", Toast.LENGTH_LONG)
                                .show()
                            print("$TAG setup intent fail error $e")
                        }
                    }

                    override fun onResponse(call: Call, response: Response) {
                        if (!response.isSuccessful) {
                            runOnUiThread {
                                Toast.makeText(
                                    applicationContext,
                                    "${getString(R.string.addCardSetupError)} $response",
                                    Toast.LENGTH_LONG
                                ).show()
                                print("$TAG setup intent response error ${response.message}")
                            }
                        } else {
                            val responseData = response.body?.string()
                            val json = JSONObject(responseData)
                            print("$TAG setup intent response success $json")
                            // The response from the server includes the Stripe publishable key and
                            // SetupIntent details.
                            val dataObject = json.getJSONObject(DATA)
                            val stripePublishableKey = dataObject.getString(PUBLIC_KEY)
                            setupIntentClientSecret = dataObject.getString(CLIENT_KEY)
                            Log.d("exe", "setupIntentClientSecret" + setupIntentClientSecret)
                            // Use the publishable key from the server to initialize the Stripe instance.
                            stripe = Stripe(applicationContext, stripePublishableKey)
                        }
                    }
                })
        }
        progressBar = findViewById(R.id.progressBar)
        val ivBack: AppCompatImageView = findViewById(R.id.ivBack)
        ivBack.setOnClickListener {
            finish()
        }

        // Hook up the pay button to the card widget and stripe instance
        val payButton: Button = findViewById(R.id.addCardPayButton)
        payButton.setOnClickListener {

            // Collect card details
            val cardInputWidget =
                findViewById<CardInputWidget>(R.id.addCardDetails)
            val paymentMethodCard = cardInputWidget.paymentMethodCard

            // Later, you will need to attach the PaymentMethod to the Customer it belongs to.
            // This example collects the customer's email to know which customer the PaymentMethod belongs to, but your app might use an account id, session cookie, etc.
            val billingDetails = PaymentMethod.BillingDetails.Builder()
                .build()

            // Create SetupIntent confirm parameters with the above
            Log.d("exe", "isEnabled" + cardInputWidget.isEnabled + "isEnabled" + cardInputWidget.toString())
            //   if (cardInputWidget.isEnabled) {
            progressBar.visibility = VISIBLE
            if (paymentMethodCard != null) {
                val paymentMethodParams = PaymentMethodCreateParams
                    .create(paymentMethodCard, billingDetails, null)
                val confirmParams = ConfirmSetupIntentParams
                    .create(paymentMethodParams, setupIntentClientSecret)
                stripe.confirmSetupIntent(this, confirmParams)
            }
            /*}
            else{
                Toast.makeText(applicationContext, "${resources.getString(R.string.inputWarning)}", Toast.LENGTH_SHORT)
                        .show()
            }*/
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val weakActivity = WeakReference<Activity>(this)

        // Handle the result of stripe.confirmSetupIntent
        stripe.onSetupResult(requestCode, data, object : ApiResultCallback<SetupIntentResult> {
            override fun onSuccess(result: SetupIntentResult) {
                val setupIntent = result.intent
                val status = setupIntent.status
                if (status == StripeIntent.Status.Succeeded) {
                    // Setup completed successfully
                    runOnUiThread {

                        if (weakActivity.get() != null) {
                            print("$TAG payment id response success ${setupIntent.paymentMethodId}")
                            userId?.let {
                                postPaymentId(
                                    setupIntent.paymentMethodId.toString(),
                                    it
                                )
                            }
                        }
                    }
                } else if (status == StripeIntent.Status.RequiresPaymentMethod) {
                    // Setup failed – allow retrying using a different payment method
                    runOnUiThread {
                        if (weakActivity.get() != null) {
                            val activity = weakActivity.get()!!
                            val builder = AlertDialog.Builder(activity)
                            builder.setTitle(getString(R.string.addCardSetupFailed))
                            builder.setMessage(setupIntent.lastSetupError!!.message)
                            builder.setPositiveButton(getString(R.string.addCardStripeOk)) { _, _ ->
                                val cardInputWidget =
                                    findViewById<CardInputWidget>(R.id.addCardDetails)
                                cardInputWidget.clear()
                            }
                            val dialog = builder.create()
                            dialog.show()
                        }
                    }
                }
            }

            override fun onError(e: Exception) {
                // Setup request failed – allow retrying using the same payment method
                runOnUiThread {
                    if (weakActivity.get() != null) {
                        val activity = weakActivity.get()!!
                        val builder = AlertDialog.Builder(activity)
                        builder.setMessage(e.toString())
                        builder.setPositiveButton(getString(R.string.addCardStripeOk), null)
                        val dialog = builder.create()
                        dialog.show()
                    }
                }
            }
        })
    }

    /**
     * posts the payment id from stripe to backend
     * @param paymentId id returned from stripe
     */
    private fun postPaymentId(paymentId: String, userId: String) {
        // Create a SetupIntent by calling the sample server's /create-setup-intent endpoint.
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val jsonObject = JSONObject()
        jsonObject.put(USER_ID, userId)
        jsonObject.put(STRIPE_ID, paymentId)

        val body = jsonObject.toString().toRequestBody(mediaType)
        val request = language?.let {
            Request.Builder()
                .url(BuildConfig.STRIPE_SERVER + CARD)
                .addHeader(LANGUAGE, it)
                .addHeader(AUTHORIZATION, token)
                .post(body)
                .build()
        }
        request?.let {
            httpClient.build().newCall(it)
                .enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {

                        runOnUiThread {
                            progressBar.visibility = GONE

                            Toast.makeText(applicationContext, "Error: $e", Toast.LENGTH_LONG)
                                .show()
                            print("$TAG post payment fail error $e")
                        }
                    }

                    override fun onResponse(call: Call, response: Response) {

                        if (!response.isSuccessful) {
                            runOnUiThread {
                                progressBar.visibility = GONE

                                Toast.makeText(
                                    applicationContext,
                                    "Error: $response",
                                    Toast.LENGTH_LONG
                                ).show()
                                print("$TAG post payment response error ${response.message}")
                            }
                        } else {
                            val responseData = response.body?.string()
                            val json = JSONObject(responseData)
                            Log.d("exe", "json" + json.toString())
                            runOnUiThread {
                                progressBar.visibility = GONE
                                print("$TAG post payment response success $json")
                                Toast.makeText(
                                    applicationContext,
                                    "Card added successfully.",
                                    Toast.LENGTH_LONG
                                ).show()

                                var intent = Intent()
                                intent.putExtra(CARD, json.toString())
                                setResult(Activity.RESULT_OK, intent)
                                finish()
                            }
                        }
                    }
                })
        }
    }

    companion object {
        var TAG = StripePaymentIntentActivity::class.java
    }
}
