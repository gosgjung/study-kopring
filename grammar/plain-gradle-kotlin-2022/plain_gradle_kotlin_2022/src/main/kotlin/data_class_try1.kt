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
    test2()
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