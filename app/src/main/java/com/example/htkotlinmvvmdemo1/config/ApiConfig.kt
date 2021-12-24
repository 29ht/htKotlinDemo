package com.example.htkotlinmvvmdemo1.config

/**
 * 存放网络接口
 */
object ApiConfig {
    //网络请求的BaseUrl层
    var BaseUrl = "http://www.baidu.com/"

    //获取qq音乐列表
    const val QQMusicData =
        "https://c.y.qq.com/v8/fcg-bin/fcg_v8_toplist_cp.fcg?g_tk=5381&uin=0&format=json&inCharset=utf-8&outCharset=utf-8%C2%ACice=0&platform=h5&needNewCode=1&tpl=3&page=detail&type=top&topid=36&_=1520777874472"

    //获取qq音乐封面地址
    const val qqCover = "http://imgcache.qq.com/music/photo/album_300/17/300_albumpic_"
    const val StartMusic1 = "https://c.y.qq.com/musichall/fcgi-bin/fcg_yqqhomepagerecommend.fcg"
    const val SearchMusic = "http://mobilecdn.kugou.com/api/v3/search/song?format=json&page=1&pagesize=20&showtype=1&"
    const val  SearchStartMusic="https://m.kugou.com/app/i/getSongInfo.php?cmd=playInfo&"
    const val getCity =
        "http://datavmap-public.oss-cn-hangzhou.aliyuncs.com/areas/csv/100000_province.json"

    //网络请求,翻译
    const val Translate = "http://fanyi.youdao.com/translate"
    const val PostHotelCity =
        "https://mobileservicetest.bcdtravel.cn:8089/AndroidService.testForIT/QueryService.svc/GetLinkedElongPost"
    const val DailyVerse = "http://poetry.apiopen.top/sentences"
    const val News = "https://api.apiopen.top/getWangYiNews"
    const val News1 =
        "https://pacaio.match.qq.com/irs/rcd?cid=108&ext=&token=349ee24cdf9327a050ddad8c166bd3e3&page=3"
    const val Hotel =
        "https://mobileservicetest.bcdtravel.cn:8089/AndroidService.testForIT/QueryService.svc/QueryHotelPost"

}