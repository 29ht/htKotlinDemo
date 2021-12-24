package com.example.htkotlinmvvmdemo1.bean.response

data class QQBean(
    var songlist: ArrayList<Songlist>? = null
)

data class Songlist(
    var data: DataList? = null
)

data class DataList(
    var albumid: String? = null,
    var albummid: String? = null,
    var songname: String? = null,
    var albumname: String? = null,
    var interval: Int? = null,
    var songmid: String? = null,
    var singer: ArrayList<Singer>? = null
)

data class Singer(
    var id: String? = null,
    var mid: String? = null,
    var name: String? = null
)


data class SearchList(
    var status: Int? = null,
    var errcode: Int? = null,
    var error: String? = null,
    var data: SearchDataList? = null,
)

data class SearchDataList(
    var info: ArrayList<InfoList>? = null,
)

data class InfoList(
    var hash: String? = null,
    var songname: String? = null,
    var album_name: String? = null,
    var songname_original: String? = null,
    var singername: String? = null,
)

data class StartMusic(
    var url: String? = null,
    var error: String? = null,
    var songName: String? = null,
    var singerName: String? = null,
    var timeLength: Int? = 1
)