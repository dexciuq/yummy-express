package com.dexciuq.yummy_express.data.data_source.remote.token

import com.dexciuq.yummy_express.common.Constants
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber
import java.net.HttpURLConnection
import javax.inject.Inject

class RefreshTokenInterceptor @Inject constructor(
    private val sessionManager: SessionManager,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
            val accessToken = sessionManager.getAccessToken()
            val prev = request.headers().get("Authorization")

            return if (prev.isNullOrBlank()) {
                Timber.i("Adding access token to header")
                response.close()
                chain.proceed(newRequestWithAccessToken(accessToken, request))
            } else if (prev == accessToken) {
                Timber.i("Access token is expired. Refreshing...")
                val refreshToken = sessionManager.getRefreshToken()
                response.close()
                val newAccessToken = refreshAccessToken(chain, refreshToken)
                sessionManager.setAccessToken(newAccessToken ?: "")
                chain.proceed(newRequestWithAccessToken(newAccessToken, request))
            } else {
                Timber.i("Refresh token is expired. Logout")
                response
            }
        }

        return response
    }

    private fun newRequestWithAccessToken(accessToken: String?, request: Request): Request =
        request.newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .build()

    private fun refreshAccessToken(chain: Interceptor.Chain, refreshToken: String?): String? {
        val request = Request.Builder()
            .url("${Constants.API.BASE_URL}/v1/auth/refresh")
            .header("Authorization", "Bearer $refreshToken")
            .get()
            .build()

        val response = chain.proceed(request)
        val accessToken = if (response.isSuccessful) {
            val body = response.body()?.string()
            body?.let { extractAccessToken(it) }
        } else {
            null
        }
        return accessToken
    }

    private fun extractAccessToken(body: String): String? {
        return try {
            JSONObject(body).optString("accessToken", null)
        } catch (e: JSONException) {
            null
        }
    }
}
