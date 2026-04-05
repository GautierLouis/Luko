package com.louisgautier.permission

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.louisgautier.logger.AppLogger
import com.louisgautier.utils.PermissionActivityResultObserver

class AndroidPermissionsManager(
    private val context: Context,
    private val activityResultObserver: PermissionActivityResultObserver
) : PermissionsManager {

    override fun isPermissionGranted(permission: PermissionType): PermissionResult {
        return when {
            checkSelf(permission.toAndroidPermission()) -> {
                PermissionResult.GRANTED
            }

            shouldRationale(permission.toAndroidPermission()) -> {
                AppLogger.w("[PERMISSION] : A rationale must be asked for this permission ($permission)")
                PermissionResult.DENIED
            }

            else -> {
                PermissionResult.DENIED
            }
        }
    }

    private fun checkSelf(permissions: Array<String>): Boolean {
        return permissions.map {
            context.checkSelfPermission(it)
        }.all {
            it == PackageManager.PERMISSION_GRANTED
        }
    }

    //TODO (implement Rationale?)
    private fun shouldRationale(permissions: Array<String>): Boolean {
        return permissions.map {
            ActivityCompat.shouldShowRequestPermissionRationale(
                context as Activity,
                it
            )
        }.any { it }
    }

    override suspend fun requestPermission(
        permission: PermissionType,
        callback: PermissionCallback
    ) {
        val result = activityResultObserver.launchIntent(permission.toAndroidPermission())
        callback.onPermissionStatus(permission, result.toPermissionResult())
    }
}