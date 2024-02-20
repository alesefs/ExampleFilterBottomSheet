package com.example.searchandbottomsheet

import androidx.annotation.DrawableRes

data class CardModel (
    @DrawableRes val image: Int,
    val title: String,
    val body: String,
    val action: String,
    val tag: String
)