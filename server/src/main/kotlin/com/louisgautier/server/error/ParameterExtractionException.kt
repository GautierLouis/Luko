package com.louisgautier.server.error

class ParameterExtractionException(val code: String, message: String) : Exception(message)

fun missingParameter(name: String) =
    ParameterExtractionException(ErrorCode.MISSING_PARAM, "Missing parameter: '$name'")