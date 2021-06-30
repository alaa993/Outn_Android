package com.alaan.outn.activity

import ModelProfile
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.*
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import com.alaan.outn.R
import com.alaan.outn.api.Repository
import com.alaan.outn.model.Api
import com.alaan.outn.model.Token
import com.alaan.outn.utils.Preference
import com.alaan.outn.utils.Utils
import com.alaan.outn.utils.Utils.setFillWindowAndTransparetStatusBar
import com.chaos.view.PinView
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.gson.Gson
import com.hbb20.CountryCodePicker
import com.shuhart.stepview.StepView
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit
import com.alaan.outn.api.interfaces.CallBack as CallBack1

class LoginActivity : AppCompatActivity(){


    private lateinit var auth: FirebaseAuth

    private var verificationInProgress = false
    private var statusVerify = false
    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    // val progressBar = CustomProgressBar()

    private var currentStep = 0
    var layout1: LinearLayout? = null
    var layout2:android.widget.LinearLayout? = null
    var layout3:android.widget.LinearLayout? = null
   // var stepView: StepView? = null
    var dialog_verifying: AlertDialog? = null
    var profile_dialog:android.app.AlertDialog? = null

    private val uniqueIdentifier: String? = null
    private val UNIQUE_ID = "UNIQUE_ID"
    private val ONE_HOUR_MILLI = 60 * 60 * 1000.toLong()

    private val TAG = "FirebasePhoneNumAuth"

    private var mCallbacks: OnVerificationStateChangedCallbacks? = null
    private var firebaseAuth: FirebaseAuth? = null

    private val phoneNumber: String? = null
    private var sendCodeButton: Button? = null
    private var verifyCodeButton: Button? = null
    private val signOutButton: Button? = null
    private var button3: Button? = null

    private var phoneNum: EditText? = null
    private var verifyCodeET: PinView? = null
    private var phonenumberText: TextView? = null
    private  var get_code:android.widget.TextView? = null

    private var mVerificationId: String? = null
    private var mResendToken: ForceResendingToken? = null
    private var countryCodePicker: CountryCodePicker? = null


    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFillWindowAndTransparetStatusBar(this)
        setContentView(R.layout.activity_login)
        Utils.changeLanuge(this)
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState)
        }

        mAuth = FirebaseAuth.getInstance()

        layout1 = findViewById(R.id.layout1)
        layout2 = findViewById(R.id.layout2)
        sendCodeButton = findViewById(R.id.submit1)
        verifyCodeButton = findViewById(R.id.submit2)
        firebaseAuth = FirebaseAuth.getInstance()
        phoneNum = findViewById(R.id.phonenumber)
        get_code = findViewById(R.id.get_code)
        verifyCodeET = findViewById(R.id.pinView)
        phonenumberText = findViewById(R.id.phonenumberText)
        countryCodePicker = findViewById(R.id.countryCodePicker)


     //   stepView = findViewById(R.id.step_view)
       // stepView?.setStepsNumber(3)
        //stepView?.go(0, true)
        layout1?.visibility = View.VISIBLE

        sendCodeButton?.setOnClickListener(View.OnClickListener {
            val phoneNumber: String = phoneNum?.getText().toString()
            phonenumberText?.text = "+" + countryCodePicker?.selectedCountryCode + phoneNumber
            if (TextUtils.isEmpty(phoneNumber)) {
                phoneNum?.setError("Enter a Phone Number")
                phoneNum?.requestFocus()
            } else {
//                if (currentStep < stepView?.getStepCount()!! - 1) {
//                    currentStep++
//                    stepView?.go(currentStep, true)
//                } else {
//                    stepView?.done(true)
//                }
                layout1?.visibility = View.GONE
                layout2?.visibility = View.VISIBLE
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        phonenumberText?.text.toString(),  // Phone number to verify
                        60,  // Timeout duration
                        TimeUnit.SECONDS,  // Unit of timeout
                        this,  // Activity (for callback binding)
                        mCallbacks!!) // OnVerificationStateChangedCallbacks
            }
        })

        mCallbacks = object : OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
               showLoading()
                signInWithPhoneAuthCredential(phoneAuthCredential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(this@LoginActivity,"Please try again", LENGTH_SHORT).show()
            }

            override fun onCodeSent(verificationId: String,
                                    token: ForceResendingToken) {

               Preference.tokenFierbase = verificationId
                mVerificationId = verificationId
                mResendToken = token

                // ...
            }
        }

        verifyCodeButton?.setOnClickListener(View.OnClickListener {
            val verificationCode: String = verifyCodeET?.getText().toString()
            if (verificationCode.isEmpty()) {
                Toast.makeText(this, "Enter verification code", LENGTH_SHORT).show()
            } else {
                if (Preference.tokenFierbase.equals("")){
                    currentStep = 0
                   // stepView?.go(currentStep, true)
                    layout1?.visibility = View.VISIBLE
                    layout2?.visibility = View.GONE

                }else{
                    showLoading()
                    val credential = PhoneAuthProvider.getCredential(Preference.tokenFierbase!!, verificationCode)
                    signInWithPhoneAuthCredential(credential)
                }


            }
        })


        get_code?.setOnClickListener {
            currentStep = 0
            layout1?.visibility = View.VISIBLE
            layout2?.visibility = View.GONE
        }

        val spannable = SpannableString("outn")
        spannable.setSpan(
                ForegroundColorSpan(Color.rgb(244, 0, 12)),
                3,
                4,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        title_login.text = spannable
        brand_lay1.text = spannable
        if (phoneNumber != null) {
            checkLogin(phoneNumber)
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth!!.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                       hideLoading()
                        Preference.tokenFierbase = ""
                        val a = task.result!!.user!!.phoneNumber
                        a?.let { checkLogin(it) }
                    } else {
                       hideLoading()
                        Preference.tokenFierbase = ""
                        Toast.makeText(this, "Something wrong", LENGTH_SHORT).show()
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        }
                    }
                }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_VERIFY_IN_PROGRESS, verificationInProgress)
        outState.putBoolean(KEY_VERIFY_statusVerify, statusVerify)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        verificationInProgress = savedInstanceState.getBoolean(KEY_VERIFY_IN_PROGRESS)
        statusVerify = savedInstanceState.getBoolean(KEY_VERIFY_statusVerify)
    }



    private fun checkLogin(phoneNumber:String) {
        showLoading()


        Repository().getInstance()!!.getLogin(phoneNumber, object : CallBack1<Api<Token>>() {

            override fun onSuccess(t: Api<Token>) {
                super.onSuccess(t)
                hideLoading()
                if (t.data?.token != null) {
                    Preference.token = t.data?.token
                    getProfile()
                }

            }

            override fun onFail(e: Exception, code: Int) {
                hideLoading()
                if (code == 404) {

                    val g = Gson()
                    val b = g.fromJson(e.message.toString(), Api::class.java)
                    if (b.status!!.code == 404) {
                        intent = Intent(this@LoginActivity, RejesterActivity::class.java);
                        intent.putExtra("mobile", phoneNumber)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, e.message, LENGTH_SHORT).show()
                    }
                } else if (code == 100) {
                    Toast.makeText(this@LoginActivity, R.string.try_agin, LENGTH_SHORT).show()

                }

            }

        })
    }


    fun getProfile() {

        showLoading()
        Repository().getInstance()?.getProfile(object : CallBack1<Api<ModelProfile>>() {

            override fun onSuccess(t: Api<ModelProfile>) {
                super.onSuccess(t)
                hideLoading()
                if (t.data?.id != null) {

                    Preference.idUser = t.data?.id?.toString()
                    Preference.email = t.data?.email
                    Preference.image = t.data?.avatar
                    Preference.firstName = t.data?.fname
                    Preference.lastName = t.data?.lname
                    Preference.phone = t.data?.mobile
                    Preference.isLogin = true
                    Preference.business_name = t.data?.business_name
                    Preference.real_state_image = t.data?.real_state_image
                    finish()

                } else {

                    Toast.makeText(this@LoginActivity, resources.getString(R.string.try_agin), Toast.LENGTH_LONG).show()
                }
            }

            override fun onFail(e: Exception, code: Int) {
                super.onFail(e, code)
                hideLoading()

                Toast.makeText(this@LoginActivity, R.string.try_agin, Toast.LENGTH_LONG).show()
            }


        })

    }

    var progressDialog: ProgressDialog? = null

    private fun showLoading() {
        progressDialog = ProgressDialog(this)
        progressDialog!!.setCancelable(false)
        progressDialog!!.setMessage(getString(R.string.pls_wait))
        progressDialog!!.show()
    }

    private fun hideLoading() {
        if (progressDialog != null) {
            if (progressDialog!!.isShowing) {
                progressDialog!!.dismiss()
            }
        }
    }


    companion object {
        private const val TAG = "PhoneAuthActivity"
        private const val KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress"
        private const val KEY_VERIFY_statusVerify = "KEY_VERIFY_statusVerify"
    }

}



