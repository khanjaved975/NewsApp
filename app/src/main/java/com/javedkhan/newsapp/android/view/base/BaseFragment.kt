package com.javedkhan.newsapp.android.view.base

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.javedkhan.newsapp.android.R
import com.javedkhan.newsapp.android.utils.Constant


abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel<*>> : Fragment() {

    //val sharedPreferencesManager: SharedPreferencesManager by inject()
    private var baseActivity: BaseActivity<*, *>? = null
    private var mRootView: View? = null
    var viewDataBinding: T? = null
        private set
    private var mViewModel: V? = null
    private var progressDialog: ProgressDialog? = null

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    abstract val bindingVariable: Int

    /**
     * @return layout resource id
     */
    @get:LayoutRes
    abstract val layoutId: Int

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract val viewModel: V
    lateinit var navController: NavController

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            this.baseActivity = context
            context.onFragmentAttached()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        mViewModel = viewModel
        setHasOptionsMenu(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        mRootView = viewDataBinding!!.root
        return mRootView
    }

    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding!!.setVariable(bindingVariable, mViewModel)
        viewDataBinding!!.lifecycleOwner = this
        viewDataBinding!!.executePendingBindings()
        navController = Navigation.findNavController(view)
    }

    fun hideKeyboard() {
        if (baseActivity != null) {
            baseActivity!!.hideKeyboard()
        }
    }

    fun showKeyboard() {
        if (baseActivity != null) {
            baseActivity!!.showKeyboard()
        }
    }

    interface Callback {
        fun onFragmentAttached()
        fun onFragmentDetached(tag: String)
    }

    open fun showAlert(
        title: String?,
        msg: String?,
        ctx: Activity
    ) {
        try {
            val dialogBuilder =
                AlertDialog.Builder(ctx)
            val inflater = ctx.layoutInflater
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
            wmlp?.gravity = Gravity.CENTER
            //y position*/
            dialog.show()
            dialog.window
                ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    open fun showProgressLoading(message: String?) {

        progressDialog = Constant.progressDialog(activity)
    }

    open fun hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog!!.dismiss()
        }
        progressDialog = null
    }


    fun getVersionCode(): Int {
        /*val pInfo : PackageInfo = activity?.packageManager!!.getPackageInfo(activity!!.packageName, 0)
        return pInfo.versionCode*/
        return 3
    }


}
