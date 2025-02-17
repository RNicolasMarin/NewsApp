package com.example.newsapp.users.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.core.domain.Result
import com.example.newsapp.users.domain.models.Address
import com.example.newsapp.users.domain.models.Geo
import com.example.newsapp.users.domain.models.User
import com.example.newsapp.users.domain.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    private val _state = MutableStateFlow(UsersState())
    val state: StateFlow<UsersState> = _state

    init {
        getUsers()
    }

    fun onAction(action: UsersAction) {
        when (action) {
            UsersAction.RetryUsers -> {
                getUsers()
            }
            else -> Unit
        }
    }

    private fun getUsers() {
        viewModelScope.launch(Dispatchers.Main) {

            _state.emit(
                _state.value.copy(
                    status = UsersStatus.LOADING,
                    users = (0..20).map {
                        User(
                            id = it,
                            firstName = "",
                            lastName = "",
                            email = "",
                            birthDate = "",
                            phone = "",
                            website = "",
                            address = Address(
                                street = "",
                                suite = "",
                                city = "",
                                zipcode = "",
                                geo = Geo(
                                    lat = 0.0,
                                    lng = 0.0
                                )
                            )
                        )
                    }
                )
            )

            withContext(Dispatchers.IO) {
                val result = userRepository.getUsers()

                Log.d("USERS", "Users: $result")

                withContext(Dispatchers.Main) {
                    _state.emit(
                        when (result) {
                            Result.Error -> {
                                _state.value.copy(
                                    status = UsersStatus.ERROR
                                )
                            }
                            is Result.Success -> {
                                _state.value.copy(
                                    status = UsersStatus.SUCCESS,
                                    users = result.data,
                                )
                            }
                        }
                    )
                }
            }
        }
    }

}