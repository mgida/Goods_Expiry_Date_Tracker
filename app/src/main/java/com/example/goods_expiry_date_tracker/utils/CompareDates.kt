package com.example.goods_expiry_date_tracker.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.abs

@RequiresApi(Build.VERSION_CODES.O)
fun compareDates(current: LocalDateTime?, selectedDate: String?): String {

    val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
    val formattedCurrentDate = current?.format(formatter)

    val dates = SimpleDateFormat("MM/dd/yyyy")
    val date1 = dates.parse(formattedCurrentDate)
    val date2 = dates.parse(selectedDate)

    val difference: Long = abs(date1.time - date2.time)
    val differenceDates = difference / (24 * 60 * 60 * 1000)
    return differenceDates.toString()

}