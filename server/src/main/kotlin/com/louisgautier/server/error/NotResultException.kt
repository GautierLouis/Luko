package com.louisgautier.server.error

class NotResultException(val code: String, message: String) : Exception(message)

fun dictionaryNotFound(code: Int) =
    NotResultException(ErrorCode.NO_RESULT, "No dictionary found for '${Char(code)}' ($code)")

fun graphicNotFound(code: Int) =
    NotResultException(ErrorCode.NO_RESULT, "No Graphic found for '${Char(code)}' ($code)")
