package io.gosgjung.study.fpstudy.reducing

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class SubtractingTest1 {

    /**
     * 관리비 통장잔고에 1년치 관리비를 저장해뒀다.
     * 매달 관리비는 다르다.
     * 1년치 월세를 모두 소모하는 함수형 프로그램을 작성해보자
     */
    @Test
    @DisplayName("월세통장잔고에 1년치 관리비를 저장해뒀다. 매달 관리비는 다르다. 1년치 월세를 모두 소모하는 프로그램을 작성해보자")
    fun `1년치 각 월별 월세를 소모하는 단순한 형태의 함수형 프로그램`(){
        // given
        // 1) 원하는 관리비 시나리오 : 1,2,3,4,5,6,7,8,9,10,11,12
        val list1 = listOf(1,2,3,4,5,6,7,8,9,10,11,12)
        val max = list1.sum()
        assertThat(max, equalTo(78))

        val list2 = listOf(1,1,1,1,1,1,1,1,1,1,1,1)
        assertThat(list2.sum(), equalTo(12))

        val list3 = listOf(10,10,10,10,10,10,10,10,10,10,10,10)
        assertThat(list3.sum(), equalTo(120))

        // 2.1)
        val copy1 = list1.toMutableList() // for copy-on-write
        val result1 = copy1.fold(max) { acc, i ->  acc - i}
        assertThat(result1, equalTo(0))

        // 2.1) 78 - 12
        val copy2 = list2.toMutableList() // for copy-on-write
        val result2 = copy2.fold(max) { acc, i -> acc - i}
        assertThat(result2, equalTo(78-12))

        // 2.3) 관리비의 총합이 더 커서 max 를 초과하는 경우
        val copy3 = list3.toMutableList()
        val result3 = copy3.fold(max) { acc, i ->
            if(acc <= i) 0
            else acc - i
        }
        assertThat(result3, equalTo(0))
    }

    data class Acc(
        val myMoney: Int,
        val usedMoney: Int = 0,
        val lastIndex: Int,
        val tobeSum: Int = 0,
    )

    @Test
    @DisplayName("현재 관리비 통장의 잔액으로 관리비를 몇월 치 관리비까지 계산할 수 있을지 계산")
    fun `현재 관리비 통장의 잔액으로 관리비를 몇월 치 관리비까지 계산할 수 있을지 계산`(){
        // given
        // 관리비 통장 잔액
        val max = 78
        // 월별 관리비 예상 금액
        //                 78,68,58,48,38,28,18, 8,-2, 0, 0, 0
        //                  0, 1, 2, 3, 4, 5, 6, 7, 8, 9,10,11
        val list1 = listOf(10,10,10,10,10,10,10,10,10,10,10,10)

        // when
        val copy1 = list1.toMutableList()
        val result = copy1
            .fold(Acc(myMoney = max, usedMoney = 0, lastIndex = 0, tobeSum = 0)){
                acc, monthlyBill ->
                    val tobeSum = acc.tobeSum + monthlyBill

                    if(acc.myMoney <= 0){
                        Acc(
                            myMoney = 0,
                            usedMoney = acc.usedMoney,
                            tobeSum = tobeSum,
                            lastIndex = acc.lastIndex,
                        )
                    }
                    else{
                        println("current :: ${acc}")
                        println("tobe : ${acc.myMoney - monthlyBill}, tobeIndex = ${acc.lastIndex+1}")

                        if(acc.myMoney <= monthlyBill){ // e.g. myMoney = 8, monthlyBill = 10
                            Acc(
                                myMoney = 0,
                                usedMoney = acc.usedMoney + acc.myMoney,
                                tobeSum = tobeSum,
                                lastIndex = acc.lastIndex+1,
                            )
                        }else{ // e.g. myMoney = 58, monthlyBill = 10
                            Acc(
                                myMoney = acc.myMoney - monthlyBill,
                                usedMoney = acc.usedMoney + monthlyBill,
                                tobeSum = tobeSum,
                                lastIndex = acc.lastIndex+1
                            )
                        }

                    }
            }

        assertThat(result.myMoney, equalTo(0))
        assertThat(result.usedMoney, equalTo(max))
        assertThat(result.tobeSum, equalTo(120))

        // 나중에 다시 돌아와서 봤을 때, 외계인이 풀라고 남겨둔 숙제처럼 느껴질 것 같아서 println 문을 추가..;;;
        println(result)
    }

    fun fetchApiResult() : List<Int>{
        return listOf(10,10,10,10,10,10,10,10,10,10,10,10)
    }

    @Test
    @DisplayName("현재 관리비 통장의 잔액이 1년 치 예상 관리비보다 넉넉하게 있을 경우 반복문을 통해 연산을 수행")
    fun `현재 관리비 통장의 잔액이 1년 치 예상 관리비보다 넉넉하게 있을 경우 반복문을 통해 연산을 수행`(){
        // given
        // 관리비 통장 잔액
        val max = 300
        // 월별 관리비 예상 금액
        //                  0, 1, 2, 3, 4, 5, 6, 7, 8, 9,10,11
        val list1 = listOf(10,10,10,10,10,10,10,10,10,10,10,10)

        // when
        val copy1 = list1.toMutableList()

        val fnSubMonthlyBill : (acc: Acc, monthlyBill: Int) -> Acc = {
            acc, monthlyBill ->
                val tobeSum = acc.tobeSum + monthlyBill

                if(acc.myMoney <= 0){
                    Acc(
                        myMoney = 0,
                        usedMoney = acc.usedMoney,
                        tobeSum = tobeSum,
                        lastIndex = acc.lastIndex,
                    )
                }
                else{
                    println("current :: ${acc}")
                    println("tobe : ${acc.myMoney - monthlyBill}, tobeIndex = ${acc.lastIndex+1}")

                    if(acc.myMoney <= monthlyBill){ // e.g. myMoney = 8, monthlyBill = 10
                        Acc(
                            myMoney = 0,
                            usedMoney = acc.usedMoney + acc.myMoney,
                            tobeSum = tobeSum,
                            lastIndex = acc.lastIndex+1,
                        )
                    }else{ // e.g. myMoney = 58, monthlyBill = 10
                        Acc(
                            myMoney = acc.myMoney - monthlyBill,
                            usedMoney = acc.usedMoney + monthlyBill,
                            tobeSum = tobeSum,
                            lastIndex = acc.lastIndex+1
                        )
                    }

                }
        }

        // 1) 1년치 관리비 리스트는 매번 새로 받아와야 한다.
        //  => 1년치 관리비 리스트는 매 루프마다 toMutableList 로 새로운 리스트를 받아온다.

        // 2) max 는 1년치 관리비 리스트의 합을 매 루프마다 순서대로 차감해야 한다.
        // 3) 루프 한번은 1년이고, 합을 구하는 reduce 는 1개월씩의 관리비를 순차적으로 fold 한다.

        // max = 300, copy 1 = [10,...,10] => 180
        // max = 180, copy 2 = [10,...,10] => 60
        // max = 60,  copy 3 = [10,...,10] => 0 (6 개월치만 소모)

        var remain = max

        // 이 부분 어떻게 불변으로 바꿀수 있을까...
        while(remain > 0){
            remain = fetchApiResult()
                .toMutableList()
                .fold(Acc(myMoney = remain, usedMoney = 0, lastIndex = 0, tobeSum = 0))
                { acc, monthlyBill -> fnSubMonthlyBill(acc, monthlyBill) }.myMoney
        }

        println(remain)







        val fpYearlyReducing : (limit: Int, (acc: Acc, monthlyBill: Int) -> Acc) -> Acc = {
                limit, function -> fetchApiResult().toMutableList()
            .fold(Acc(myMoney = max, usedMoney = 0, lastIndex = 0, tobeSum = 0))
            {acc, monthlyBill -> function(acc, monthlyBill)}
        }
    }

}