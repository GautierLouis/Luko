package com.louisgautier.tracking

import com.louisgautier.logger.AppLogger
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

object Tracker {
    private val _events = Channel<TrackingEvent>(capacity = Channel.UNLIMITED)
    val events = _events.receiveAsFlow()

    fun track(event: TrackingEvent) {
        AppLogger.d(
            tag = "Tracking event",
            message = "${event.key} : ${event.params}",
        )
        _events.trySend(event)
    }
}