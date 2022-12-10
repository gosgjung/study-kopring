package function.anonymous_function

fun main(){
    val f : (Double) -> Double = {Math.PI/2 - it}
    val sin : (Double) -> Double = Math::sin
}