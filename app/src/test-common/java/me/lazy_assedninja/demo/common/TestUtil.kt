package me.lazy_assedninja.demo.common

import me.lazy_assedninja.demo.vo.User
import me.lazy_assedninja.demo.vo.YouBike

object TestUtil {

    fun createUser(id: Long = 0, name: String, email: String, password: String) = User(
        id = id,
        name = name,
        email = email,
        password = password
    )

    fun createYouBike(
        id: Long,
        stationNo: String,
        stationName: String,
        totalSpace: String,
        bikeAmount: String,
        stationArea: String,
        modifyDate: String,
        latitude: String,
        longitude: String,
        area: String,
        spaceAmount: String,
        activate: String,
        srcUpdateTime: String,
        updateTime: String,
        infoTime: String,
        infoDate: String
    ) = YouBike(
        id = id,
        stationNo = stationNo,
        stationName = stationName,
        totalSpace = totalSpace,
        bikeAmount = bikeAmount,
        stationArea = stationArea,
        modifyDate = modifyDate,
        latitude = latitude,
        longitude = longitude,
        area = area,
        stationAreaEnglish = "",
        stationNameEnglish = "",
        areaEnglish = "",
        spaceAmount = spaceAmount,
        activate = activate,
        srcUpdateTime = srcUpdateTime,
        updateTime = updateTime,
        infoTime = infoTime,
        infoDate = infoDate
    )
}