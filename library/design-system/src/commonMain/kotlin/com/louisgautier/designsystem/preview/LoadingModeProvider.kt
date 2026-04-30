package com.louisgautier.designsystem.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class LoadingModeProvider : PreviewParameterProvider<LoadingMode> {
    override val values: Sequence<LoadingMode>
        get() = sequenceOf(LoadingMode.LOADING, LoadingMode.ERROR, LoadingMode.SUCCESS)
}
