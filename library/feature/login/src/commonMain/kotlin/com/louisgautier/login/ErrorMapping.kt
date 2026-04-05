package com.louisgautier.login

import com.louisgautier.utils.AppErrorCode
import learn_chinese.library.feature.login.generated.resources.Res
import learn_chinese.library.feature.login.generated.resources.error_invalid_credentials
import learn_chinese.library.feature.login.generated.resources.error_network
import learn_chinese.library.feature.login.generated.resources.error_server
import learn_chinese.library.feature.login.generated.resources.error_unknown
import org.jetbrains.compose.resources.StringResource


fun AppErrorCode.toResourceString(): StringResource = when (this) {
    AppErrorCode.AppError -> Res.string.error_unknown // or your real key
    AppErrorCode.NetworkError -> Res.string.error_network
    AppErrorCode.ServerError -> Res.string.error_server
    AppErrorCode.InvalidCredentials -> Res.string.error_invalid_credentials
    else -> Res.string.error_unknown
}
