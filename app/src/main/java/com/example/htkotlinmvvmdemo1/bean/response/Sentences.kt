package com.example.htkotlinmvvmdemo1.bean.response

data class Sentences(
    var result: SentencesData? = null
)

data class SentencesData(
    var name: String? = null,
    var from: String? = null

)
