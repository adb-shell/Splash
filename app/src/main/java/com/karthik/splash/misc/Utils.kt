package com.karthik.splash.misc

import android.content.Context
import android.net.ConnectivityManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.karthik.splash.models.PhotosLists.Photos
import com.karthik.splash.restserviceutility.UserOfflineException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Utils {
    enum class NetworkErrorType { OFFLINE, IOEXCEPTION }
    enum class ResponseType { NEW, TRENDING, FEATURED, LIKES }

    companion object{
        const val UNSPLASHDATEFORMAT = "yyyy-MM-dd'T'HH:mm:ss"
        val photo = "photo"
        val photomimetype = "image/*"
        fun isInternetAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }

        fun convertArrayListToString(responseList: List<Any>): String = Gson().toJson(responseList)

        fun convertStringToArrayList(response: String, responseType: ResponseType): ArrayList<out Any>? {
            if (responseType == ResponseType.NEW || responseType == ResponseType.TRENDING || responseType == ResponseType.FEATURED) {
                val type = object : TypeToken<ArrayList<Photos>>(){}.type
                return Gson().fromJson<ArrayList<out Any>>(response, type)
            }
            return null
        }

        fun getErrorType(e: Throwable): Utils.NetworkErrorType {
            return if (e is UserOfflineException) Utils.NetworkErrorType.OFFLINE else Utils.NetworkErrorType.IOEXCEPTION
        }

        fun parseDate(receivedDate: String?): String? {
            if(receivedDate==null)
                return null

            val formatter = SimpleDateFormat(UNSPLASHDATEFORMAT,Locale.getDefault())
            return try {
                val cal = Calendar.getInstance()
                cal.time = formatter.parse(receivedDate)
                cal.get(Calendar.DAY_OF_MONTH).toString() + "/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.YEAR)
            } catch (e: ParseException) {
                e.printStackTrace()
                null
            }

        }
    }
}