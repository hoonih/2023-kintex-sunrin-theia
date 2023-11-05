package com.example.thiea.data.model

data class reversegeocode(
    val status: Status,
    val results: List<Result>
)

data class Area3(
    val name: String
)

data class Result(
    val name: String,
    val code: Code,
    val region: Region
)

data class Code(
    val id: String,
    val type: String,
    val mappingId: String
)

data class Region(
    val area3: Area3
)

data class Status(
    val code: Int,
    val name: String,
    val message: String
)

