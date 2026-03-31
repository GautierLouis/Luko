package com.louisgautier.designsystem.components.metrics.attrs

import com.louisgautier.designsystem.components.metrics.attrs.SessionStatistic

sealed class MetricItem {
    data class AppMetric(val metric: AppStatistic, val value: String) : MetricItem()
    data class SessionMetric(val metric: SessionStatistic, val value: String) : MetricItem()
}