# Kotlin 처음 사용시 멘붕오는 람다문법 (1) map, filter 사용할 때 - 괄호는 어떻게 지정해?

**처음** 사용할 때에만 멘붕이 올뿐이다. 나중에 다른 언어를 사용하게 될수도 있고, 다시 돌아와서 kotlin 사용할 때 멘붕이 한번 더 올 수 있으니, 그냥 한번 정리를 해봤다.

<br>

제목도 자극적으로 정했으니 나중에 이런 문서가 있다는 것 쯤은 까먹지 않겠지 ㅋ<br>

<br>

# e.g. 1) `{객체명 -> 변환로직}`

```kotlin
@Service
class UserService(
    private val userRepository: UserRepository,
){
    
    @Transactional(readOnly = true)
    fun getUsers() : List<UserResponse> {
        return userRepository.findAll()
        	.map { user -> UserResponse(user) }
    }
    
}

```

<br>



# e.g. 2) `{변환로직(it)}`

```kotlin
@Service
class UserService(
    private val userREpository: UserRepository,
){
    
    @Transactional(readOnly = true)
    fun getUsers() : List<UserResponse> {
        return userRepository.findAll()
        	.map{ UserResponse(it) }
    }
    
}
```

<br>



# e.g. 3) 메서드 레퍼런스

1\)

```kotlin
@Service
class UserService(
    private val userRepository: UserRepository,
){
    
    @Transactional(readOnly = true)
    fun getUsers() : List<UserResponse> {
        return userRepository.findAll()
        	.map(::UserResponse)
    }
    
}
```

<br>

2\)

```kotlin
@Service
class UserService(
    private val userRepository: UserRepository,
){
    
    @Transactional(readOnly = true)
    fun updateUserName(request: UserUpdateRequest){
        val user = userRepository
        	.findById(request.id)
        	.orElseThrow(::IllegalArgumentException)
        
        user.updateName(request.name)
    }
    
}
```

<br>

<br>