package com.example.todomobile.api

import com.example.todomobile.model.Categoria
import com.example.todomobile.model.Tarefa
import retrofit2.Response
import retrofit2.http.*


interface ApiService {

    //Requisição das categorias - Lista de categorias
    @GET("categoria")
    suspend fun listCategoria(): Response<List<Categoria>>

    //Adicionar nova tarefa
    @POST("tarefa")
    suspend fun addTarefa(
        @Body tarefa: Tarefa
    ): Response<Tarefa>

    //Requisições Tarefas
    @GET("tarefa")
    suspend fun listTarefas(): Response<List<Tarefa>>

    //Requisição PUT - Tarefas
    @PUT("tarefa")
    suspend fun updateTarefa(
        @Body tarefas: Tarefa
    ): Response<Tarefa>

    //Requisição DELETE - Tarefas
    @DELETE("tarefa/{id}")
    suspend fun deleteTarefa(
        @Path("id") valor: Long
    ): Response<Tarefa>

}