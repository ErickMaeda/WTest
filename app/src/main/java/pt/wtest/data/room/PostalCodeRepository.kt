package pt.wtest.data.room

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import pt.wtest.data.entities.PostalCodeEntity


class PostalCodeRepository(context: Context?) {
    private var database: WTestDatabase? = null
    init {
        database = context?.let { Room.databaseBuilder(it, WTestDatabase::class.java, DB_NAME).build() }
    }

    fun insert(item: PostalCodeEntity): Long? {
        return database?.postalCodeDAO()?.insert(item)
    }

    fun fetchAll(): List<PostalCodeEntity>? {
        return database?.postalCodeDAO()?.fetchAll()
    }
}