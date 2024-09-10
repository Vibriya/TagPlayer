package com.example.tagplayer.search.domain

import com.example.tagplayer.main.presentation.SongUi
import com.example.tagplayer.search.presentation.SearchListenerAdapter
import com.example.tagplayer.search.presentation.SearchUi

interface SearchState {
    fun dispatch(searchResultAdapter: SearchListenerAdapter)

    class SearchHistorySuccess(private val list: List<SearchUi>) : SearchState {
        override fun dispatch(searchResultAdapter: SearchListenerAdapter) = Unit
    }

//    class SongsSuccess(private val list: List<SongUi>) : SearchState {
//        override fun dispatch(searchResultAdapter: SearchListenerAdapter) {
//            searchResultAdapter.submitList(list)
//        }
//    }

    class Error(private val cause: String) : SearchState {
        override fun dispatch(searchResultAdapter: SearchListenerAdapter) = Unit
    }
}