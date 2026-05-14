package xyz.luko.server.error

object ErrorCode {
    const val INTERNAL_ERROR = "INTERNAL_ERROR"
    const val BAD_REQUEST = "BAD_REQUEST"
    const val NO_RESULT = "NO_RESULT"
    const val MISSING_PARAM = "MISSING_PARAM"

    const val DEFAULT_INTERNAL_MESSAGE = "Unexpected error"
    const val DEFAULT_INVALID_PARAMETER = "Invalid request parameters"
}