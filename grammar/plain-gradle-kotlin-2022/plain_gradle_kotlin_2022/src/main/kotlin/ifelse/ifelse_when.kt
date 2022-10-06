package ifelse

fun main(){
    val price : Int = 33000

    val str = if(price > 33000){
        "안돼... 사지마!!!"
    } else {
        "사든가 말든가... 알아서 해"
    }

    println(str)
    println()

    val number : Int = 3
    val when1 = when(number) {
        1 -> "one"
        2 -> "two"
        3 -> "three"
        4 -> "four"
        5 -> "five"
        else -> "whatever"
    }

    println("when1 = $when1")
    println()

    println("one_two_three(3) = ${one_two_three(3)}")
    println()

    when(getMarketType(StockMarketType.NASDAQ.name)){
        StockMarketType.NASDAQ -> println("미국주식")
        StockMarketType.KOSPI, StockMarketType.KOSDAQ -> println("한국주식")
    }
    println()

    when(OneTwoThree.ONE){
        OneTwoThree.ONE -> "하나"
        OneTwoThree.TWO -> "둘"
        OneTwoThree.THREE -> "셋"
    }
    println()
}

fun one_two_three(number: Int) : String{
    return when(number){
        1 -> "one"
        2 -> "two"
        3 -> "three"
        else -> "??"
    }
}

enum class StockMarketType{
    NASDAQ, KOSPI, KOSDAQ
}

fun getMarketType(name: String) : StockMarketType {
    return StockMarketType.valueOf(name)
}

enum class OneTwoThree{
    ONE, TWO, THREE
}
