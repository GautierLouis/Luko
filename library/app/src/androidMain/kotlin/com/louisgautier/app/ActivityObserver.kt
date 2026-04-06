package com.louisgautier.app

import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher

interface ActivityObserver {
    fun setIntentLauncher(launcher: ActivityResultLauncher<Intent>)
    fun setPermissionLauncher(launcher: ActivityResultLauncher<Array<String>>)
    fun setIntentResult(result: ActivityResult)
    fun setPermissionResult(result: Map<String, Boolean>)
}