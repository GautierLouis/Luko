package com.louisgautier.firebase.event

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

object Tracker {
    private val _events = Channel<TrackingEvent>(capacity = Channel.Factory.UNLIMITED)
    val events = _events.receiveAsFlow()

    fun track(event: TrackingEvent) {
        _events.trySend(event)
    }
}