package xyz.luko.permission

class JvmPermissionsManager : PermissionsManager {
    override fun isPermissionGranted(permission: PermissionType): PermissionResult =
        PermissionResult.GRANTED

    override suspend fun requestPermission(
        permission: PermissionType,
        callback: PermissionCallback,
    ) {
        callback.onPermissionStatus(permission, PermissionResult.GRANTED)
    }
}
