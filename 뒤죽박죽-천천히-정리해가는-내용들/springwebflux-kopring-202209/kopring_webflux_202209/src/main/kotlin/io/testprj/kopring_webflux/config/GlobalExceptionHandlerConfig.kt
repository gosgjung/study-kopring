package io.testprj.kopring_webflux.config

import com.fasterxml.jackson.databind.ObjectMapper
import io.testprj.kopring_webflux.global.exception.ErrorResponse
import io.testprj.kopring_webflux.global.exception.ServerException
import io.testprj.kopring_webflux.global.exception.ThatIsNotABookException
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.mono
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Configuration
//@RestControllerAdvice
class GlobalExceptionHandlerConfig (private val objectMapper: ObjectMapper) : ErrorWebExceptionHandler{

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