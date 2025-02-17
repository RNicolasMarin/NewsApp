package com.example.newsapp.posts.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.core.domain.Result
import com.example.newsapp.posts.domain.repositories.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val postRepository: PostRepository
): ViewModel() {

    private val _state = MutableStateFlow(DetailState())
    val state: StateFlow<DetailState> = _state

    fun getPostDetails(id: Int) {
        viewModelScope.launch(Dispatchers.Main) {

            _state.emit(
                _state.value.copy(
                    status = DetailStatus.LOADING
                )
            )

            withContext(Dispatchers.IO) {
                val result = postRepository.getPostDetail(id)

                Log.d("DETAIL", "Post Complete: $result")

                withContext(Dispatchers.Main) {
                    _state.emit(
                        when (result) {
                            Result.Error -> {
                                _state.value.copy(
                                    status = DetailStatus.ERROR
                                )
                            }
                            is Result.Success -> {
                                _state.value.copy(
                                    status = DetailStatus.SUCCESS,
                                    post = result.data,
                                )
                            }
                        }
                    )
                }
            }
        }
    }

}