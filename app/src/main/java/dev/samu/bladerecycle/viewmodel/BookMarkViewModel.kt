package dev.samu.bladerecycle.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.samu.bladerecycle.data.Bookmark
import dev.samu.bladerecycle.data.BookmarkDao
import dev.samu.bladerecycle.data.BookmarkType
import dev.samu.bladerecycle.data.BookmarkTypeDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookmarkViewModel(
    private val bookmarkDao: BookmarkDao,
    private val bookmarkTypeDao: BookmarkTypeDao
) : ViewModel() {

    private val _bookmarks = MutableStateFlow<List<Bookmark>>(emptyList())
    val bookmarks: StateFlow<List<Bookmark>> get() = _bookmarks

    private val _bookmarkstype = MutableStateFlow<List<BookmarkType>>(emptyList())
    val bookmarkstype: MutableStateFlow<List<BookmarkType>> get() = _bookmarkstype

    init {
        loadBookmarks()
        loadBookmarkTypes()
    }

    private fun loadBookmarks() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                bookmarkDao.getAllBookmarks().collect { loadedBookmarks ->
                    _bookmarks.value = loadedBookmarks
                }
            }
        }
    }

    private fun loadBookmarkTypes() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                bookmarkTypeDao.getAllBookmarkTypes().collect { loadedTypes ->
                    _bookmarkstype.value = loadedTypes
                }
            }
        }
    }

    fun addBookmark(bookmark: Bookmark) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                bookmarkDao.insert(bookmark)
                loadBookmarks()
            }
        }
    }

    fun updateBookmark(bookmark: Bookmark) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                bookmarkDao.update(bookmark)
                loadBookmarks()
            }
        }
    }

    fun deleteBookmark(bookmark: Bookmark) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                bookmarkDao.delete(bookmark)
                loadBookmarks()
            }
        }
    }

    fun addBookmarkType(bookmarkType: BookmarkType) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                bookmarkTypeDao.insert(bookmarkType)
                loadBookmarkTypes()
            }
        }
    }

    fun updateBookmarkType(bookmarkType: BookmarkType) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                bookmarkTypeDao.update(bookmarkType)
                loadBookmarkTypes()
            }
        }
    }

    fun deleteBookmarkType(bookmarkType: BookmarkType) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                bookmarkTypeDao.delete(bookmarkType)
                loadBookmarkTypes()
            }
        }
    }
}