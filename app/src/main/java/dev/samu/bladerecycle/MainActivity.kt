package dev.samu.bladerecycle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import dev.samu.bladerecycle.data.AppDatabase
import dev.samu.bladerecycle.data.BookmarkDao
import dev.samu.bladerecycle.ui.theme.BladeRecycleTheme
import dev.samu.bladerecycle.view.FirstScreen
import dev.samu.bladerecycle.viewmodel.BookmarkViewModel
import dev.samu.bladerecycle.viewmodel.BookmarkViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bookmarkDao: BookmarkDao = AppDatabase.getDatabase(applicationContext).bookmarkDao()
        val bookmarkTypeDao = AppDatabase.getDatabase(applicationContext).bookmarkTypeDao()

        val viewModel = ViewModelProvider(
            this,
            BookmarkViewModelFactory(bookmarkDao, bookmarkTypeDao)
        )[BookmarkViewModel::class.java]

        setContent {
            FirstScreen(viewModel = viewModel)
        }
    }
}