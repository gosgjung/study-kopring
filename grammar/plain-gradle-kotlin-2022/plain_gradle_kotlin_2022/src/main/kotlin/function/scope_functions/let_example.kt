package function.scope_functions

fun main(){

    println()
    println("exam 03 ===")
    exam03()

    println()
    println("exam 04 ===")
    exam04()

    println()
    println("exam 05 ===")
    exam05()
}


fun exam03(){
    val str1: String? = "대한민국~!!"

    val result1 = str1?.let{
        println("it = $it")
        str1.length
    }

    println("result1 = $result1")
    println()

    val str2: String? = "hello ~ "

    val result2 = str2?.let{
        println("it = $it")
        str2.uppercase()
    }

    println("result2 = $result2")
}


fun exam04(){
    val str: String? = "대한민국~!!"
    val result = str?.let{
        println("it = $it")

        val hello: String? = "hello ~ "
        hello?.let{
            val HELLO: String? = it.uppercase()
            println("str, hello 모두 null 이 아님")
        }

        "world".uppercase()
    }

    println("result = $result")
}


fun exam05(){
    val str: String? = "대한민국~!!"
    val result = str?.let{
        println("it = $it")

        val hello: String? = "hello ~ "
        val HELLO: String? = "HELLO ~ "

        if(!hello.isNullOrBlank() && !HELLO.isNullOrBlank()){
            println("hello, HELLO 모두 null 이 아님")
        }

        "world".uppercase()
    }

    println("result = $result")
}