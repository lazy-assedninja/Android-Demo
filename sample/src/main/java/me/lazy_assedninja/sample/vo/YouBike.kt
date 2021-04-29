package me.lazy_assedninja.sample.vo

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class YouBike(
    @field:SerializedName("sno") // 站點代號
    val stationNo: String,
    @field:SerializedName("sna") // 場站中文名稱
    val stationName: String,
    @field:SerializedName("tot") // 場站總停車格
    val totalSpace: String,
    @field:SerializedName("sbi") // 場站目前車輛數量
    val bikeAmount: String,
    @field:SerializedName("sarea") // 場站區域
    val stationArea: String,
    @field:SerializedName("mday") // 資料更新時間
    val modifyDate: String,
    @field:SerializedName("lat") // 緯度
    val latitude: String,
    @field:SerializedName("lng") // 經度
    val longitude: String,
    @field:SerializedName("ar") // 地點
    val area: String,
    @field:SerializedName("sareaen") // 場站區域英文
    val stationAreaEnglish: String,
    @field:SerializedName("snaen") // 場站名稱英文
    val stationNameEnglish: String,
    @field:SerializedName("aren") // 地址英文
    val areaEnglish: String,
    @field:SerializedName("bemp") // 空位數量
    val spaceAmount: String,
    @field:SerializedName("act") // 全站禁用狀態
    val activate: String,
    @field:SerializedName("srcUpdateTime") // 2000-00-00 00:00:00
    val srcUpdateTime: String,
    @field:SerializedName("updateTime") // 2000-00-00 00:00:00
    val updateTime: String,
    @field:SerializedName("infoTime") // 2000-00-00 00:00:00
    val infoTime: String,
    @field:SerializedName("infoDate") // 2000-00-00
    val infoDate: String,
) : Parcelable