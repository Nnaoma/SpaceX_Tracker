package com.adikob.spacextracker.utility

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

const val LAUNCH_INFO_ARGUMENT_KEY = "LAUNCH_INFO_ARGUMENT_KEY"

fun filterNull(value: String?) = value ?: "...."

fun String.toDate(): String? {
    val instant = Instant.parse(this)
    val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:ss a").withZone(ZoneId.systemDefault())
    return formatter.format(instant)
}


fun Date.formatTo(dateFormat: String = "dd-MMM-yyyy hh:mm", timeZone: TimeZone = TimeZone.getDefault()): String? {
    val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
    formatter.timeZone = timeZone
    return formatter.format(this)
}