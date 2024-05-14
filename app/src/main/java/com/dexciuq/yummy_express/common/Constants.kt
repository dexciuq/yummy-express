package com.dexciuq.yummy_express.common

object Constants {
    object Preferences {
        const val SHARED_PREF_NAME = "yummy_express_shared_pref"
        const val ON_BOARDING_COMPLETE = "on_boarding_complete"
        const val ACCESS_TOKEN = "access_token"
        const val REFRESH_TOKEN = "refresh_token"
        const val AUTH_SKIP = "auth_skip"
    }

    object API {
//        const val BASE_URL = "http://10.0.2.2:8080/"
        const val BASE_URL = "http://192.168.0.14:8080/"
    }

    object DB {
        const val DATABASE_NAME = "yummy-express"
    }

    object TransitionName {
        const val NAME = "name"
        const val PRICE = "price"
        const val IMAGE = "image"
    }
}