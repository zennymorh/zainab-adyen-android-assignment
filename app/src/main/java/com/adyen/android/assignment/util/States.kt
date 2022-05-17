package com.adyen.android.assignment.util

data class State<T>(
    val message: String? = null,
    val data: T? = null,
    val isLoading: Boolean = false,
    val success: Boolean = false
) {
    companion object {
        fun <T> error(
            message: String
        ): State<T> {
            return State(
                message = message,
                data = null,
                success = false
            )
        }

        fun <T> data(
            message: String? = null,
            data: T? = null,
            success: Boolean = true
        ): State<T> {
            return State(
                message = message,
                data = data,
                success = success
            )
        }

        fun <T> loading() = State<T>(
            isLoading = true
        )
    }
}