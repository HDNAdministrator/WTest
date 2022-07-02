package com.example.wtest.lib.repo.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Address::class, AddressFTS::class], exportSchema = false, version = 3)
abstract class LocalSQL: RoomDatabase() {
    abstract fun addressDao(): AddressDAO
}