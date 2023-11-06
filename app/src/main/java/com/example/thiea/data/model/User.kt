package com.example.thiea.data.model

data class User (
    val uid : String,
    val name : String,
    val phone : String,
    val gender : String,
    val friends : Int = 0,
    var posts : List<Post>,
    val parties : Int = 0,
    )