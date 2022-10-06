package io.testprj.kopring_webflux.book.dto

data class BookResponse <T> (
    val body : T,
    val success : Boolean = true,
    val errorMessage : String = "NO_ERROR"
){
    companion object{
        fun <T> badResponse(body : T, badMessage: String) : BookResponse<T>{
            return BookResponse(body, success = false, errorMessage = badMessage)
        }

        fun <T> okResponse(body : T, message: String) : BookResponse<T>{
            return BookResponse(body, success = true, errorMessage = message)
        }
    }
}