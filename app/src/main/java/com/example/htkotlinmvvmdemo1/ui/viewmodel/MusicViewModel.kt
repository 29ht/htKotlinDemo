package com.example.htkotlinmvvmdemo1.ui.viewmodel

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Application
import android.util.Log
import android.view.animation.LinearInterpolator
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.caspar.base.helper.ActivityStackManager.application
import com.example.htkotlinmvvmdemo1.bean.NetworkResult
import com.example.htkotlinmvvmdemo1.bean.response.ImageBanner
import com.example.htkotlinmvvmdemo1.bean.response.QQBean
import com.example.htkotlinmvvmdemo1.bean.response.SearchList
import com.example.htkotlinmvvmdemo1.bean.response.StartMusic
import com.example.htkotlinmvvmdemo1.repository.MenuRepository
import com.example.htkotlinmvvmdemo1.service.MyPlayerService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import snow.player.PlayerClient
import snow.player.audio.MusicItem
import snow.player.playlist.Playlist
import java.util.concurrent.TimeUnit


/*
 *封面图片url要使用albumid字段拼接生成，格式：
 *比如albumid=8217，封面地址就是
 *http://imgcache.qq.com/music/photo/album_300/17/300_albumpic_8217_0.jpg
 *专辑封面
 * -------------------------------------------------------------------------------》
 * 获取token
 * songmid可以从歌曲信息中取到，filename根据songmid生成。比如，songmid是003lghpv0jfFXG，则filename就是前缀加上C400，后缀加上.m4a，即C400003lghpv0jfFXG.m4a。
 * 其他字段format、platform、cid、guid可以写死，但都是必须的。
 *   https://c.y.qq.com/base/fcgi-bin/fcg_music_express_mobile3.fcg?format=json205361747&platform=yqq&cid=205361747&songmid=003lghpv0jfFXG&filename=C400003lghpv0jfFXG.m4a&guid=126548448
 *
 * ----------------------------------------------------------------------------------------------》
 * 拼接播放地址
 * url的path就是上文中用到的filename。参数中的几个字段都是必须的：guid要和请求token时使用的guid保持一致，vkey即token中的vkey字段，fromtag随意指定一个整数，可以写死为0。
 * http://ws.stream.qqmusic.qq.com/C400003lghpv0jfFXG.m4a?fromtag=0&guid=126548448&vkey=D661E5DF19B8FEB2FBFC554276746AC608AE98B0F30595B3B3BAD5C1C89ECCDD7BE599E306F786621856D22D6BD6B96F5DD344CF3814DB71
 */
class MusicViewModel(application: Application) : AndroidViewModel(application) {
    var misUrl: String? =
        "http://bpic.588ku.com/art_origin_min_pic/19/03/19/edc7b7b834a931669f2a7e4d4617233c.jpg"
    private var playerClient: PlayerClient? = null

    // 图片旋转动画
    private var objectAnimator: ObjectAnimator? = null
    fun getIvData(): String? {
        return misUrl
    }

    fun startImg(image: AppCompatImageView, cancel: Boolean) {
        if (objectAnimator != null && cancel) {
            objectAnimator?.cancel()
            return
        }
        objectAnimator = ObjectAnimator.ofFloat(image, "rotation", 0f, 360f)
        objectAnimator?.interpolator = LinearInterpolator()
        objectAnimator?.duration = 20 * 1000
        objectAnimator?.repeatCount = ValueAnimator.INFINITE//Animation.INFINITE 表示重复多次
        objectAnimator?.repeatMode = ValueAnimator.RESTART//RESTART表示从头开始，REVERSE表示从末尾倒播
        objectAnimator?.start()
    }

    /*
     *播放音乐
     */
    fun startMusic(music: StartMusic?, play: Boolean) {
        if (music == null) {
            return
        }
        if (play && playerClient != null) {
            playerClient!!.shutdown()
            return
        }
        // 创建一个 PlayerClient 对象
        if (playerClient == null) {
            playerClient = PlayerClient.newInstance(
                application!!.applicationContext,
                MyPlayerService::class.java
            )
        }

        // 连接到 PlayerService
        playerClient?.connect { success ->
            Log.d("App", "connect: $success")
            val song1 = MusicItem.Builder()
                .setTitle(music.songName!!)
                .setArtist(music.singerName!!)
                //    .setAlbum("太阳照常升起 电影原声大碟")
                .setDuration(
                    TimeUnit.MILLISECONDS.convert(
                        music.timeLength!!.toLong(),
                        TimeUnit.SECONDS
                    ).toInt()
                )
                .setUri(music.url!!)
                .setIconUri(misUrl.toString())
                .build()
            // 设置播放列表，并播放音乐
            playerClient?.setPlaylist(Playlist.Builder().append(song1).build(), true)


        }
    }


    //获取QQ音乐推荐列表
    var qqMusicsLateResult: MutableStateFlow<NetworkResult<QQBean>> =
        MutableStateFlow(NetworkResult.Loading())

    fun getMusicItem() {
        viewModelScope.launch {
            MenuRepository.qqMusicData().collect {
                qqMusicsLateResult.emit(it)
            }
        }
    }


    //获取token
    var qqMusicsTokenLateResult: MutableStateFlow<NetworkResult<ImageBanner>> =
        MutableStateFlow(NetworkResult.Loading())

    fun getBannerUrl() {
        viewModelScope.launch {
            MenuRepository.qqMusicTokenData().collect {
                qqMusicsTokenLateResult.emit(it)
            }
        }

    }

    //获取搜索歌名歌曲
    var qqMusicsSearchLateResult: MutableStateFlow<NetworkResult<SearchList>> =
        MutableStateFlow(NetworkResult.Loading())

    fun getMusicsSearchUrl(keyword: String) {
        viewModelScope.launch {
            MenuRepository.qqMusicSearchData(keyword = keyword).collect {
                qqMusicsSearchLateResult.emit(it)
            }
        }

    }

    //获取搜索歌名歌曲播放链接
    var qqMusicsSearchStartResult: MutableStateFlow<NetworkResult<StartMusic>> =
        MutableStateFlow(NetworkResult.Loading())

    fun getMusicsSearchStartUrl(hash: String) {
        viewModelScope.launch {
            MenuRepository.qqMusicSearchStartData(hash = hash).collect {
                qqMusicsSearchStartResult.emit(it)
            }
        }

    }

}