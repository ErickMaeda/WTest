package pt.wtest.ui.third

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ThirdViewModel : ViewModel() {

    val viewTypes = MutableLiveData<List<ExerciseThreeAdapter.ViewTypes>>()

    suspend fun loadInputList() {
        withContext(Dispatchers.Default) {
            val listExerciseThree: MutableList<ExerciseThreeAdapter.ViewTypes> = mutableListOf()
            var nextViewType = ExerciseThreeAdapter.ViewTypes.NORMAL
            for (i in 1 .. 50) {
                listExerciseThree.add(nextViewType)
                nextViewType = when (nextViewType) {
                    ExerciseThreeAdapter.ViewTypes.ALL_UPPER_CASE -> ExerciseThreeAdapter.ViewTypes.NORMAL
                    ExerciseThreeAdapter.ViewTypes.NUMBER -> ExerciseThreeAdapter.ViewTypes.ALL_UPPER_CASE
                    else -> ExerciseThreeAdapter.ViewTypes.NUMBER
                }
            }
            withContext(Dispatchers.Main) {
                viewTypes.value = listExerciseThree
            }
        }
    }
}