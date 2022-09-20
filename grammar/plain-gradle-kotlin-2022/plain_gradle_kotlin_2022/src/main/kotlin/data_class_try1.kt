import java.math.BigDecimal

data class Person(val name: String, val age: Int)


class Person1(val name: String, val age: Int){
    override fun equals(other: Any?): Boolean {
        if(this === other) return true
        if(javaClass != other?.javaClass) return false

        other as Person1

        if(name != other.name) return false
        if(age != other.age) return false

        return true
    }
}

class Person2(val name: String, val age: Int){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Person2

        if (name != other.name) return false
        if (age != other.age) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + age
        return result
    }
}

fun main(){
//    test1()
//    test2()
//    test3()
//    test4()
    test5()
}

fun test1(){
    val person11 = Person1(name = "Great", age = 31)
    val person12 = Person1(name = "Great", age = 31)

    println(person11 == person12)   // true

    val set = hashSetOf(person11)
    println(set.contains(person12)) // false
}

fun test2(){
    val person21 = Person2(name = "Great", age = 31)
    val person22 = Person2(name = "Great", age = 31)

    println(person21 == person22)

    val set = hashSetOf(person21)
    println(set.contains(person22))
}

fun test3(){
    val person31 = Person(name = "Great", age = 33)
    val person32 = Person(name = "Great", age = 33)

    println(person31 == person32)

    val set = hashSetOf(person31)
    println(set.contains(person32))
}

class Book1(val name : String, val price : BigDecimal)

fun test4(){
    val book : Book1 = Book1(name = "주식공부 5일 완성", BigDecimal.valueOf(12600))
    println(book)
}

data class Book2(val name : String, val price : BigDecimal)
fun test5(){
    val book = Book2(name = "주식공부 5일 완성", BigDecimal.valueOf(12600))
    println(book)
}