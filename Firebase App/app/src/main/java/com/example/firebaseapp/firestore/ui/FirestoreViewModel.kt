package com.example.firebaseapp.firestore.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firebaseapp.firestore.FirestoreModelResponse
import com.example.firebaseapp.firestore.repository.FirestoreRepository
import com.example.firebaseapp.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirestoreViewModel @Inject constructor(
    private val repo: FirestoreRepository
): ViewModel(){
    private val _res: MutableState<FirestoreState> = mutableStateOf(FirestoreState())
    val res: State<FirestoreState> = _res
    init {
        getItems()
    }
    fun insert(item: FirestoreModelResponse.FirestoreItem) = repo.insert(item)
    fun getItems()  = viewModelScope.launch {
        repo.getItems().collect{
            when(it){
                is ResultState.Failure -> {
                    _res.value = FirestoreState(
                        error = it.msg.toString()
                    )
                }
                ResultState.Loading -> {
                    _res.value = FirestoreState(
                        isLoading = true
                    )
                }
                is ResultState.Success -> {
                    _res.value = FirestoreState(
                        data = it.data
                    )
                }
            }
        }
    }
    fun delete(key: String) = repo.delete(key)
    fun update(item: FirestoreModelResponse) = repo.update(item)
}

data class FirestoreState(
    val data: List<FirestoreModelResponse> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false,
    )