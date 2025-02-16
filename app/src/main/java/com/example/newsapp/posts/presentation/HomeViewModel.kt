package com.example.newsapp.posts.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.core.domain.Result
import com.example.newsapp.posts.domain.models.Post
import com.example.newsapp.posts.domain.repositories.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postRepository: PostRepository
): ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state

    init {
        getInitialPosts()
    }

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.RetryInitialPosts -> {
                getInitialPosts()
            }
            is HomeAction.UpdateSearchingValue -> {
                searchPosts(action.value)
            }
            HomeAction.SearchingValue -> {
                searchPosts(_state.value.searching ?: "")
            }
        }
    }

    private fun searchPosts(searching: String) {
        viewModelScope.launch(Dispatchers.Default) {
            val filtered = _state.value.allPosts.filter {
                it.title.lowercase().contains(searching) ||
                it.content.lowercase().contains(searching)
            }
            _state.emit(
                _state.value.copy(
                    searching = searching,
                    filteredPosts = filtered
                )
            )
        }
    }

    private fun getInitialPosts() {
        viewModelScope.launch(Dispatchers.Main) {

            _state.emit(
                _state.value.copy(
                    status = HomeStatus.LOADING,
                    filteredPosts = (0..20).map {
                        Post(
                            id = it,
                            title = "",
                            content = ""
                        )
                    }
                )
            )

            withContext(Dispatchers.IO) {
                val result = postRepository.getPosts()

                Log.d("HOME", "Posts: $result")

                withContext(Dispatchers.Main) {
                    _state.emit(
                        when (result) {
                            Result.Error -> {
                                _state.value.copy(
                                    status = HomeStatus.ERROR
                                )
                            }
                            is Result.Success -> {
                                _state.value.copy(
                                    status = HomeStatus.SUCCESS,
                                    allPosts = result.data,
                                    filteredPosts = result.data,
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}