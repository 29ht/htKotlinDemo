package com.example.htkotlinmvvmdemo1.bean.response



data class ImageBanner(
    var data: ImageDataRes? = null
)

data class ImageDataRes(
    var slider: ArrayList<sliderList>? = null
)

data class sliderList(
    var linkUrl: String? = null,
    var picUrl: String? = null
)