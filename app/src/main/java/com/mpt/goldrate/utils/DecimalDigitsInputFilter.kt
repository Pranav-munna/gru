package com.mpt.goldrate.utils

import android.text.InputFilter
import android.text.Spanned
import java.util.regex.Matcher
import java.util.regex.Pattern


/**
 * Created by Pranav Ashok on 13-06-2022.
 */
class DecimalDigitsInputFilter :
    InputFilter {
    var mPattern: Pattern = Pattern.compile("[0-9]+((\\.[0-9]{0,1})?)||(\\.)?")
    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        val matcher: Matcher = mPattern.matcher(dest)
        return if (!matcher.matches()) "" else null
    }

}
