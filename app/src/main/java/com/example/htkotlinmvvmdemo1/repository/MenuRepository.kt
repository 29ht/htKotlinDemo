package com.example.htkotlinmvvmdemo1.repository

import com.caspar.base.base.BaseRepository
import com.example.htkotlinmvvmdemo1.bean.NetworkResult
import com.example.htkotlinmvvmdemo1.bean.response.*
import com.example.htkotlinmvvmdemo1.helper.call
import com.example.htkotlinmvvmdemo1.network.Api
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow


/*
 *  "ht" 创建 2021/11/25.
 *   界面名称以及功能:
 */
object MenuRepository : BaseRepository() {
    //获取qq列表
    suspend fun qqMusicData() = flow<NetworkResult<QQBean>> {
        val result = Api.api.getQQMusic()
        emit(NetworkResult.Success(result))
    }.catch { ex ->
        val networkResult = call<QQBean>(ex)
        emit(networkResult)
    }.distinctUntilChanged()

    //获取qq列表
    suspend fun qqMusicTokenData() = flow<NetworkResult<ImageBanner>> {
        val result = Api.api.getQQTokenMusic()
        emit(NetworkResult.Success(result))
    }.catch { ex ->
        val networkResult = call<ImageBanner>(ex)
        emit(networkResult)
    }.distinctUntilChanged()


    //获取搜索的音乐列表
    suspend fun qqMusicSearchData(keyword: String) = flow<NetworkResult<SearchList>> {
        val result = Api.api.getQQSearchMusic(keyword = keyword)
        emit(NetworkResult.Success(result))
    }.catch { ex ->
        val networkResult = call<SearchList>(ex)
        emit(networkResult)
    }.distinctUntilChanged()

    //获取搜索的音乐列表播放链接
    suspend fun qqMusicSearchStartData(hash: String) = flow<NetworkResult<StartMusic>> {
        val result = Api.api.getQQSearchStartMusic(hash = hash)
        emit(NetworkResult.Success(result))
    }.catch { ex ->
        val networkResult = call<StartMusic>(ex)
        emit(networkResult)
    }.distinctUntilChanged()

    //网络请求，获取城市列表
    fun HotleCity(city: HotelCity) = flow<NetworkResult<PostLinkedElong>> {
        val result = Api.api.getSyncHotelCity(city = city)
        emit(NetworkResult.Success(result))

    }.catch { ex ->
        val networkResult = call<PostLinkedElong>(ex)
        emit(networkResult)
    }.distinctUntilChanged()

    //网络请求，获取每日诗句
    fun getDailyVerse() = flow<NetworkResult<Sentences>> {
        val result = Api.api.getVerse()
        emit(NetworkResult.Success(result))

    }.catch { ex ->
        val networkResult = call<Sentences>(ex)
        emit(networkResult)
    }.distinctUntilChanged()


    /*
    网易新闻
     */
    fun getWangyiNews(count: Int, page: Int) = flow<NetworkResult<News>> {
        val result = Api.api.getWangyiNews(count = count, page = page)
        emit(NetworkResult.Success(result))

    }.catch { ex ->
        val networkResult = call<News>(ex)
        emit(networkResult)
    }.distinctUntilChanged()

    /*
    腾讯新闻
     */
    fun getTengxunNews(page: Int) = flow<NetworkResult<News>> {
        val result = Api.api.getTengxunNews(page = page)
        emit(NetworkResult.Success(result))

    }.catch { ex ->
        val networkResult = call<News>(ex)
        emit(networkResult)
    }.distinctUntilChanged()

    /*
       酒店列表
        */
    fun getHotelList(hotle: requestHotel) = flow<NetworkResult<Hotel>> {
        val result = Api.api.getHotleList(hotle = hotle)
        emit(NetworkResult.Success(result))

    }.catch { ex ->
        val networkResult = call<Hotel>(ex)
        emit(networkResult)
    }.distinctUntilChanged()
}



