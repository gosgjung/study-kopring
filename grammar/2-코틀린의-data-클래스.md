# 코틀린의 Data 클래스

데이터를 표현하는 것만을 목적으로 하는 객체를  `data` 클래스라고 부른다. 흔히 `dto` 라고 부르는 클래스는 코틀린의 `data` 클래스로 표현 가능하다.<br>

<br>

# data 클래스의 장점

java 14 프리뷰 이후로 등장한 개념인 `record` 와 유사한 개념이다. Lombok 의 `@Data` 어노테이션과 비슷한 기능을 가지고 있다.<br>

data 클래스를 선언하면 아래의 함수들을 컴파일러가 자동으로 만들어준다.

- equals()
- hashCode()
- toString()
- componentN(), copy()

<br>

데이터 저장을 목적으로 하는 클래스는 일반적으로 3 가지 함수 `toString()`, `equals()`, `hashCode()` 를 재정의한다. 그런데, data 클래스는 `toString()`, `equals()`, `hashCode()` 등의 기능과 `componentN()`, `copy()` 기능을 제공해준다. 이 점은 확실히 장점이다.<br>

하지만, data 클래스 역시 단점이 있다. 필드가 많을 경우 `equals()`, `hashCode()` 에 정의되어야 하는 필드 비교가 그 많큼 많아지고 복잡해진다는 점이다.<br>

<br>

# data 클래스의 단점

data 클래스의 단점은 모든 필드를 equals(), hashCode() 화 하면 조금 무거워진다는 단점이 있다. <br>

내피셜이지만, 이런 경우는 아래의 두가지 방식으로 equals(), hashCode()가 무거워지는 현상을 해결할 수 있을 것 같다.

- equals() 와 hashCode() 의 재정의를 필수적인 필드에만 제한적으로 사용해서 재정의를 하거나,
- equals(), hashCode() 메서드의 재정의 없이 data 클래스에 필수적인 필드만 존재하도록 객체 디자인을 하는 방식

<br>

# equals(), hashCode()

## equals() 재정의를 하지 않으면?

equals() 는 타입이 같은 서로 다른 두 인스턴스의 비교를 위해, equals()를 사용한다. 이때 재정의 없이 내장된 equals() 를 그대로 사용하면, 객체의 hashCode()를 비교하므로 다르다고 판정한다.<br>

e.g. Java 를 예로 들어보면 ... (뜬금없이 java 를 사용하긴 했다)

```java
public class Fruit{
    private String kind;
    private Integer price;

    public Fruit(String kind, Integer price){
        this.kind = kind;
        this.price = price;
    }
}

@Test
public void 객체식별성_확인해보기(){
    Fruit apple1 = new Fruit("Apple", 1000);
    Fruit apple2 = new Fruit("Apple", 1000);

    System.out.println(apple1.equals(apple2));
    System.out.println(apple1 == apple2);
    assertThat(apple1.equals(apple2)).isFalse();
    assertThat(apple1 == apple2).isFalse();
}
```

<br>

출력결과

```plain
false
false
```

<br>

출력결과에서 보듯, equals(), hashCode() 재정의 없이 그대로 사용하면 `java.lang.Object` 의 `hashCode()` 를 기반으로 `equals()` 연산을 수행한다.<br>

같은 타입 기반으로 생성된 인스턴스이고, 필드 값도 모두 동일하더라도 hashCode() 의 결과값이 다르게 나오고, 다른 메모리 상의 서로 다른 객체로 판단하기에 equals()의 결과는 `false` 를 리턴하게 된다.<br>

<br>

## lombok, java record 의 equals(), hashCode()

일반적으로 `equals()` 메서드는 타입이 같은 두 인스턴스가 논리적으로 값이 같은지 비교를 위해 사용한다. 흔히 알려진 롬복의 `@Data` 내에는 `@EqualsAndHashCode` 가 내장되어 있다. java 14 이후로 도입된 `record` 에도 `equals()`, `hashCode()` 가 내장되어 있다.<br>

<br>

## kotlin 일반 클래스의 equals(), hashCode()

equals() 메서드를 재정의할 때는 반드시 hashCode() 를 재정의해야 한다. (JVM 언어 공통사항)

<br>

예를 들면 Person 타입 객체 A, B 가 있을때, A.equals(B) == true 이면, A 의 hashCode, B의 hashCode가 모두 같아야 한다.<br>

만약 객체 두개의 비교 결과 equals 의 결과값은 true 인데, 두 객체의 hashCode가 서로 다르다면, HashMap, HashSet 과 같은 자료구조에서는 정상적으로 동작하지 못한다.<br>

(hashCode가 다른 객체가 equals 는 true인상태)<br>

<br>

> 예제1) equals()를 재정의했지만, hashCode()를 재정의하지 않은 경우

```kotlin
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

fun main(){
    val person11 = Person1(name = "Great", age = 31)
    val person12 = Person1(name = "Great", age = 31)

    println(person11 == person12)   // true

    val set = hashSetOf(person11)
    println(set.contains(person12)) // false
}
```

<br>

출력결과

```plain
true
false
```

<br>

출력결과를 해석해보면 이렇다. 먼저 아래의 구문을 보자.

```kotlin
println(person11 == person12) // true
```

<br>

equals() 메서드를 재정의했기에, `name`, `age` 필드에 대한 동치성 검사를 수행하도록 했기 때문에 두 객체가 논리적으로 같은지를 체크하는 것이 가능하다.<br>

하지만, hashCode() 하지 않았다. 따라서 java.lang.Object 의 hashCode()를 사용하게 되는데, hashCode() 가 생성하는 hashCode 는 서로 다르다.<br>

```kotlin
val set = hashSetOf(person11)
println(set.contains(person12))
```

`person11` 과 `person12` 의 hashCode 가 서로 다르기 때문에 `HashSet` 내에 들어간 `person11` , `person12` 은 서로 다른 것으로 인식된다.<br>

<br>

> 예제 2) equals(), hashCode() 를 재정의할 경우

클래스 `Person2` 에 equals() 메서드, hashCode() 를 모두 재정의해줬다.

```kotlin
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
```

<br>

`name` , `age`  에 대해서 의미가 부여된 hashCode가 생성되도록 hashCode() 메서드를 재정의했다. 이 hashCode()를 기반으로 `person21` , `person22` 가 HashSet 안에서 같은 객체로 인식되는지 테스트하는 아래의 코드를 보자.<br>

```kotlin
fun test2(){
    val person21 = Person2(name = "Great", age = 31)
    val person22 = Person2(name = "Great", age = 31)

    println(person21 == person22)

    val set = hashSetOf(person21)
    println(set.contains(person22))
}
```

<br>

위의 코드를 실행시킨 결과는 아래와 같다.

```plain
true
true
```

<br>

# data 클래스의 equals, hashCode

<br>

# data 클래스의 부가적인 함수들

<br>

## toString()

<br>

## copy()

<br>

## componentN()

<br>









