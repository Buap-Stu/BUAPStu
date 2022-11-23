package com.buap.stu.buapstu.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buap.stu.buapstu.core.utils.Resource
import com.buap.stu.buapstu.domain.database.DatabaseRepoImpl
import com.buap.stu.buapstu.domain.database.DatabaseRepository
import com.buap.stu.buapstu.models.Horario
import com.buap.stu.buapstu.models.Ruta
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DatabaseViewModel @Inject constructor(
    private val database:DatabaseRepository
):ViewModel() {


    val listRoute= flow<Resource<List<Ruta>>> {
        emit(Resource.Success(database.getListRouter()))
    }.catch {
        Timber.d("$it")
        emit(Resource.Failure)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        Resource.Loading
    )

    val listHours=flow<Resource<List<Horario>>> {
        emit(Resource.Success(database.getListHours()))
    }.catch {
        Timber.d("$it")
        emit(Resource.Failure)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        Resource.Loading
    )

}