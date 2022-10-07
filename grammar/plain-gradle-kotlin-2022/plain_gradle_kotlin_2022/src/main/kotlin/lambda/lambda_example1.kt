package lambda
//https://www.baeldung.com/kotlin/lambda-expressions
fun main(){
    val list1 = listOf(1,2,3)

    val r1 = list1.reduce { total, num -> total + num }
    val r2 = list1.fold(0){ total, num -> total + num }
    val r22 = list1.fold(0){ total, num ->
        println("total = $total , num = $num")
        total + num
    }

    println("r1 = $r1")
    println("r2 = $r2")
    println("rr2 = $r22")

    val list2 = listOf(1,2,3,4,5)
    val r3 = list2.fold(10){total, num -> total + num} // 1+2+3+4+5 = 15 and + 10 = 25
    val r4 = list2.fold(1){total, num -> total * num}
    val r5 = list2.fold(10){total, num -> total * num}

    println("r3 = $r3")
    println("r4 = $r4")
    println("r5 = $r5")

    list2.fold(1) {
            a, b ->
                val result = a*b
                result
    }
}


