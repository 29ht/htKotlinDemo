package com.example.htkotlinmvvmdemo1.network

import com.example.htkotlinmvvmdemo1.bean.response.*
import com.example.htkotlinmvvmdemo1.config.ApiConfig
import com.example.htkotlinmvvmdemo1.config.ApiConfig.SearchMusic
import com.example.htkotlinmvvmdemo1.config.ApiConfig.SearchStartMusic
import com.example.htkotlinmvvmdemo1.config.ApiConfig.StartMusic1
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * "ht" 创建 2021/11/25.
 * 界面名称以及功能:Http的接口
 */
interface ApiService {
    /**
     * 下载文件
     * @param range 下载的范围，一般只有流文件才会生效，其他类型的下载需要做文件过滤 格式为:bytes = 全部[0-],范围[0-100]
     * @param url 下载的连接地址
     */
    @Streaming
    @GET
    suspend fun download(@Header("Range") range: String, @Url url: String): ResponseBody

    /**
     * config设置
     */
    @GET(ApiConfig.QQMusicData)
    suspend fun getQQMusic(): QQBean

    /**
     * config设置
     * &filename=C400003lghpv0jfFXG.m4a&guid=126548448
     */
    @GET(StartMusic1)
    suspend fun getQQTokenMusic(
        //      @Query("format") data: String
    ): ImageBanner

    @GET(SearchMusic)
    suspend fun getQQSearchMusic(
              @Query("keyword") keyword: String
    ): SearchList

    @GET(SearchStartMusic)
    suspend fun getQQSearchStartMusic(
        @Query("hash") hash: String
    ): StartMusic



    /**
     * chengshi
     */
    @GET(ApiConfig.getCity)
    fun getSyncCity(): Call<City>

    /*
  * 城市
  */
    @POST(ApiConfig.PostHotelCity)
    suspend fun getSyncHotelCity(
        @Body city: HotelCity
    ): PostLinkedElong

    /*
* 每日诗句
*/
    @GET(ApiConfig.DailyVerse)
    suspend fun getVerse(
    ): Sentences

    /*
    网易新闻
     */
    @POST(ApiConfig.News)
    suspend fun getWangyiNews(
        @Query("count") count: Int,
        @Query("page") page: Int
    ): News

    /*
    腾讯新闻
     */
    @POST(ApiConfig.News1)
    suspend fun getTengxunNews(
        @Query("page") page: Int
    ): News

    /*
腾讯新闻
 */
    @POST(ApiConfig.Hotel)
    suspend fun getHotleList(
        @Body hotle: requestHotel
    ): Hotel
}
