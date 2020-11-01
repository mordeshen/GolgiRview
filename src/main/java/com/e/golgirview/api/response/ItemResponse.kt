package com.e.golgirview.api.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ItemResponse(
    @SerializedName("id")
    @Expose
    var id : Int = -1,

    @SerializedName("answer")
    @Expose
    var answer : String = "",

    @SerializedName("question")
    @Expose
    var question : String = ""
) {
    override fun toString(): String {
        return "ItemResponse(id=$id, answer='$answer', question='$question')"
    }
}