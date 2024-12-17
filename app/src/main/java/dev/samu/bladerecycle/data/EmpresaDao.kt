package dev.samu.bladerecycle.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EmpresaDao {
    @Query("SELECT * FROM empresa")
    fun getAllEmpresas(): Flow<List<Empresa>>

    @Insert
    suspend fun insert(bookmark: Empresa)
}