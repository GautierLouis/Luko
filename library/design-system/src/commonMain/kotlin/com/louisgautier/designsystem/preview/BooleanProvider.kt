package com.louisgautier.designsystem.preview

import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

class BooleanProvider() : PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean>
        get() = sequenceOf(false, true)
}