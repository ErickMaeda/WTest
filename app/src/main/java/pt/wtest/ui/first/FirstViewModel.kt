package pt.wtest.ui.first

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    val postalCodes: MutableLiveData<List<PostalCodeEntity>> by lazy { MutableLiveData<List<PostalCodeEntity>>() }
    val loadingState: MutableLiveData<LoadingState> = MutableLiveData<LoadingState>().apply { LoadingState.INITIAL }
    val totalPostalCodes = 326069

    enum class LoadingState {
        INITIAL, // First state
        DOWNLOADING, // If need to download
        FETCHING, // If is fetching some data like searching data
        NORMAL // Normal case, doesn't need to show loading
    }

    suspend fun initialize() {
        checkIfNeedDownloadPostalCodes()
    }

    suspend fun loadPostalCodes(search: String? = "") {
        Log.d(javaClass.simpleName, "loadPostalCodes | $search")
        withContext(Dispatchers.IO) {
            loadingState.postValue(LoadingState.FETCHING)
            val repository = PostalCodeRepository()
            val searchList = search
                ?.split('-')
                ?.joinToString(" ")
                ?.split(',')
                ?.joinToString(" ")
                ?.split(" ")
                ?.filter { it.isNotBlank() }
            if (searchList.isNullOrEmpty()) {
                val list = repository.fetchAll()
                postalCodes.postValue(list)
                loadingState.postValue(LoadingState.NORMAL)
            } else {
                val list = repository.fetch(searchList)
                postalCodes.postValue(list)
                loadingState.postValue(LoadingState.NORMAL)
            }
        }
    }

    suspend fun checkIfNeedDownloadPostalCodes() {
        withContext(Dispatchers.IO) {
            val repository = PostalCodeRepository()
            val count = repository.getCount()
            Log.d(javaClass.simpleName, "Count | $count")

            if (count!! < totalPostalCodes) {
                Log.d(javaClass.simpleName, "")
                downloadAllPostalCodes()
            }
        }
    }

    suspend fun downloadAllPostalCodes() {
        // Download the postal codes on IO Thread
        withContext(Dispatchers.IO) {
            loadingState.postValue(LoadingState.DOWNLOADING)
            val service: RestApi = RestAdapter().getRetrofitInstance()!!
            val call: Call<ResponseBody> = service.getPostalCodes()
            val response = call.execute() // Call API to get results
            Log.d(
                this.javaClass.simpleName,
                "" +
                        "isSuccessfull: ${response.isSuccessful} | " +
                        "code: ${response.code()} | " +
                        "message: ${response.message()}"
            )

            if (response.isSuccessful) {
                val repository = PostalCodeRepository()
                val rows = response.body()
                    ?.string() // Get the response as string
                    ?.lines() // Split the String into lines
                    ?.drop(1) // Remove the first row that is the labels
                    ?.dropLast(1) // Remove the last row because it's an empty line

                Log.d("Rows", "${rows?.size}")

                // Map the array $rows to a List<PostalCodeEntity>
                val insertValues: List<PostalCodeEntity> = rows!!.map {
                    val rowColumns = it.split(",")
                    PostalCodeEntity(
                        id = null,
                        nome_localidade = rowColumns[3], // Get element column index 3
                        nome_localidade_ascii = Normalizer.normalize(
                            rowColumns[3],
                            Normalizer.Form.NFD
                        ).replace(
                            "[^\\p{ASCII}]".toRegex(),
                            ""
                        ), // Get element column index 3 and replace with a normalized ASCII format to be possible to search for values.
                        num_cod_postal = rowColumns[14], // Get element column index 14
                        ext_cod_postal = rowColumns[15] // Get element column index 15
                    )
                }

                // Remove registers before inserting new ones
                repository.deleteAll()
                Log.d(javaClass.simpleName, "Items deleted with success")

                // Divide the array in pieces of 10k to insert
                insertValues.chunked(10000).forEach {
                    repository.insertMultiple(it)
                }
                Log.d(javaClass.simpleName, "Items inserted with success")
                loadingState.postValue(LoadingState.NORMAL)
            }
        }
    }

}