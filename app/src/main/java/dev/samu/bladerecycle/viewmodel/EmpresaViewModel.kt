package dev.samu.bladerecycle.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.samu.bladerecycle.data.Empresa
import dev.samu.bladerecycle.data.EmpresaDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EmpresaViewModel(private val empresaDao: EmpresaDao) : ViewModel() {

    private val _empresas = MutableStateFlow<List<Empresa>>(emptyList())
    val empresas: StateFlow<List<Empresa>> = _empresas.asStateFlow()

    init {
        viewModelScope.launch {
            empresaDao.getAllEmpresas().collect { listaEmpresas ->
                _empresas.value = listaEmpresas
            }
        }
    }

    fun agregarEmpresa(nombre: String) {
        val nuevaEmpresa = Empresa(name = nombre)
        viewModelScope.launch {
            empresaDao.insert(nuevaEmpresa)
        }
    }
}