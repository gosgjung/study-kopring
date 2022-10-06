package for_while

fun main(){
    for(number in 1..7){
        println(number)
    }
    println()

    for(number in 1 until 7){
        println(number)
    }
    println()

    for(number in 1..7 step 2){
        println(number)
    }
    println()

    for(number in 7 downTo 2){
        println(number)
    }
    println()

    val numbers = arrayOf(1,2,3,4,5)

    for(n in numbers){
        println(n)
    }
    println()
}
