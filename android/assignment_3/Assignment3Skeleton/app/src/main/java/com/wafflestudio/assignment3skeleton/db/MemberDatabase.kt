package com.wafflestudio.assignment3skeleton.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.wafflestudio.assignment3skeleton.model.Member

// Already Completed
@Database(entities = [Member::class], version = 1)
abstract class MemberDatabase : RoomDatabase() {

    abstract fun memberDao(): MemberDao

    companion object {
        @Volatile
        private var INSTANCE: MemberDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): MemberDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                MemberDatabase::class.java,
                "member_db"
            ).build().also { INSTANCE = it }
        }
    }
}
