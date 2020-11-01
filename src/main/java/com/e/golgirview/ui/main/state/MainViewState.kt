package com.e.golgirview.ui.main.state

import com.e.golgirview.model.ItemModel


data class MainViewState(
    var itemModel: ItemModel? = null,
    var loginFields: LoginFields = LoginFields()
)


data class LoginFields(
    var login_name: String = ""
){
    class LoginError {

        companion object{

            fun mustFillAllFields(): String{
                return "You can't login without an email and password."
            }

            fun none():String{
                return "None"
            }

        }
    }
    fun isValidForLogin(): String{

        if(login_name.isNullOrEmpty()){
            return LoginError.mustFillAllFields()
        }
        return LoginError.none()
    }


}