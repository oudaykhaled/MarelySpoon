package com.ouday.marelyspoon.core.utils

import android.os.Build
import android.text.Html
import android.widget.TextView
import java.util.regex.Pattern

fun TextView.applyTextFormatter(){

    var str = text.toString()

    str = replaceAllTags(str, "\\*", "b")
    str = replaceAllTags(str, "__", "u")

    text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(str, Html.FROM_HTML_MODE_COMPACT);
    } else {
        Html.fromHtml(str)
    }

}

fun replaceAllTags(text: String, regExStr: String, tag: String): String {
    var txt = text.toString()
    while (hasTag(txt, regExStr)){
        txt = replace(txt, regExStr, tag)
    }
    return txt
}

fun replace(text: String, delimiter: String, tag: String): String {
    val pattern = Pattern.compile("$delimiter(?s)(.*)$delimiter")
    val matcher = pattern.matcher(text)
    var formattedSubString = "$delimiter(?s)(.*)$delimiter".toRegex().find(text)?.groups?.get(1)?.value
    formattedSubString = "<$tag>$formattedSubString</$tag>"
    return matcher.replaceFirst(formattedSubString)
}

fun hasTag(text: String, delimiter: String): Boolean {
    val pattern = Pattern.compile("$delimiter(?s)(.*)$delimiter")
    val matcher = pattern.matcher(text)
    return matcher.find()
}