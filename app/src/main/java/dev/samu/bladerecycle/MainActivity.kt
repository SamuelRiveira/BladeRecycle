package dev.samu.bladerecycle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import dev.samu.bladerecycle.data.AppDatabase
import dev.samu.bladerecycle.data.BookmarkDao
import dev.samu.bladerecycle.view.FirstScreen
import dev.samu.bladerecycle.viewmodel.BookmarkViewModel
import dev.samu.bladerecycle.viewmodel.BookmarkViewModelFactory
import dev.samu.tareas.navigation.AppNavigation

class MainActivity : ComponentActivity() {

    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = AppDatabase.Companion.getDatabase(this)
        val bookmarkDao: BookmarkDao = AppDatabase.getDatabase(applicationContext).bookmarkDao()
        val bookmarkTypeDao = AppDatabase.getDatabase(applicationContext).bookmarkTypeDao()

        val viewModel = ViewModelProvider(
            this,
            BookmarkViewModelFactory(bookmarkDao, bookmarkTypeDao)
        )[BookmarkViewModel::class.java]

        setContent {
            AppNavigation(
                modifier = Modifier,
                viewModel = viewModel,
                database
            )
        }
    }
}