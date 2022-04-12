package com.javedkhan.currencyapp.android.widgets

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.AttributeSet

import androidx.appcompat.widget.AppCompatButton
import com.javedkhan.currencyapp.android.R
import com.javedkhan.currencyapp.android.util.Constant
import com.project.jewelmart.royalchains.utils.FontCache

class CustomButton : AppCompatButton {
    private var typefaceType: Int = 0

    override fun setBackgroundDrawable(background: Drawable?) {
        super.setBackgroundDrawable(background)
    }

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)

    }

    private fun init(context: Context, attrs: AttributeSet?){
        if (!isInEditMode) {
            applyCustomFont(context, attrs)
        }
    }

    /**
     * @param context
     * @param attrs
     * @Author Javed Khan
     * This method is for applying font for Button, by default font will select 0th position
     */

    private fun applyCustomFont(context: Context, attrs: AttributeSet?) {


        val array = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.CustomTextView,
                0, 0)
        try {
            typefaceType = array.getInteger(R.styleable.CustomTextView_font_name, 0)
        } finally {
            array.recycle()
        }
        if (!isInEditMode) {
            typeface = getTypeFace(typefaceType)
        }

    }

    fun getTypeFace(type: Int): Typeface? {
        return FontCache.getTypeface(Constant.U_BOLD, context)
    }

    companion object {
        private val TAG = CustomButton::class.java.simpleName
    }


}
