package pt.wtest.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import pt.wtest.data.entities.PostalCodeEntity

val DB_NAME = "WTest"

@Database(entities = [PostalCodeEntity::class], version = 2, exportSchema = false)
abstract class WTestDatabase : RoomDatabase() {
    abstract fun postalCodeDAO(): PostalCodeDAO?
}