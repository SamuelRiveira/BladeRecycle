package dev.samu.bladerecycle.view

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dev.samu.bladerecycle.viewmodel.BookmarkViewModel

@Composable
fun FirstScreen(
    viewModel: BookmarkViewModel
){
    val bookmarks by viewModel.bookmarks.collectAsState()
    val bookmarkstype by viewModel.bookmarkstype.collectAsState()

    Text(text = "Hola")
}