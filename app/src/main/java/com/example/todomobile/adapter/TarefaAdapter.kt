package com.example.todomobile.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.todomobile.MainViewModel
import com.example.todomobile.R
import com.example.todomobile.model.Tarefa

class TarefaAdapter (
   private val taskItemClickListener: TaskItemClickListener,
   private val mainViewModel: MainViewModel,
   private val context: Context
        ): RecyclerView.Adapter<TarefaAdapter.TarefaViewHolder>(){

    private var listTarefas = emptyList<Tarefa>()

    class TarefaViewHolder(view: View): RecyclerView.ViewHolder(view){
        val textNome = view.findViewById<TextView>(R.id.textNome)
        val textDescricao = view.findViewById<TextView>(R.id.textDescricao)
        val textResponsavel = view.findViewById<TextView>(R.id.textResponsavel)
        val textData = view.findViewById<TextView>(R.id.textData)
        val switchAtivoCard = view.findViewById<Switch>(R.id.switchAtivoCard)
        val textCategoria = view.findViewById<TextView>(R.id.textCategoria)
        val buttonDeletar = view.findViewById<Button>(R.id.buttonDeletar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarefaViewHolder {
       val layoutAdapter = LayoutInflater.from(parent.context)
           .inflate(R.layout.card_layout, parent, false)

        return TarefaViewHolder(layoutAdapter)

    }

    override fun onBindViewHolder(holder: TarefaViewHolder, position: Int) {
        val tarefa = listTarefas[position]

        holder.textNome.text = tarefa.nome
        holder.textDescricao.text = tarefa.descricao
        holder.textResponsavel.text = tarefa.responsavel
        holder.textData.text = tarefa.data
        holder.switchAtivoCard.isChecked = tarefa.status
        holder.textCategoria.text = tarefa.categoria.descricao

        holder.itemView.setOnClickListener {
            taskItemClickListener.onTaskClicked(tarefa)
        }

        holder.buttonDeletar.setOnClickListener {
            //Implementar a lógica para deletar a tarefa
            mainViewModel.deleteTarefa(tarefa.id)
            //Toast.makeText(context, "Deletou e", Toast.LENGTH_SHORT).show()
        }

        holder.switchAtivoCard
            .setOnCheckedChangeListener { compoundButton, ativo ->
                tarefa.status = ativo
                mainViewModel.updateTarefa(tarefa)
            }


    }

    override fun getItemCount(): Int {
        return listTarefas.size
    }

    fun setLista(tarefas: List<Tarefa>){
        this.listTarefas = tarefas
        notifyDataSetChanged()
    }

}