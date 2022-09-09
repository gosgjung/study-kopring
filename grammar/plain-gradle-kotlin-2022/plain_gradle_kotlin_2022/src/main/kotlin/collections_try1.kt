import java.math.BigDecimal
import java.util.*

fun main(){
    // 불변 리스트
    val immutablePlayerList = listOf("황희찬", "황의조", "황인범", "손흥민")
    // listOf(E,E...) 는 불변 컬렉션을 만들기 위해 사용한다.
    // List<E> 는 add(E)과 같은 메서드를 제공하지 않는다.
    // List<E> 가 extends 하고 있는 Collections 역시 수정을 위한 메서드는 따로 제공하지 않는다.

    // 변경할 수 있는 리스트
    val mutablePlayerList1 = mutableListOf<String>("황희찬", "황의조")
    mutablePlayerList1.add("황인범")
    mutablePlayerList1.add("손흥민")
    mutablePlayerList1.add("김민재")
    // mutableListOf<E>(...) 는 mutable 한 컬렉션의 한 종류인 MutableList<E> 객체를 생성한다.
    // MutableList<E> 는 MutableCollection<E> 를 extends 하고 있다.
    // MutableCollection<E>는 요소를 추가/삭제할 수 있는 메서드인 add,remove,addAll 등의 메서드를 제공한다.(=Mutable하다)

    // 위의 구문은 아래와 같이 apply 라는 inline 함수를 통해 더 간결하게 변경할 수 있다.
    val mutablePlayerList2 = mutableListOf<String>("황희찬", "황의조").apply{
        add("황인범")  // = this.add("황인범")
        add("손흥민")  // = this.add("손흥민")
        add("김민재")  // = this.add("김민재")
    }

    mutablePlayerList2.add("김영광")

    println("mutablePlayerList >>> ")
    println(mutablePlayerList2)


    // immutable Set
    // 불변 Set 자료형
    val immutableSet = setOf(1,2,3,4)


    // mutableSet
    // 변경가능한 Set 자료형
    // 위에서 설명했듯 mutableSet.add(1) mutableSet.add(2) mutableSet.add(3) 등과 같은 식으로 표현하지 않고
    // apply 라는 inline 함수를 통해 더 간결하게 변경할 수 있다.
    val mutableSet = mutableSetOf<Int>().apply{
        add(1)  // = this.add(1)
        add(2)  // = this.add(2)
        add(3)  // = this.add(3)
        add(4)  // = this.add(4)
    }

    // 불변 Map
    val numberMap = mapOf("one" to 1, "tow" to 2)
    // to: to 는 키워드는 아니다. 코틀린에서 말하는 중위표현식, 또는 infix notation 이라고도 불리는 표현식이다.


    // 변경 가능한 Map
    // 코틀린에서는 map.put("키", 값) 형식 대신 아래와 같이 map["키"] = 값 의 형식으로 초기화 할 수 있다.
    val mutableNumberMap = mutableMapOf<String, Int>()
    mutableNumberMap["one"]     = 1     // mutableNumberMap.put("one", 1)
    mutableNumberMap["two"]     = 2     // mutableNumberMap.put("two", 2)
    mutableNumberMap["three"]   = 3     // mutableNumberMap.put("three", 3)


    println("mutable 테스트 / 일반 라인으로 초기화 >>> ")
    val tickerPriceMap1 = mutableMapOf<String, BigDecimal>()
    tickerPriceMap1["AAPL"] = BigDecimal.valueOf(154.46)
    // = tickerPriceMap.put("MSFT", BigDecimal.valueOf(258.52))

    tickerPriceMap1["MSFT"] = BigDecimal.valueOf(258.52)
    // = tickerPriceMap.put("AAPL", BigDecimal.valueOf(154.46))

    tickerPriceMap1["GOOGL"] = BigDecimal.valueOf(108.38)
    // = tickerPriceMap.put("GOOGL", BigDecimal.valueOf(108.38))

    println(tickerPriceMap1)
    println()

    println("mutable 테스트 / apply 를 이용해 초기화 >>> ")
    val tickerPriceMap2 = mutableMapOf<String, BigDecimal>().apply{
        this["MSFT"] = BigDecimal.valueOf(258.52)
        this["AAPL"] = BigDecimal.valueOf(154.46)
        this["GOOGL"] = BigDecimal.valueOf(108.38)
    }

    println(tickerPriceMap2)
    println()

    println("immutable 테스트 / 중위표현식(infix notation) 'to' 를 이용해 초기화 >>> ")
    val tickerImmutableMap = mapOf(
        "MSFT" to BigDecimal.valueOf(258.52),
        "AAPL" to BigDecimal.valueOf(154.46),
        "GOOGL" to BigDecimal.valueOf(108.38)
    )

    println(tickerImmutableMap)
    println()


    /////////// 컬렉션 빌더 // 겪어보니 특이하다.
    // 컬렉션 빌더는 내부에서는 mutable 로 동작. 반환값은 immutable 로 변환되어 반환.
    // @BuilderInference builderAction: MutableList<E>.() -> Unit

    // buildList :: 컬렉션 빌더는 내부에서는 mutable, 반환은 immutable
    // 아래는 내피셜
    // 참고) buildList 는 MutableList<E> 를 받는 함수인데, 리턴 값은 List<Int> 인 이유는 아래와 같다.
    // = buildList 내부에서는 익명으로 생성한 MutableList<Int> 객체를 통해 inline 함수 내의 식을 통해 mutableList 내의 값들을 초기화하고,
    // = return 시에는 코틀린에서의 불변 타입인 List<Int> 타입으로 변환해서 리턴한다.
    val numberList : List<Int> = buildList{
        add(1)
        add(2)
        add(3)
        add(4)
    }

    // 위와 같이 buildList 인라인 함수로 객체가 생성된 후에는 값을 새로 추가할 수 없다.
    // 반환된 객체가 불변객체이기 때문
    // 아래 코드는 컴파일 에러
    // numberList.add(5)


    // linkedList.apply
    val linkedList = LinkedList<Int>().apply{
        addFirst(3)
        add(2)
        addLast(1)
    }

    // arrayList.apply
    val arrayList = ArrayList<Int>().apply{
        add(1)
        add(2)
        add(3)
    }

    // iterator
    // 코틀린의 컬렉션은 자바에서처럼 Iterable 의 구현체이기 때문에 Iterator 를 사용해 순차적으로 반복하는 것 역시 가능
    val mutableTickerList1 = mutableListOf<String>().apply{
        add("MSFT")
        add("AAPL")
        add("QQQ")
        add("GOOGL")
        add("AMZN")
    }

    println("이터레이터 사용해보기")
    val iterator = mutableTickerList1.iterator()
    while(iterator.hasNext()){
        println(iterator.next())
    }
    println()

    println("for-each 를 사용해 순회해보기")
    for(ticker in mutableTickerList1){
        println(ticker)
    }
    println()

    println("인라인 forEach 사용해 순회해보기... forEach 내부에서는 it 라는 변수를... 사용...")
    mutableTickerList1.forEach{
        println(it)
    }
    println()

    println()
    println("map 함수 없이, 리스트를 변환해서 새로운 소문자 티커 리스트로 만들어보기")
    val lowerCaseList1 = mutableListOf<String>()
    for(ticker in mutableTickerList1){
        lowerCaseList1.add(ticker.lowercase())
    }
    println("변환결과 >> $lowerCaseList1")

    println()
    println("map 함수를 통해, 리스트를 소문자의 새로운 티커리스트로 변환해보기")
    val lowerCaseList2 = mutableTickerList1.map{it.lowercase()}
    println("변환결과 >> $lowerCaseList2")
    println()

    println()
    println("filter 함수 없이 for문으로 filter 함수 효과내보자")
    val mutableTickerList3 = mutableListOf<String>().apply{
        add("MSFT")
        add("AAPL")
        add("TSLA")
    }
    val filteredTickerList3 = mutableListOf<String>()
    for(ticker in mutableTickerList3){
        if(ticker.contains("A"))
            filteredTickerList3.add(ticker)
    }
    println("filter 결과 >> $filteredTickerList3")

    println()
    println("filter 함수로 원하는 조건값 필터링하기")
    val filteredTickerList4 = filteredTickerList3.filter{it.contains("A")}
    println("filter 결과 >> $filteredTickerList4")

    //
    /**
     * Java 8 이후의 Stream 에서의 filter 와 다른점.
     *  : JAVA 8에서의 Stream 은 최종연산자(Terminal Operator)를 만나야 해당 filter 가 속한 Stream 이 실행된다는 점이 코틀린과 다르다.
     *
     *  코틀린에는 Stream 과 비슷한 연산은 없는지?
     *  코틀린에는 Stream 대신에 sequence 라는 것이 존재한다.
     *  아래의 예제를 보자.
     */

    val filteredSequence1 = mutableTickerList3
        .asSequence()
        .filter{it.contains("A")}

    println()
    println("asSequence()로 시퀀스만 생성했을 때의 결과값 : $filteredSequence1")
    println("toList 같은 종단 연산이 없으면, 아무일도 일어나지 않는다")

    val filteredSequence2 = mutableTickerList3
        .asSequence()
        .filter{it.contains("A")}
        .toList()

    println()
    println("asSequence()로 시퀀스만 생성했을 때의 결과값 : $filteredSequence2")

    println()
    println("toList() 같은 종단연산으로 실행하면, 실제 필터링 연산을 수행한다.")
    val filteredSequence3 = mutableTickerList3
        .asSequence()
        .filter{it.contains("A")}
        .toList()

    println("종단연산까지 마무리한 후의 결과값 : $filteredSequence3")


    // 인라인함수를 여러개 붙여서 사용하면, 내부적으로는 객체가 인라인 함수 하나마다 생성된다.
    val mutableTickerList5 = mutableListOf<String>().apply{add("MSFT"); add("TQQQ"); add("AAPL"); add("AMD"); add("TSM"); add("TSLA")}


    // 아래와 같이 인라인 함수를 여러개 붙여서 파이프라이닝 하면, 내부적으로는 객체가 인라인 함수하나마다 각각 생성된다.
    val filteredList5 = mutableTickerList5
        .filter{it.contains("T")} // collection 하나가 새로 생성됐다.
        .filter{it.contains("S")} // 또 다른 collection 하나가 또 새로 생성됐다.

    println(filteredList5)

    // 위의 구문은 데이터의 양이 많을 때는 잘못하면 Out of Memory 이슈에 직면할 수도 있다.
    // 조건 값 계산할 때마다 컬렉션, 즉, 리스트를 매번 새로 생성하기 때문이다.

    // 이렇게 인라인 함수를 여러번 타게 될 경우는 stream() 연산 처럼 asSequence() 를 사용해주자.
    // asSequence() 를 사용해서, 연산이 끝나는 곳에서 toList() 와 같은 종단(Terminal)연산을 수행해주도록 정의해두면
    // 위와 같이 filter{...} 와 같은 인라인 함수가 실행 될때마다 객체를 생성하는 것이 아니라
    // 종단연산이 이뤄질 때 최종 컬렉션을 생성하기 때문에 메모리에 대한 이슈를 줄일 수 있다.

    // 이런 것으로 인한 단점은, 매 연산의 마지막마다 toSet(), toList() 와 같은 종단 연산을 실행해줘야 한다는 점이다.
    // 벤치마크를 통한 상대적인 경험적인 사례들은 대부분 5만건 ~ 10만건 의 데이터들의 경우 오히려 시퀀스 api 를 사용하지 않는 것이
    // 성능상으로는 더 낫다고 한다.
    // 아래는 위의 예제를 sequence api 로 변환한 예제
    println()
    val filteredList6 = mutableTickerList5
        .asSequence()
        .filter{it.contains("T")}
        .filter{it.contains("S")}
        .toList()

    println(filteredList6)

}