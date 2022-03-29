package com.example.todomobile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todomobile.adapter.TarefaAdapter
import com.example.todomobile.adapter.TaskItemClickListener
import com.example.todomobile.databinding.FragmentListBinding
import com.example.todomobile.model.Tarefa

class ListFragment : Fragment(), TaskItemClickListener {

    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mainViewModel.listTarefas()

        binding = FragmentListBinding.inflate(inflater, container, false)

        //Configurar a recycler no projeto
        val tarefaAdapter = TarefaAdapter(this, mainViewModel, requireContext())

        //Layout Manager
        binding.recyclerTarefa.layoutManager = LinearLayoutManager(context)

        //Configurar o Adapter
        binding.recyclerTarefa.adapter = tarefaAdapter

        //Definir nossa lista para ter um tamanho fixo
        binding.recyclerTarefa.setHasFixedSize(true)

        binding.floatingAdd.setOnClickListener {
            mainViewModel.tarefaSelecionada = null
            findNavController().navigate(R.id.action_listFragment_to_formFragment)
        }

        mainViewModel.myTarefaResponse.observe(viewLifecycleOwner, {
            response -> if(response != null){
                tarefaAdapter.setLista(response.body()!!)
            }
        })

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onTaskClicked(tarefas: Tarefa) {
        mainViewModel.tarefaSelecionada = tarefas
        findNavController().navigate(R.id.action_listFragment_to_formFragment)
    }

}