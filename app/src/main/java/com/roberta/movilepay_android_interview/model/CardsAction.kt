package com.roberta.movilepay_android_interview.model

import com.google.gson.annotations.SerializedName

data class CardsAction(
    val identifier: String?,
    @SerializedName("content")
    val cardContent: CardContent?
)
