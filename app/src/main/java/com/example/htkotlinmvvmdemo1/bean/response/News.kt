package com.example.htkotlinmvvmdemo1.bean.response

data class News(
    var result: ArrayList<NewsResult>? = null,
    var data: ArrayList<NewsResult>? = null
)

data class NewsResult(
    var path: String? = null,
    var image: String? = null,
    var title: String? = null,
    var url: String? = null,
    var img: String? = null,
    var passtime: String? = null,
    var publish_time: String? = null

)
