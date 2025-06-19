package com.soezeya.currencyconverter

import android.os.Parcel
import android.os.Parcelable

data class ConversionHistory(
    val fromAmount: String,
    val toAmount: String,
    val timestamp: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(fromAmount)
        parcel.writeString(toAmount)
        parcel.writeString(timestamp)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<ConversionHistory> {
        override fun createFromParcel(parcel: Parcel): ConversionHistory {
            return ConversionHistory(parcel)
        }

        override fun newArray(size: Int): Array<ConversionHistory?> {
            return arrayOfNulls(size)
        }
    }
}