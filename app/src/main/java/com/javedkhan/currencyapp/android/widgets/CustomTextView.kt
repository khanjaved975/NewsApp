package com.javedkhan.currencyapp.android.widgets

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.javedkhan.currencyapp.android.R
import com.project.jewelmart.royalchains.utils.FontCache
import com.javedkhan.currencyapp.android.util.Constant

/**
 * This class is for applying font & theme for Text View
 */
class CustomTextView : AppCompatTextView {
    private var typefaceType: Int = 0

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
       // setTextAppearance(context, R.style.DefaultTextColor)
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(context, attrs)
    }

    constructor(context: Context) : super(context) {
        init(context, null)

    }

    /**
     * @param context
     * @param attrs
     * @Author Javed Khan
     * This method is for applying font, by default font will select 0th position
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
        typeface = getTypeFace(typefaceType)

    }

    fun getTypeFace(type: Int): Typeface? {

        when (type) {

            Constant.KEY_U_BOLD -> return FontCache.getTypeface(Constant.U_BOLD, context)

            Constant.KEY_U_Medium -> return FontCache.getTypeface(Constant.U_Medium, context)

            Constant.KEY_U_Light -> return FontCache.getTypeface(Constant.U_Light, context)

            Constant.KEY_U_Light_Italic -> return FontCache.getTypeface(Constant.U_Light_Italic, context)

            else -> return FontCache.getTypeface(Constant.U_Medium, context)
        }
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        if (!isInEditMode) {
            applyCustomFont(context, attrs)
        }
    }

    companion object {
        private val TAG = CustomTextView::class.java.simpleName
    }

}
