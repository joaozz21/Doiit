package com.example.todomobile.adapter

import com.example.todomobile.model.Tarefa

interface TaskItemClickListener {

    fun onTaskClicked(tarefas: Tarefa)

}