package io.testprj.kopring_webflux.config

import com.fasterxml.jackson.databind.ObjectMapper
import io.testprj.kopring_webflux.global.exception.ErrorResponse
import io.testprj.kopring_webflux.global.exception.ServerException
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

//@RestControllerAdvice
@Configuration
class GlobalExceptionHandlerConfig (private val objectMapper: ObjectMapper) : ErrorWebExceptionHandler{

    // 일반 Webflux 코드
    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        val errorResponse = if(ex is ServerException){
            ErrorResponse(success = "false", errorMessage = ex.message)
        } else {
            ErrorResponse(success = "false", errorMessage = "정의되지 않은 예외 발생")
        }

        with(exchange.response){
            statusCode = HttpStatus.OK
            headers.contentType = MediaType.APPLICATION_JSON
            val dataBuffer = bufferFactory().wrap(objectMapper.writeValueAsBytes(errorResponse))
            return writeWith(dataBuffer.toMono())
        }
    }

    // 아래는 coroutine 을 사용할 경우의 로직
//    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> = mono {
//        val errorResponse = if(ex is ServerException){
//            ErrorResponse(success = "false", errorMessage = ex.message)
//        } else {
//            ErrorResponse(success = "false", errorMessage = "정의되지 않은 예외 발생")
//        }
//
//        with(exchange.response){
//            statusCode = HttpStatus.OK
//            headers.contentType = MediaType.APPLICATION_JSON
//
//            val dataBuffer = bufferFactory().wrap(objectMapper.writeValueAsBytes(errorResponse))
//            writeWith(dataBuffer.toMono()).awaitSingle()
//        }
//    }



//    @ExceptionHandler(ServerException::class)
//    fun thatIsNotABookException(ex : ServerException) : ErrorResponse{
//        return ErrorResponse("false", ex.message)
//    }
//
//    @ExceptionHandler(Exception::class)
//    fun notDefinedException(ex: Exception) : ErrorResponse{
//        return ErrorResponse(success = "false", "정의되지 않은 예외 발생")
//    }
}