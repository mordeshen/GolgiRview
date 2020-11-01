package com.e.golgirview.ui

import com.e.golgirview.ui.DataState

interface cDataStateListener {
    fun onDataStateChange(dataState: DataState<*>?)
}