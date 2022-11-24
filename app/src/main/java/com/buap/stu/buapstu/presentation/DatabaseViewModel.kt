package com.buap.stu.buapstu.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buap.stu.buapstu.core.utils.Resource
import com.buap.stu.buapstu.domain.database.DatabaseRepository
import com.buap.stu.buapstu.models.Horario
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DatabaseViewModel @Inject constructor(
    private val database:DatabaseRepository
):ViewModel() {


    val listRoute= flow {
        emit(Resource.Loading)
        val response=withContext(Dispatchers.IO){ database.getListRouter() }
        emit(Resource.Success(response))
    }.catch {
        Timber.d("$it")
        emit(Resource.Failure)
    }

    val listHours=MutableStateFlow<Resource<List<Horario>>>(Resource.Loading)

    fun requestHours(nameRoute:String)=viewModelScope.launch{
        listHours.value=Resource.Loading
        try {
            val response=database.getListHours(nameRoute)
            listHours.value=Resource.Success(response)
        }catch (e:Exception){
            Timber.d("$e")
            listHours.value=Resource.Failure
        }

    }


}