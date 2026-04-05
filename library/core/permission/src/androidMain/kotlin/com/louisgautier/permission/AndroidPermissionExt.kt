package com.louisgautier.permission

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.READ_MEDIA_VIDEO
import android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
import android.app.Activity
import android.os.Build
import androidx.activity.result.ActivityResult

internal fun ActivityResult.toPermissionResult(): PermissionResult {
    return if (resultCode == Activity.RESULT_OK) PermissionResult.GRANTED else PermissionResult.DENIED
}

internal fun PermissionType.toAndroidPermission(): Array<String> = when (this) {
    PermissionType.GALLERY -> galleryPermissions()
    else -> TODO()
}

internal fun galleryPermissions(): Array<String> = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE -> {
        arrayOf(READ_MEDIA_IMAGES, READ_MEDIA_VIDEO, READ_MEDIA_VISUAL_USER_SELECTED)
    }

    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
        arrayOf(READ_MEDIA_IMAGES, READ_MEDIA_VIDEO)
    }

    else -> arrayOf(READ_EXTERNAL_STORAGE)
}