package com.dexciuq.yummy_express.data.data_source.remote

import android.content.Context
import android.util.Log
import com.dexciuq.yummy_express.common.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

class RefreshTokenInterceptor @Inject constructor(
    @ApplicationContext private val context: Context
) : Interceptor {

    private val sharedPreferences = context.getSharedPreferences(
        Constants.Preferences.SHARED_PREF_NAME,
        Context.MODE_PRIVATE
    )

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val response = chain.proceed(originalRequest)

        if (response.code() == 401) {
            val newAccessToken = callRefreshTokenAPI(chain)
            Log.i("RefreshTokenInterceptor", "intercept: Refreshing")
            val newRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer $newAccessToken")
                .build()

            return chain.proceed(newRequest)
        }

        return response
    }

    private fun callRefreshTokenAPI(chain: Interceptor.Chain): String = runBlocking {
        val refreshToken = sharedPreferences.getString(
            Constants.Preferences.REFRESH_TOKEN, ""
        ) ?: ""

        val accessToken = refreshAccessToken(chain, refreshToken)

        sharedPreferences.edit()
            .putString(Constants.Preferences.ACCESS_TOKEN, accessToken)
            .apply()

        println(accessToken)
        accessToken
    }

    private fun refreshAccessToken(chain: Interceptor.Chain, refreshToken: String): String {
        val request = Request.Builder()
            .url("${Constants.API.BASE_URL}/v1/auth/refresh")
            .header("Authorization", "Bearer $refreshToken")
            .get()
            .build()

        val response = chain.proceed(request)
        return if (response.isSuccessful) {
            val responseBody = response.body()?.string()
            responseBody?.let { accessTokenFromJson(it) } ?: ""
        } else ""
    }

    private fun accessTokenFromJson(responseBody: String): String {
        return try {
            JSONObject(responseBody).optString("accessToken", "")
        } catch (e: JSONException) {
            ""
        }
    }
}
