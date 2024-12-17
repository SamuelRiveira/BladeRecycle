package dev.samu.bladerecycle.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.samu.bladerecycle.data.EmpresaDao

class EmpresaViewModelFactory(
    private val empresaDao: EmpresaDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmpresaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EmpresaViewModel(empresaDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
