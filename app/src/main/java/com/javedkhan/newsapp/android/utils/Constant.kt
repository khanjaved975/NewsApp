package com.javedkhan.newsapp.android.utils

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.provider.Settings
import android.util.DisplayMetrics
import android.view.Window
import androidx.fragment.app.FragmentActivity
import com.javedkhan.newsapp.android.R

object Constant {

    /* ---- Font Related Constants -- */
   /* var U_BOLD = "OpenSans-Bold.ttf"
    var U_Medium = "OpenSans-Regular.ttf"
    var U_Light = "OpenSans-Light.ttf"
    var U_Light_Italic = "OpenSans-Italic.ttf"
*/
    // Retrofit file cache name
    var retrofitCacheFile = "NewsAppCacheFile"

    const val OK = "OK"
   /* const val KEY_API_SUCESS_CODE = "200"
    const val KEY_API_ERROR_401 = "401"
    const val KEY_API_ERROR_422 = "422"
    const val KEY_API_ERROR_500 = "500"*/


    @SuppressLint("all")
    fun getDeviceId(context: Context): String {
        return Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )
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

    fun progressDialog(context: FragmentActivity?): ProgressDialog? {

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



}