package com.sendmystatus.oeventapp.di

import com.sendmystatus.oeventapp.data.local.database.OEventDatabase
import com.sendmystatus.oeventapp.data.local.database.getRoomDatabase
import com.sendmystatus.oeventapp.data.local.datasource.*
import org.koin.dsl.module

val databaseModule = module {
    single { getRoomDatabase(get()) }
    
    single { get<OEventDatabase>().attendeeDao() }
    single { get<OEventDatabase>().eventDao() }
    single { get<OEventDatabase>().merchantDao() }
    single { get<OEventDatabase>().userDao() }
    
    single<AttendeeLocalDataSource> { AttendeeLocalDataSourceImpl(get()) }
    single<EventLocalDataSource> { EventLocalDataSourceImpl(get()) }
    single<MerchantLocalDataSource> { MerchantLocalDataSourceImpl(get()) }
    single<UserLocalDataSource> { UserLocalDataSourceImpl(get()) }
}
