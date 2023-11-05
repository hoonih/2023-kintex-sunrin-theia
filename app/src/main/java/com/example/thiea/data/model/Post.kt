package com.example.thiea.data.model

data class Post (
    val post_id : Int,
    val author_uid : String,
    val author : User,
    val text : String,
    val title : String,
    val latitude : Float,
    val longtitude : Float,
    )