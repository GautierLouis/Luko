package com.louisgautier.permission

class JvmPermissionsManager: PermissionsManager {
    override fun isPermissionGranted(permission: PermissionType): PermissionResult {
        return PermissionResult.GRANTED
    }

    override suspend fun requestPermission(
        permission: PermissionType,
        callback: PermissionCallback
    ) {
        callback.onPermissionStatus(permission, PermissionResult.GRANTED)
    }
}