package dev.samu.bladerecycle.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "empresa")
data class Empresa(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String
)