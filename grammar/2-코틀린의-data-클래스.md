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

# equals()

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

## kotlin data 클래스의 equals(), hashCode()



<br>