package com.example.todomobile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.todomobile.databinding.FragmentFormBinding
import com.example.todomobile.fragment.DatePickerFragment
import com.example.todomobile.fragment.TimePickerListener
import com.example.todomobile.model.Categoria
import com.example.todomobile.model.Tarefa
import java.time.LocalDate

class FormFragment : Fragment(), TimePickerListener {

    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var binding: FragmentFormBinding

    private var categoriaSelecionada = 0L

    private var tarefaSelecionada: Tarefa? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFormBinding.inflate(inflater, container, false)

        carregarDados()

        mainViewModel.listCategoria()

        mainViewModel.myCategoriaResponse.observe(viewLifecycleOwner) { response ->
            Log.d("Requisicao", response.body().toString())
            spinnerCategoria(response.body())
        }

        mainViewModel.dataSelecionada.observe(viewLifecycleOwner) { selectedDate ->
            binding.editData.setText(selectedDate.toString())
        }


        binding.buttonSalvar.setOnClickListener {
            findNavController().navigate(R.id.action_formFragment_to_listFragment)
        }

        binding.editData.setOnClickListener {
            DatePickerFragment(this)
                .show(parentFragmentManager, "DatePicker")
        }

        binding.buttonSalvar.setOnClickListener {
            inserirNoBaco()
        }


        return binding.root
    }

    fun spinnerCategoria(categorias: List<Categoria>?){

        if(categorias != null){
            binding.spinnerCategoria.adapter = ArrayAdapter(
                requireContext(),
                R.layout.support_simple_spinner_dropdown_item,
                categorias
            )

            binding.spinnerCategoria.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(
                        p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long
                    ) {
                        val categoriaSelecionadaFun = binding
                            .spinnerCategoria.selectedItem as Categoria

                        categoriaSelecionada = categoriaSelecionadaFun.id
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }

                }
        }
    }

    fun validarCampos(
        nome: String, desc: String, responsavel: String,
        data: String
    ): Boolean{

        return !(
                (nome == "" || nome.length < 3 || nome.length > 20) ||
                (desc == "" || desc.length < 5 || desc.length > 200) ||
                (responsavel == "" || responsavel.length < 3 || responsavel.length > 20) ||
                data == ""
                )
    }

    fun inserirNoBaco(){

        val nome = binding.editNome.text.toString()
        val desc = binding.editDescricao.text.toString()
        val responsavel = binding.editResponsavel.text.toString()
        val data = binding.editData.text.toString()
        val status = binding.switchAtivoCard.isChecked
        val categoria = Categoria(categoriaSelecionada, null, null)

        if(validarCampos(nome, desc, responsavel, data)){
            if(tarefaSelecionada == null){
                val tarefa = Tarefa(
                    0, nome, desc, responsavel, data, status, categoria
                )
                mainViewModel.addTarefa(tarefa)
            }else{
                val tarefa = Tarefa(
                    tarefaSelecionada?.id!!,
                    nome, desc, responsavel, data, status, categoria
                )
                mainViewModel.updateTarefa(tarefa)
            }
            Toast.makeText(
                context, "Tarefa Salva!",
                Toast.LENGTH_LONG
            ).show()
            findNavController().navigate(R.id.action_formFragment_to_listFragment)
        }else{
            Toast.makeText(
                context, "Preencha os campos corretamente!",
                Toast.LENGTH_LONG
            ).show()

        }

    }

    override fun onTimeSelected(date: LocalDate) {
        mainViewModel.dataSelecionada.value = date
    }

    private fun carregarDados() {
        tarefaSelecionada = mainViewModel.tarefaSelecionada
        if (tarefaSelecionada != null) {
            binding.editNome.setText(tarefaSelecionada?.nome)
            binding.editDescricao.setText(tarefaSelecionada?.descricao)
            binding.editResponsavel.setText(tarefaSelecionada?.responsavel)
            binding.editData.setText(tarefaSelecionada?.data)
            binding.switchAtivoCard.isChecked = tarefaSelecionada?.status!!
        } else {
            binding.editNome.text = null
            binding.editDescricao.text = null
            binding.editResponsavel.text = null
            binding.editData.text = null
        }
    }
}