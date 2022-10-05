package io.testprj.kopring_webflux.book.application

import io.testprj.kopring_webflux.global.formatter.OffsetDateTimeFormatters
import org.springframework.stereotype.Service
import java.time.ZoneId
import java.time.ZonedDateTime

@Service
class BookIdService {

    // 작가명###책이름###등록시각
    fun nextBookId(authorName: String, bookName: String): String{
        val builder : StringBuilder = StringBuilder()
        val dateStr : String = ZonedDateTime.now(ZoneId.of("Etc/UTC"))
            .format(OffsetDateTimeFormatters.COMPLETE_FORMATTER)

        return builder.append(bookName).append("###")
            .append(authorName).append("###")
            .append(dateStr)
            .toString()
    }
}