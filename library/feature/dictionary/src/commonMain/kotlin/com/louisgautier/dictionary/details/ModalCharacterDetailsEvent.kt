package com.louisgautier.dictionary.details

internal sealed class ModalCharacterDetailsEvent {
    data object OnRetry : ModalCharacterDetailsEvent()

    data object OnPractice : ModalCharacterDetailsEvent()
}
