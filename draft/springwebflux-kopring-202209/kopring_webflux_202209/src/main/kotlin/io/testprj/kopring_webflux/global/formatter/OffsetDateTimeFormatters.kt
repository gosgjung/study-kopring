package io.testprj.kopring_webflux.global.formatter

import java.time.format.DateTimeFormatter

object OffsetDateTimeFormatters {
    val COMPLETE_FORMATTER : DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS.ZZZ")
}