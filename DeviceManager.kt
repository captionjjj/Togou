package com.caption.mprint.manager

import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.SPUtils
import com.caption.mprint.App
import com.caption.mprint.R
import com.caption.mprint.common.KEY_DEVICE_INFO
import com.caption.mprint.entity.DeviceInfo
import com.caption.mprint.entity.DeviceType
import com.caption.mprint.entity.PrintInfo
import com.caption.mprint.entity.TypefaceInfo
import com.caption.mprint.util.DevicePrinter
import com.google.gson.reflect.TypeToken
import com.module.mprinter.PrinterInfo

object DeviceManager {

    var deviceList: HashMap<String, DeviceInfo>? = null

    var noConnectName = ""

    fun put(sn: String?, type: String?, device: DeviceInfo) {
        if (deviceList == null) {
            deviceList = getList()
        }
        if (type == null) {
            return
        }
        deviceList!![type + sn] = device
        SPUtils.getInstance().put(KEY_DEVICE_INFO, GsonUtils.toJson(deviceList))
    }

    fun get(sn: String?, type: String?): DeviceInfo? {
        if (deviceList == null) {
            deviceList = getList()
        }
        return deviceList!![type + sn]
    }

    fun getName(sn: String?, type: String?, defaultName: String?): String? {
        val deviceInfo = get(sn, type)
        val name: String? = if (deviceInfo != null) {
            deviceInfo.name
        } else {
            defaultName
        }

        if (name?.startsWith("QR380") == true) {
            return "P129B"
        }
        if (name?.startsWith(DeviceType.TYPE_IMP032B) == true) {
            return "P032B"
        }
        return when (name) {
            DeviceType.TYPE_B246D -> {
                "P129B"
            }
            DeviceType.TYPE_D30S -> {
                "P031B"
            }
            else -> {
                name
            }
        }
    }

    fun getCurrName(): String {
        return getCurrName(
            noConnectName
        )
    }

    fun getCurrName(noConnectName: String): String {
        return if (DevicePrinter.isConnect()) {
            getName(DevicePrinter.getSn(), DevicePrinter.getType(), DevicePrinter.getType())
                ?: noConnectName
        } else {
            noConnectName
        }
    }

    private fun getList(): HashMap<String, DeviceInfo>? {
        val deviceInfoStr = SPUtils.getInstance().getString(KEY_DEVICE_INFO)
        if (deviceInfoStr.isNullOrEmpty().not()) {
            deviceList = GsonUtils.fromJson<HashMap<String, DeviceInfo>>(
                deviceInfoStr,
                object : TypeToken<HashMap<String, DeviceInfo>>() {
                }.type
            )
        }
        if (deviceList == null) {
            deviceList = HashMap()
        }
        return deviceList
    }
}