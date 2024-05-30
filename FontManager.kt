package com.caption.mprint.manager

import com.caption.mprint.entity.TypefaceInfo

object FontManager {

    private var fontTypeFaceMap: HashMap<Int, List<TypefaceInfo>> = HashMap()

    fun getFontTypeFaceList(): List<TypefaceInfo>? {
        return fontTypeFaceMap[UserManager.language]
    }

    fun setFontTypeFaceList(typefaceInfoList: List<TypefaceInfo>?) {
        if (typefaceInfoList != null) {
            fontTypeFaceMap[UserManager.language] = typefaceInfoList
        }
    }
}