package com.javedkhan.newsapp.android.view.base

import android.annotation.TargetApi
import android.app.ProgressDialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.javedkhan.newsapp.android.R
import com.javedkhan.newsapp.android.utils.Constant

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel<*>> : AppCompatActivity(), BaseFragment.Callback {
    /**
     * this probably depend on isLoadcan ing variable of BaseViewModel,
     * its going to be common for all the activities
     */
    //val sharedPreferencesManager: SharedPreferencesManager by inject()
    private var mProgressDialog: ProgressDialog? = null
    var viewDataBinding: T? = null
    private var mViewModel: V? = null

    /**
     * Override for set binding variable
     *
     * @return variable idbindingVariable
     */
    abstract val bindingVariable: Int

    /**
     * @return layout resource id
     */
    @get:LayoutRes
    abstract val layoutId: Int
    private var progressDialog: ProgressDialog? = null
    //val db = MyApplication.appContext?.let { BiharSurveyAppDB.getDatabase(it) }
    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract val viewModel: V


    override fun onFragmentAttached() {

    }

    override fun onFragmentDetached(tag: String) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun showKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    fun openActivityOnTokenExpire() {
        //startActivity(LoginActivity(this));
        finish()
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode)
        }
    }

    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, layoutId)
        this.mViewModel = if (mViewModel == null) viewModel else mViewModel
        viewDataBinding!!.setVariable(bindingVariable, mViewModel)
        viewDataBinding!!.executePendingBindings()
    }

    open fun showAlert(title: String?, msg: String?) {
        try {
            val dialogBuilder =
                AlertDialog.Builder(this)
            val inflater = this.layoutInflater
            val alertDialog: View =
                inflater.inflate(R.layout.alertdialogroundedlayout, null)
            dialogBuilder.setView(alertDialog)
            val textMessage =
                alertDialog.findViewById<TextView>(R.id.txt_message)
            val textTitle =
                alertDialog.findViewById<TextView>(R.id.txt_title)
            val imageClose =
                alertDialog.findViewById<ImageView>(R.id.img_close)
            val buttonPositive =
                alertDialog.findViewById<Button>(R.id.txt_positive)
            dialogBuilder.setCancelable(false)
            textMessage.text = msg
            textTitle.text = title
            val dialog = dialogBuilder.create()
            buttonPositive.setOnClickListener { v: View? -> dialog.dismiss() }
            imageClose.setOnClickListener { v: View? -> dialog.dismiss() }
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            val wmlp = dialog.window?.attributes
            wmlp?.gravity ?:  Gravity.CENTER
            //y position*/
            dialog.show()
            dialog.window
                ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    open fun showProgressLoading(message: String?) {
        /*if (progressDialog == null || !progressDialog.isShowing()) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(message);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }*/
        progressDialog = Constant.progressDialog(this@BaseActivity)

    }




    open fun hideProgressDialog() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
        }
        progressDialog = null
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            val fm = supportFragmentManager
            if (supportFragmentManager.backStackEntryCount == 1) finish() else {
                fm.popBackStackImmediate()
            }
        } else {
            super.onBackPressed()
        }
    }


}

