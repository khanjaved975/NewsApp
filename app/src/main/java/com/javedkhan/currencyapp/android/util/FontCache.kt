package com.project.jewelmart.royalchains.utils

import android.content.Context
import android.graphics.Typeface
import androidx.collection.ArrayMap


/**
 * An cache for prevent accessing font assets every time.
 */
object FontCache {

    private val fontCache = ArrayMap<String, Typeface>()

    /**
     * Method for returning font typeface either from cache or directly from assets.
     *
     * @param fontName Name of the font
     * @param context View context
     * @return font typeface
     */
    fun getTypeface(fontName: String, context: Context): Typeface? {
        var typeface = fontCache[fontName]

        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.assets, fontName)
            } catch (e: Exception) {
                return null
            }

            fontCache[fontName] = typeface
        }

        return typeface
    }

}
