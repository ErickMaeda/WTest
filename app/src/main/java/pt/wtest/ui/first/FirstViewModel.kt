package pt.wtest.ui.first

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

class FirstViewModel : ViewModel() {

    suspend fun loadPostalCodes(context: Context) {
        withContext(Dispatchers.IO) {
            val service: RestApi = RestAdapter().getRetrofitInstance()!!
            val call: Call<ResponseBody> = service.getPostalCodes()
            Log.d(this.javaClass.simpleName,"before")
            val response = call.execute()
            Log.d(this.javaClass.simpleName,"after: ${response.isSuccessful} | code: ${response.code()} | message: ${response.message()}")

            if (response.isSuccessful) {
                val repository = PostalCodeRepository(context)
                val rows = response.body()
                    ?.string()
                    ?.lines()
                    ?.drop(1)
                    ?.dropLast(1)

                Log.d("Rows", "${rows?.size}")

                for (element in rows!!) {
                    val rowColumns = element.split(",")
                    val lastInsertId = repository.insert(PostalCodeEntity(
                        id = null,
                        nome_localidade = rowColumns[3],
                        num_cod_postal = rowColumns[14],
                        ext_cod_postal = rowColumns[15]
                    ))
                    Log.d("LastInsertId", "$lastInsertId")
                }
            }
        }
    }

}