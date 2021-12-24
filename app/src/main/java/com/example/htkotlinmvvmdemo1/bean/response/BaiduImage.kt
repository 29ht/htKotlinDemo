package com.example.htkotlinmvvmdemo1.bean.response

data class BaiduImage(
    var data: ArrayList<ImageData>? = null
)

data class ImageData(
    var thumbURL: String? = null
)