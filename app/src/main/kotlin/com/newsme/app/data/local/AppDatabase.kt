package com.newsme.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.newsme.app.data.model.User
import com.newsme.app.data.model.News
import com.newsme.app.data.model.Reaction
import com.newsme.app.data.model.Comment

@Database(
    entities = [User::class, News::class, Reaction::class, Comment::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun newsDao(): NewsDao
    abstract fun reactionDao(): ReactionDao
    abstract fun commentDao(): CommentDao
}
