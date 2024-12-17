package dev.samu.bladerecycle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import dev.samu.bladerecycle.data.AppDatabase
import dev.samu.bladerecycle.data.BookmarkDao
import dev.samu.bladerecycle.viewmodel.BookmarkViewModel
import dev.samu.bladerecycle.viewmodel.BookmarkViewModelFactory
import dev.samu.bladerecycle.viewmodel.EmpresaViewModel
import dev.samu.bladerecycle.viewmodel.EmpresaViewModelFactory
import dev.samu.tareas.navigation.AppNavigation

class MainActivity : ComponentActivity() {

    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = AppDatabase.getDatabase(this)

        val bookmarkDao: BookmarkDao = database.bookmarkDao()
        val bookmarkTypeDao = database.bookmarkTypeDao()
        val empresaDao = database.empresaDao()

        val bookmarkViewModel = ViewModelProvider(
            this,
            BookmarkViewModelFactory(bookmarkDao, bookmarkTypeDao)
        )[BookmarkViewModel::class.java]

        val empresaViewModel = ViewModelProvider(
            this,
            EmpresaViewModelFactory(empresaDao)
        )[EmpresaViewModel::class.java]

        setContent {
            AppNavigation(
                modifier = Modifier,
                viewModel = bookmarkViewModel,
                empresaViewModel = empresaViewModel,
                database = database
            )
        }
    }

}