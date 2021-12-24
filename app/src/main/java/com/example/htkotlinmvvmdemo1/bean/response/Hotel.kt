package com.example.htkotlinmvvmdemo1.bean.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

data class Hotel(
    var count: String? = null,
    var hotels: ArrayList<Hotels>? = null,
    var HotelListTipExcludingTax: String? = null,
    var IsInter: String? = null,
    var lat: String? = null,
    var lng: String? = null,
    var SabreShopKey: String? = null,
    var LocalShopKey: String? = null,
    var GDSHotelDifferPreferenceAndAgreement: String? = null,
    var IsShowCompanyPay: String? = null,
)

@Parcelize
data class Hotels(
    var NotNeedGarantee: Boolean = false,
    var IsAgreement: String? = null,
    var IsTMCAgreement: String? = null,
    var NoRoom: String? = null,
    var Breakfast: Int? = 0,
    var CancelRuleType: Int? = 0,
    var CityCode: String? = null,
    var HotelName: String? = null,
    var ID: String? = null,
    var Price: String? = null,
    var StarLevel: String? = null,
    var StarLevelDes: String? = null,
    var imageUrl: String? = null,
    var HotelType: String? = null,
    var HotelAddress: String? = null,
    var HotelPhone: String? = null,
    var HotelCount: String? = null,
    var HotelRating: String? = null,
    var HotelRatingDes: String? = null,
    var HotelDistance: String? = null,
    var Laitude: Double? = null,
    var Longitude: Double? = null,
    var Currency: String? = null,
    var IsCommonAgreement: String? = null,
    var IsFXHotel: String? = null
) : Parcelable

//{"Language":"EN","id":"90013a75-d24c-4ba1-b662-1c8aac551884","queryKey":"SHA,,,,2022-01-27,2022-01-28,0-1-2-3-4-5,0,0,1,1,1,2000,,"}
data class requestHotel(
    var Language: String? = null,
    var id: String? = null,
    var queryKey: String? = null


) {
    override fun toString(): String {
        return "requestHotel(Language=$Language, id=$id, queryKey=$queryKey)"
    }
}