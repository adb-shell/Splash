package com.karthik.splash.misc

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Utils private constructor() {
    companion object {
        const val UNSPLASHDATEFORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
        const val photo = "photo"
        const val photomimetype = "image/*"

        const val offScreenPageLimit = 3
        const val imageLoadDuration = 1200
        const val pageSize = 10
        const val intialPageSize = 10

        fun parseDate(receivedDate: String?): String {
            if (receivedDate == null)
                return ""

            val formatter = SimpleDateFormat(UNSPLASHDATEFORMAT, Locale.getDefault())
            return try {
                val cal = Calendar.getInstance()
                cal.time = formatter.parse(receivedDate)
                cal.get(Calendar.DAY_OF_MONTH)
                        .toString() + "/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.YEAR)
            } catch (e: ParseException) {
                e.printStackTrace()
                ""
            }

        }
    }
}