package dev.jolken.giphyer.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import dev.jolken.giphyer.models.responses.BaseGiphyResponse
import retrofit2.Response

fun <T> Response<T>.toKotlinError(): Error {
    when (val body = this.body()) {
        null -> return Error("Empty response body")
        is BaseGiphyResponse -> return Error(body.meta.message)
    }
    return Error("Unexpected error")
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.pxToDp(px: Int = 1): Int {
    return (px * resources.displayMetrics.density).toInt()
}