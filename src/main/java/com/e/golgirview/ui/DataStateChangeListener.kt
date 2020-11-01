package com.e.golgirview.ui

import com.e.golgirview.ui.DataState

interface DataStateChangeListener {
    fun onDataStateChange(dataState: DataState<*>?)

    fun expandAppBar()

    fun hideSoftKeyboard()
}