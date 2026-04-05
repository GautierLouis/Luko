package com.louisgautier.permission

import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val permissionPlatformModule: Module = module {
    singleOf(::AndroidPermissionsManager) bind PermissionsManager::class
}