package xyz.luko.server.data.database

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import xyz.luko.server.data.database.dao.CharacterDao
import xyz.luko.server.data.database.dao.CharacterFsrsDao
import xyz.luko.server.data.database.dao.DefaultCharacterDao
import xyz.luko.server.data.database.dao.DefaultCharacterFsrsDao
import xyz.luko.server.data.database.dao.DefaultDictionaryDao
import xyz.luko.server.data.database.dao.DefaultPrepopulateDao
import xyz.luko.server.data.database.dao.DefaultSeedDao
import xyz.luko.server.data.database.dao.DefaultSessionDao
import xyz.luko.server.data.database.dao.DefaultUserDao
import xyz.luko.server.data.database.dao.DictionaryDao
import xyz.luko.server.data.database.dao.PrepopulateDao
import xyz.luko.server.data.database.dao.SeedDao
import xyz.luko.server.data.database.dao.SessionDao
import xyz.luko.server.data.database.dao.UserDao

val databaseModule = module {
    singleOf(::DefaultDatabase) bind Database::class
    singleOf(::DefaultDictionaryDao) bind DictionaryDao::class
    singleOf(::DefaultSeedDao) bind SeedDao::class
    singleOf(::DefaultPrepopulateDao) bind PrepopulateDao::class
    singleOf(::DefaultUserDao) bind UserDao::class
    singleOf(::DefaultCharacterDao) bind CharacterDao::class
    singleOf(::DefaultCharacterFsrsDao) bind CharacterFsrsDao::class
    singleOf(::DefaultSessionDao) bind SessionDao::class
}
