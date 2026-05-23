package xyz.luko.server.data.storage

import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import xyz.luko.server.supabase.SupabaseClientMode

val storageModule = module {
    single {
        DefaultStorageSource(
            get(named(SupabaseClientMode.ADMIN))
        )
    } bind StorageSource::class
}
