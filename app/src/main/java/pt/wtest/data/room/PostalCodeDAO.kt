package pt.wtest.data.room

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import pt.wtest.data.entities.PostalCodeEntity

@Dao
interface PostalCodeDAO {
    @Insert
    fun insert(item: PostalCodeEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMultiple(items: List<PostalCodeEntity>)

    @Query("DELETE FROM PostalCodeEntity")
    fun deleteAll(): Int

    @Query("SELECT * FROM PostalCodeEntity LIMIT 1000")
    fun fetchAll(): List<PostalCodeEntity>

    @Query("SELECT COUNT(*) FROM PostalCodeEntity")
    fun getCount(): Int

    @RawQuery
    fun fetchSearch(query: SupportSQLiteQuery): List<PostalCodeEntity>
}