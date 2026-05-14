package xyz.luko.app

import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import xyz.luko.permission.PermissionActivityResultObserver
import xyz.luko.utils.IntentActivityResultObserver

internal class DefaultActivityObserver(
    private val intentObserver: IntentActivityResultObserver,
    private val permissionObserver: PermissionActivityResultObserver,
) : ActivityObserver {
    override fun setIntentLauncher(launcher: ActivityResultLauncher<Intent>) {
        intentObserver.setLauncher(launcher)
    }

    override fun setPermissionLauncher(launcher: ActivityResultLauncher<Array<String>>) {
        permissionObserver.setLauncher(launcher)
    }

    override fun setIntentResult(result: ActivityResult) {
        intentObserver.onActivityResult(result)
    }

    override fun setPermissionResult(result: Map<String, Boolean>) {
        permissionObserver.onActivityResult(result)
    }
}
