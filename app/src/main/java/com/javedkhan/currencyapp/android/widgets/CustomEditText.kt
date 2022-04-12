package com.javedkhan.currencyapp.android.widgets

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet

import androidx.appcompat.widget.AppCompatEditText
import com.javedkhan.currencyapp.android.R
import com.javedkhan.currencyapp.android.util.Constant
import com.project.jewelmart.royalchains.utils.FontCache


class CustomEditText : AppCompatEditText {
    private var typefaceType: Int = 0

    constructor(context: Context) : super(context) {
        applyCustomFont(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setTextAppearance(context, R.style.DefaultTextColor)
        applyCustomFont(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        applyCustomFont(context, attrs)
    }

    /**
     * @param context
     * @param attrs
     * @Author Javed Khan
     * This method is for applying font for EditText, by default font will select 0th position
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

        when (type) {
            Constant.KEY_U_BOLD -> return FontCache.getTypeface(Constant.U_BOLD, context)

            Constant.KEY_U_Medium -> return FontCache.getTypeface(Constant.U_Medium, context)

            Constant.KEY_U_Light -> return FontCache.getTypeface(Constant.U_Light, context)

            else -> return FontCache.getTypeface(Constant.U_Medium, context)
        }
    }

}
