package uz.itteacher.myhandybookwithcompose.network

import android.content.Context
import uz.itteacher.myhandybookwithcompose.MyApp
import timber.log.Timber

object TokenManager {
    private const val PREF_NAME = "HandyBookPrefs"
    private const val KEY_TOKEN = "access_token"

    fun saveToken(token: String) {
        try {
            val prefs = MyApp.instance.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            prefs.edit().putString(KEY_TOKEN, token).apply()
        } catch (e: UninitializedPropertyAccessException) {
            Timber.e(e, "MyApp.instance not initialized when saving token")
        }
    }

    fun getToken(): String? {
        return try {
            val prefs = MyApp.instance.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            prefs.getString(KEY_TOKEN, null)
        } catch (e: UninitializedPropertyAccessException) {
            Timber.e(e, "MyApp.instance not initialized when getting token")
            null
        }
    }

    fun clearToken() {
        try {
            val prefs = MyApp.instance.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            prefs.edit().remove(KEY_TOKEN).apply()
        } catch (e: UninitializedPropertyAccessException) {
            Timber.e(e, "MyApp.instance not initialized when clearing token")
        }
    }
}