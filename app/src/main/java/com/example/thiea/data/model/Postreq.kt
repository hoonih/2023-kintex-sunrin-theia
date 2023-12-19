package com.example.thiea.data.model

data class Postreq(
    val author_uid : String,
    val text : String,
    val latitude : Double,
    val longitude : Double,
    val sentiment : Int,
)