package xyz.luko.server.data.storage

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val storageModule = module {
    singleOf(::DefaultStorageSource) bind StorageSource::class
}
