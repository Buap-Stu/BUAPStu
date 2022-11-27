package com.buap.stu.buapstu.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buap.stu.buapstu.core.utils.Resource
import com.buap.stu.buapstu.domain.database.DatabaseRepository
import com.buap.stu.buapstu.models.Alumno
import com.buap.stu.buapstu.models.Boleto
import com.buap.stu.buapstu.models.Horario
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DatabaseViewModel @Inject constructor(
    private val database:DatabaseRepository
):ViewModel() {

    private val _isProcess= MutableStateFlow(false)
    val isProcess = _isProcess.asStateFlow()

    private val _messageDatabase= Channel<String>()
    val messageDatabase =_messageDatabase.receiveAsFlow()

    private val _resultSearch:MutableStateFlow<Resource<Alumno>?> = MutableStateFlow(null)
    val resultSearch=_resultSearch.asStateFlow()

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

    fun addNewBoleto(boleto:Boleto,callbackSuccess:()->Unit)=viewModelScope.launch {
        _isProcess.value=true
        try {
            database.addNewBoleto(boleto)
            callbackSuccess()
        }catch (e:Exception){
            e.message?.let { _messageDatabase.trySend(it) }
        }
        _isProcess.value=false
    }

    fun transferCredits(matricula:String,creditos:Int,callBackSuccess:()->Unit)=viewModelScope.launch {
        _isProcess.value=true
        try {
            database.transferCredits(matricula,creditos)
            callBackSuccess()
        }catch (e:Exception){
            e.message?.let { _messageDatabase.trySend(it) }
            _resultSearch.value=Resource.Failure
        }
        _isProcess.value=false
    }

    fun searchStudent(matricula: String)=viewModelScope.launch {
        _isProcess.value=true
        _resultSearch.value=Resource.Loading
        try {
            val student=database.searchStudent(matricula)
            _resultSearch.value=Resource.Success(student)
        }catch (e:Exception){
            e.message?.let { _messageDatabase.trySend(it) }
            _resultSearch.value=Resource.Failure
        }
        _isProcess.value=false
    }


}