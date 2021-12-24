package com.example.htkotlinmvvmdemo1.bean.response

import java.util.ArrayList

data class PostLinkedElong(

    var Code: String? = null,
    var ElongLocationList: ArrayList<ElongLocationLists>? = null,
    var Message: String? = null
)

data class ElongLocationLists(
    var ThreeCode: String? = null,
    var BasicCityCode: String? = null,
    var BasicCityNameCn: String? = null,
    var BasicCityNameEn: String? = null,
    var HotelScore: String? = null,
    var HotelMallName: String? = null,
    var Address: String? = null,
    var ID: String? = null,
    var ParentId: String? = null,
    var ParentName: String? = null,
    var RegionName: String? = null,
    var ShowRegionName: String? = null,
    var Type: String? = null,
    var TypeName: String? = null
)