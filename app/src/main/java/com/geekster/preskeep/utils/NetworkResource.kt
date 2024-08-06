package com.geekster.preskeep.utils
//
sealed class NetworkResource<out T: Any?>(val data : T? = null, val message:String? = null){
    class Success<T>(data : T) : NetworkResource<T>(data)
    class Error<T>(message: String, data: T? = null): NetworkResource<T>(data, message)
    class Loading<T> : NetworkResource<T>()
}