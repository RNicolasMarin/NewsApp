package com.example.newsapp.posts.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.core.utils.Result
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
        viewModelScope.launch(Dispatchers.Main) {
            _state.emit(
                _state.value.copy(
                    status = HomeStatus.LOADING
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
                                    posts = result.data
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}