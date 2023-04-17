package com.carlosjunior.starwarsapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.carlosjunior.starwarsapp.database.dao.FavoriteMovieDAO
import com.carlosjunior.starwarsapp.database.dao.FavoritePersonDAO
import com.carlosjunior.starwarsapp.database.dao.MovieDAO
import com.carlosjunior.starwarsapp.database.dao.PersonDAO
import com.carlosjunior.starwarsapp.database.model.FavoriteMovie
import com.carlosjunior.starwarsapp.database.model.FavoritePerson
import com.carlosjunior.starwarsapp.database.model.Movie
import com.carlosjunior.starwarsapp.database.model.Person

@Database(entities = [Person::class, Movie::class, FavoritePerson::class, FavoriteMovie::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun personDao(): PersonDAO
    abstract fun movieDao(): MovieDAO
    abstract fun favoritePersonDao(): FavoritePersonDAO
    abstract fun favoriteMovieDao(): FavoriteMovieDAO

    companion object {
        private const val DATABASE_NAME = "database"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            if (INSTANCE != null) INSTANCE!!
            else synchronized(this) {
                INSTANCE ?: buidDatabase(context).also { INSTANCE = it }
            }

        private fun buidDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DATABASE_NAME
            ).build()
    }
}