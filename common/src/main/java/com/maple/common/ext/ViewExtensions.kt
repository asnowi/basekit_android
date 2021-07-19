package com.maple.common.ext

import android.app.Service
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.maple.baselib.app.BaseApp

fun View.showKeyboard() {
    (BaseApp.instance.getSystemService(Service.INPUT_METHOD_SERVICE) as? InputMethodManager)
            ?.showSoftInput(this, 0)
}

fun View.hideKeyboard() {
    (BaseApp.instance.getSystemService(Service.INPUT_METHOD_SERVICE) as? InputMethodManager)
            ?.hideSoftInputFromWindow(this.windowToken, 0)
}

fun View.toVisible() {
    if (this.visibility == View.GONE) {
        this.visibility = View.VISIBLE
    }
}

fun View.toGone() {
    if (this.visibility == View.VISIBLE) {
        this.visibility = View.GONE
    }
}


fun EditText.afterTextChanged(action: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            action(s.toString())
        }
    })
}
