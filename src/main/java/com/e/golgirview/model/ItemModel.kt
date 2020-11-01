package com.e.golgirview.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "item_model")
data class ItemModel(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "pk")
    var pk:Int = -1,


    @SerializedName("answer")
    @Expose
    @ColumnInfo
    var answer : String = "",

    @SerializedName("question")
    @Expose
    @ColumnInfo
    var question : String = ""

){
    override fun toString(): String {
        return "ItemModel(pk=$pk, answer='$answer', question='$question')"
    }

}