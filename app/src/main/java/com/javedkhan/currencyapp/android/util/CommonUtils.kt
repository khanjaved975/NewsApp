package com.javedkhan.currencyapp.android.util

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.provider.Settings
import android.util.DisplayMetrics
import android.util.Patterns
import android.view.Window
import androidx.fragment.app.FragmentActivity
import com.javedkhan.currencyapp.android.R


object CommonUtils {

    var selectedOption: String? = null
    var storeSepticId: Int? = null


    @SuppressLint("all")
    fun getDeviceId(context: Context): String {
        return Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }

    fun isEmailValid(email: String?): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String?): Boolean {
        if (!isNullOrEmpty(password)) {
            if (password!!.length > 4) {
                return true
            }
        }
        return false
    }

    fun isNullOrEmpty(str: String?): Boolean {
        if (str != null && !str.isEmpty())
            return false
        return true
    }

    fun getDeviceBrand(): String {
        return Build.BRAND
    }

    fun alertDialogWithCallBack(
        context: Context,
        title: String,
        message: String,
        possitiveBtn: String,
        negativeBtn: String,
        callBack: GenericCallBack
    ) {
        val alertDialog = AlertDialog.Builder(context).create()
        alertDialog.setMessage(message)
        alertDialog.setTitle(title)
        alertDialog.setButton(Dialog.BUTTON_POSITIVE, possitiveBtn) { dialog, which ->
            alertDialog.dismiss()
            callBack.invoke(true)
        }

        alertDialog.setButton(Dialog.BUTTON_NEGATIVE, negativeBtn) { dialog, which ->
            alertDialog.dismiss()
            callBack.invoke(false)
        }
        alertDialog.show()
    }

    fun ShowLoader(context: FragmentActivity?): ProgressDialog? {

        val displaymetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay
            .getMetrics(displaymetrics)
        val height = displaymetrics.heightPixels
        val width = displaymetrics.widthPixels
        var pdg1: ProgressDialog? = null
        context.runOnUiThread {
            val pdg = ProgressDialog(context)
            pdg1 = pdg
            pdg.setMessage("please wait...")
            pdg.requestWindowFeature(Window.FEATURE_NO_TITLE)
            pdg.show()
            val window = pdg.window
            window?.setLayout(width / 3, width / 3)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window?.setContentView(R.layout.progress_dialog)

            pdg.setCancelable(false)
        }

        return pdg1
    }

    fun isMyServiceRunning(serviceClass: Class<*>, activity: Activity): Boolean {
        val manager = activity.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager?
        for (service in manager!!.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }


}