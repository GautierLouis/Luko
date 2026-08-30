package xyz.luko.apicontracts.dto

import kotlinx.serialization.Serializable

@Serializable
enum class RecognitionResult { FAILURE, SUCCESS, PARTIAL }
