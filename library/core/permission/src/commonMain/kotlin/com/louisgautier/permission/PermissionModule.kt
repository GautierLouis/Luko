package com.louisgautier.permission

import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val permissionModule = module {
    includes(permissionPlatformModule)
    singleOf(::PermissionHelper)
}
internal expect val permissionPlatformModule: Module