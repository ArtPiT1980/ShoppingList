package com.artpit.android.shoppinglist.presentation

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.artpit.android.shoppinglist.R
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("errorInputName")
fun bindErrorInputName(view: TextInputLayout, value: Boolean) {
    val message = if (value) {
        view.context.getString(R.string.error_input_name)
    } else {
        null
    }
    view.error = message
}

@BindingAdapter("errorInputCount")
fun bindErrorInputCount(view: TextInputLayout, value: Boolean) {
    val message = if (value) {
        view.context.getString(R.string.error_input_count)
    } else {
        null
    }
    view.error = message
}