package dev.samu.bladerecycle.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Bookmark::class, BookmarkType::class, Empresa::class], version = 2)  // Incrementar la versión
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
    abstract fun bookmarkTypeDao(): BookmarkTypeDao
    abstract fun empresaDao(): EmpresaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Migración destructiva si es necesario
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "bookmark_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
