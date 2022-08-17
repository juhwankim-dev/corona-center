package com.example.domain.util

data class Result<out T>(
    val status: Status,
    var data: @UnsafeVariance T?,
    val message:String?
){
    companion object{
        fun <T> success(data: T?): Result<T> {
            return Result(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Result<T> {
            return Result(Status.ERROR, data, msg)
        }

        fun <T> fail(): Result<T> {
            return Result(Status.FAIL, null, null)
        }
    }
}