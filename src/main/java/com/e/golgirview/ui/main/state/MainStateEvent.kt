package com.e.golgirview.ui.main.state

sealed class MainStateEvent {

    class GetItemEvent : MainStateEvent()

    class None : MainStateEvent()
}