package pt.wtest.data.room

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import pt.wtest.MainApplication
import pt.wtest.data.entities.PostalCodeEntity
import java.text.Normalizer


class PostalCodeRepository {
    private var database: WTestDatabase? = null
    init {
        database = MainApplication.application?.let { Room.databaseBuilder(it, WTestDatabase::class.java, DB_NAME).build() }
    }

    fun insert(item: PostalCodeEntity): Long? {
        return database?.postalCodeDAO()?.insert(item)
    }

    fun deleteAll(): Int? {
        return database?.postalCodeDAO()?.deleteAll()
    }

    fun insertMultiple(items: List<PostalCodeEntity>) {
        database?.postalCodeDAO()?.insertMultiple(items)
    }

    fun getCount(): Int? {
        return database?.postalCodeDAO()?.getCount()
    }

    fun fetchAll(): List<PostalCodeEntity>? {
        return database?.postalCodeDAO()?.fetchAll()
    }

    fun fetch(search: List<String>): List<PostalCodeEntity>? {
        var query = "SELECT * FROM PostalCodeEntity WHERE "
        search.forEachIndexed { index, value ->
            var operator = " OR "
            if (index == 0) {
               operator = " "
            }
            query = "$query $operator nome_localidade_ascii LIKE '%${Normalizer.normalize(
                value,
                Normalizer.Form.NFD
            ).replace(
                "[^\\p{ASCII}]".toRegex(),
                ""
            )}%'"
            operator = " OR "
            query = "$query $operator num_cod_postal LIKE '%$value%'"
            query = "$query $operator ext_cod_postal LIKE '%$value%'"
        }
        query = "$query GROUP BY nome_localidade, num_cod_postal, ext_cod_postal"
        query = "$query LIMIT 1000"
        Log.d(javaClass.simpleName, "fetch | Query: $query")
        return database?.postalCodeDAO()?.fetchSearch(SimpleSQLiteQuery(query))
    }
}