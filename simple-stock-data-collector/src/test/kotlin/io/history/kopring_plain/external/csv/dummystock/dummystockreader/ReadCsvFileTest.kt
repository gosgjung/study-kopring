package io.history.kopring_plain.external.csv.dummystock.dummystockreader

import io.history.kopring_plain.external.csv.dummystock.DummyStockReader
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ReadCsvFileTest{

    // ClassPathResource, BufferedReader 객체를 분리할지 검토하는 것은
    // 기획내용이 파일 입출력이 잦은 구조인가를 고민해보고 판단 예정
    // 정해진 파일만 읽어들이는 것이라면, 굳이 자주 읽어들일 필요는 없기에 Holder 에 데이터를 적재해둘 예정
    @Test
    fun `단위기능 테스트 = 정해진 파일명 READ 정상여부 테스트`(){
        val filePath = "external/csv/dummystock/dummy_stocks.csv"
        val reader = DummyStockReader()
        val accountList = reader.readCsvFile(filePath)
        assertThat(accountList).isNotEmpty()

        accountList.forEach{
            println(it)
        }
    }
}