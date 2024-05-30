package com.caption.mprint.manager

import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.SPUtils
import com.caption.mprint.App
import com.caption.mprint.common.KEY_LANGUAGE
import com.caption.mprint.common.KEY_LOGIN
import com.caption.mprint.entity.LoginInfo
import com.caption.mprint.entity.TypefaceInfo
import com.caption.mprint.entity.User
import java.lang.Exception
import java.util.*
import com.google.gson.reflect.TypeToken

object UserManager {

    var token: String? = null

    var userInfo: User? = null

    var language = 0 //0英语、1中文、2西班牙语、3阿拉伯语

    var userId: String? = null

    var unit = "mm"

    var fontTypeFaceList: List<TypefaceInfo>? = null

    var mLoginInfo: LoginInfo? = null

    fun setLoginInfo(loginInfo: LoginInfo) {
        mLoginInfo = loginInfo
        token = loginInfo.token
        userInfo = loginInfo.user
        userId = userInfo?.id
    }

    fun clearLoginInfo() {
        token = null
        userInfo = null
        userId = null
        unit = "mm"

    }

    fun isLogin(): Boolean {
        return userInfo != null
    }

    fun resetLanguage(activity: AppCompatActivity?) {
        try {
            val aLanguage = SPUtils.getInstance().getInt(KEY_LANGUAGE, -1)
            if (aLanguage != -1) {
                if (activity != null) {
                    setupLanguage(activity, aLanguage)
                }
                language = aLanguage
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setupLanguage(activity: AppCompatActivity, aLanguage: Int) {
        val languageS = when (aLanguage) {
            3 -> {
                "ar"
            }
            2 -> {
                "es"
            }
            1 -> {
                "zh"
            }
            else -> {
                "en"
            }
        }
        val myLocale = Locale(languageS)
        val dm = activity.resources.displayMetrics
        val conf = activity.resources.configuration
        conf.locale = myLocale
        activity.resources.updateConfiguration(conf, dm)

        language = aLanguage
        SPUtils.getInstance().put(KEY_LANGUAGE, aLanguage)
    }

    fun reLogin() {
        try {
            val loginInfo = GsonUtils.fromJson<LoginInfo>(
                SPUtils.getInstance().getString(KEY_LOGIN, ""),
                object : TypeToken<LoginInfo>() {}.type
            )
            if (loginInfo != null) {
                App.instance.isLoginSuccess = true
                setLoginInfo(loginInfo)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}