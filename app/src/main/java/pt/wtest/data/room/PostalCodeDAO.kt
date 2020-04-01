package pt.wtest.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import pt.wtest.data.entities.PostalCodeEntity

@Dao
interface PostalCodeDAO {
    @Insert
    fun insert(item: PostalCodeEntity): Long


    @Query("SELECT * FROM PostalCodeEntity")
    fun fetchAll(): List<PostalCodeEntity>
}