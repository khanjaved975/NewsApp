package com.javedkhan.newsapp.android.utils

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.provider.Settings
import android.util.DisplayMetrics
import android.util.Patterns
import android.view.Window
import androidx.fragment.app.FragmentActivity
import com.javedkhan.newsapp.android.R

object Constant {

    /* ---- Font Related Constants -- */
    var U_BOLD = "OpenSans-Bold.ttf"
    var U_Medium = "OpenSans-Regular.ttf"
    var U_Light = "OpenSans-Light.ttf"
    var U_Light_Italic = "OpenSans-Italic.ttf"
    const val KEY_U_BOLD = 1
    const val KEY_U_Medium = 2
    const val KEY_U_Light = 3
    const val KEY_U_Light_Italic = 4

    // Retrofit file cache name
    var retrofitCacheFile = "NewsAppCacheFile"

    const val OK = "OK"
    const val KEY_API_SUCESS_CODE = "200"
    const val KEY_API_ERROR_401 = "401"
    const val KEY_API_ERROR_422 = "422"
    const val KEY_API_ERROR_500 = "500"


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