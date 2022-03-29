package com.example.todomobile.model

data class Tarefa (
    val id: Long,
    var nome: String,
    var descricao: String,
    var responsavel: String,
    var data: String,
    var status: Boolean,
    var categoria: Categoria
        ){
}