package extension_function

fun main(){
    fun <T> List<T>.len() = this.size
    val list = listOf(1,2,3,4,5)
    val result = list.len()
    println("list.len = $result")
    println()

    fun List<Int>.productAll(): Int = this.fold(1){a,b ->
        println("a = $a, b = $b")
        a*b
    }
    val numbers : List<Int> = listOf(1,2,3,4,5)
    println("numbers.productAll() = ${numbers.productAll()}")
    println()
}