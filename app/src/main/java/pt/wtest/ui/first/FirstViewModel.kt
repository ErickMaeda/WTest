package pt.wtest.ui.first

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import pt.wtest.data.entities.PostalCodeEntity
import pt.wtest.data.room.PostalCodeRepository
import pt.wtest.network.RestAdapter
import pt.wtest.network.RestApi
import retrofit2.Call
import java.text.Normalizer

class FirstViewModel : ViewModel() {

    suspend fun loadPostalCodes(search: String = "2695-650, São João da Talha") {
        withContext(Dispatchers.IO) {
            val repository = PostalCodeRepository()
            val searchList = search
                .split('-')
                .joinToString(" ")
                .split(',')
                .joinToString(" ")
                .split(" ")
                .filter { it.isNotBlank() }
            Log.d(javaClass.simpleName, "searchList $searchList")
            val list = repository.fetch(searchList)
            Log.d(javaClass.simpleName, "list ${list?.size}")
        }
    }

    suspend fun downloadAllPostalCodes() {
        withContext(Dispatchers.IO) {
            val service: RestApi = RestAdapter().getRetrofitInstance()!!
            val call: Call<ResponseBody> = service.getPostalCodes()
            Log.d(this.javaClass.simpleName, "before")
            val response = call.execute()
            Log.d(
                this.javaClass.simpleName,
                "after: ${response.isSuccessful} | code: ${response.code()} | message: ${response.message()}"
            )

            if (response.isSuccessful) {
                val repository = PostalCodeRepository()
                val rows = response.body()
                    ?.string()
                    ?.lines()
                    ?.drop(1)
                    ?.dropLast(1)

                Log.d("Rows", "${rows?.size}")

//                for (element in rows!!) {
//                    val rowColumns = element.split(",")
//                    val lastInsertId = repository.insert(
//                        PostalCodeEntity(
//                            id = null,
//                            nome_localidade = rowColumns[3],
//                            nome_localidade_ascii = Normalizer.normalize(rowColumns[3], Normalizer.Form.NFD).replace("[^\\p{ASCII}]".toRegex(), ""),
//                            num_cod_postal = rowColumns[14],
//                            ext_cod_postal = rowColumns[15]
//                        )
//                    )
//                    Log.d("LastInsertId", "$lastInsertId")
//                }
//                val insertValues: MutableList<PostalCodeEntity> = mutableListOf()
                Log.d(javaClass.simpleName, "before map")
                val insertValues: List<PostalCodeEntity> = rows!!.map {
                    val rowColumns = it.split(",")
                    PostalCodeEntity(
                        id = null,
                        nome_localidade = rowColumns[3],
                        nome_localidade_ascii = Normalizer.normalize(
                            rowColumns[3],
                            Normalizer.Form.NFD
                        ).replace("[^\\p{ASCII}]".toRegex(), ""),
                        num_cod_postal = rowColumns[14],
                        ext_cod_postal = rowColumns[15]
                    )
                }
                Log.d(javaClass.simpleName, "after map")
//                for (value in rows!!) {
//                    val rowColumns = value.split(",")
//                    insertValues.add(
//                        PostalCodeEntity(
//                            id = null,
//                            nome_localidade = rowColumns[3],
//                            nome_localidade_ascii = Normalizer.normalize(
//                                rowColumns[3],
//                                Normalizer.Form.NFD
//                            ).replace("[^\\p{ASCII}]".toRegex(), ""),
//                            num_cod_postal = rowColumns[14],
//                            ext_cod_postal = rowColumns[15]
//                        )
//                    )
////                    Log.d("rowColumn", "Items on array ${insertValues.size}")
//                }
                Log.d(javaClass.simpleName, "Items on array ${insertValues.size}")
                insertValues.chunked(10000).forEach {
                    repository.insertMultiple(it)
                }
                Log.d(javaClass.simpleName, "Items inserted with success")
            }
        }
    }

}