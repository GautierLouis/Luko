package xyz.luko.server.error

class ParameterExtractionException(val code: String, message: String) : Exception(message)
class HeaderExtractionException(val code: String, message: String) : Exception(message)

fun missingParameter(name: String) =
    ParameterExtractionException(ErrorCode.MISSING_PARAM, "Missing parameter: '$name'")

fun missingHeader(name: String) =
    HeaderExtractionException(ErrorCode.MISSING_HEADER, "Missing header: '$name'")
