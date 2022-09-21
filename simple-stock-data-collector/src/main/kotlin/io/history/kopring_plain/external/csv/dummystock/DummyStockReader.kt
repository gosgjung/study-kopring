package io.history.kopring_plain.external.csv.dummystock

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource

class DummyStockReader {
    val logger : Logger = LoggerFactory.getLogger(javaClass)

    // 아래 함수를 fromCallable 등을 활용해 비동기적으로 읽어들이는 함수는 별도의 함수에 별도 구현
    // readCsvFile 은 정해진 파일명을 받아서 파일을 읽어들이는 역할만을 담당
    fun readCsvFile(filePath: String) : List<DummyStock> {
        val classPathResource = ClassPathResource(filePath)
        val reader = classPathResource.inputStream.bufferedReader()

        val firstLine = reader.readLine()

        return reader.buffered(20)
            .lineSequence()
            .filter{
                it.isNotBlank()
            }
            .map{
                val (ticker, price) = it.split(',', ignoreCase = false, limit = 3)
                DummyStock(ticker.trim().removeSurrounding("\""), price.trim().removeSurrounding("\""))

            }
            .toList()
    }
}