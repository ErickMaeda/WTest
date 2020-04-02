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

    @Query("SELECT * FROM PostalCodeEntity LIMIT 10")
    fun fetchAll(): List<PostalCodeEntity>

    @RawQuery
    fun fetchSearch(query: SupportSQLiteQuery): List<PostalCodeEntity>
}