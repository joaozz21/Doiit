package com.example.todomobile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todomobile.model.Categoria
import com.example.todomobile.model.Tarefa
import com.example.todomobile.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (
    val repository: Repository
        ): ViewModel() {

    var tarefaSelecionada: Tarefa? = null

    private var _myCategoriaResponse =
        MutableLiveData<Response<List<Categoria>>>()

    val myCategoriaResponse: LiveData<Response<List<Categoria>>> =
        _myCategoriaResponse

    private val _myTarefaResponse =
        MutableLiveData<Response<List<Tarefa>>>()

    val myTarefaResponse: LiveData<Response<List<Tarefa>>> =
        _myTarefaResponse

    val dataSelecionada = MutableLiveData<LocalDate>()

    init {
        dataSelecionada.value = LocalDate.now()

        listCategoria()
    }

    fun listCategoria(){
        viewModelScope.launch {
            try{
                val response = repository.listCategoria()
                _myCategoriaResponse.value = response
            }catch (e: Exception){
                Log.d("Erro", e.message.toString())
            }
        }
    }

    fun addTarefa(tarefa: Tarefa){
        viewModelScope.launch {
            try {
                repository.addTarefa(tarefa)
                listTarefas()
            }catch (e: Exception){
                Log.d("Erro", e.message.toString())
            }
        }
    }

    fun listTarefas(){
        viewModelScope.launch {
            try{
                val response = repository.listTarefas()
                _myTarefaResponse.value = response
            }catch (e: Exception){
                Log.e("Developer", "Error", e)
            }

        }
    }

    fun updateTarefa(tarefas: Tarefa){
        viewModelScope.launch {
            try{
                repository.updateTarefa(tarefas)
                listTarefas()
            }catch (e: Exception){
                Log.d("Erro", e.message.toString())
            }

        }
    }

    fun deleteTarefa(id: Long){
        viewModelScope.launch {
            try{
                repository.deleteTarefa(id)
                listTarefas()
            }catch (e: Exception){
                Log.d("Erro", e.message.toString())
            }
        }
    }

}