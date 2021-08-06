package com.maple.basekit.app.config

class Const {

    object Path {
        val IMEI_PATH: String = android.os.Environment.getExternalStorageDirectory().toString() + "/android/imei.text"
    }

    object SaveInfoKey {
        const val HAS_APP_FIRST = "hasFirst"

        const val ACCESS_TOKEN = "accessToken"
    }

    object BundleKey {
        const val EXTRA_URL = "url"
        const val EXTRA_OBJ = "obj"
        const val EXTRA_INDEX = "index"
        const val EXTRA_ID = "id"
    }

}