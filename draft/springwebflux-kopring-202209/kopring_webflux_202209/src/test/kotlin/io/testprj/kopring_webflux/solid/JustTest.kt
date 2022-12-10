package io.testprj.kopring_webflux.solid

import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

class JustTest {

    @Test
    fun testSomething(){
        val localDateTime = ZonedDateTime.now()
            .withZoneSameInstant(ZoneId.of("America/New_York"))
            .toLocalDateTime()

        println("localDateTime = $localDateTime")
        println("zonedDateTime.now() = ${ZonedDateTime.now()}")


        val zonedDateTime = ZonedDateTime.of(LocalDate.now(), LocalTime.of(13,0,0), ZoneId.of("Etc/UTC"))
        println("print >>> $zonedDateTime")
        val translatedDt = zonedDateTime.withZoneSameInstant(ZoneId.of("America/New_York"))
            .toLocalDateTime()
        println("translatedDt >>> $translatedDt")


        val krSampleTime = ZonedDateTime.of(LocalDate.now(), LocalTime.of(9,0,1), ZoneId.of("Asia/Seoul"))
        println("krSampleTime >>> $krSampleTime")
        val translatedDtKr = krSampleTime.withZoneSameInstant(ZoneId.of("Etc/UTC")).toLocalDateTime()

        println("krSampleTime(utc) >>> $translatedDtKr")
    }
}