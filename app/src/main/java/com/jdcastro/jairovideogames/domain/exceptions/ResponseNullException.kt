package com.jdcastro.jairovideogames.domain.models.exceptions

class ResponseNullException : Exception(RESPONSE_IS_NULL) {
    companion object {
        const val RESPONSE_IS_NULL = "Response is null"
    }
}