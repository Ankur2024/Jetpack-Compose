package com.example.firebaseapp.firestore.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.firebaseapp.common.CommonDialog
import com.example.firebaseapp.firestore.FirestoreModelResponse
import com.example.firebaseapp.utils.ResultState
import com.example.firebaseapp.utils.showMsg
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun FirestoreScreen(
    isInput: MutableState<Boolean>,
    viewModel: FirestoreViewModel = hiltViewModel()
    ) {
    val title = remember { mutableStateOf("") }
    val des = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val isDialog = remember {
        mutableStateOf(false)
    }
    val res = viewModel.res.value
    if (isDialog.value) {
        CommonDialog()
    }
    val isUpdate = remember {
        mutableStateOf(false)
    }
    if (isInput.value) {
        AlertDialog(
            onDismissRequest = { isInput.value = false },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    TextField(
                        value = title.value,
                        onValueChange = { title.value = it },
                        placeholder = { Text(text = "Enter Text") }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    TextField(
                        value = des.value,
                        onValueChange = { des.value = it },
                        placeholder = { Text(text = "Enter Description") }
                    )
                }
            },
            confirmButton = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(onClick = {
                        scope.launch(Dispatchers.Main) {
                            viewModel.insert(
                                FirestoreModelResponse.FirestoreItem(
                                    title = title.value,
                                    description = des.value
                                )
                            ).collect {
                                when (it) {
                                    is ResultState.Success -> {
                                        isDialog.value = false
                                        isInput.value = false
                                        context.showMsg(it.data)
                                    }
                                    is ResultState.Failure -> {
                                        isDialog.value = false
                                        context.showMsg(it.msg.toString())
                                    }
                                    ResultState.Loading -> {
                                        isDialog.value = true
                                    }
                                }
                            }
                        }
                    }) {
                        Text(text = "Save")

                    }
                }
            },
        )
    }

    if(isUpdate.value){
        UpdateData(item = viewModel.updateData.value, viewModel = viewModel, isUpdate = isUpdate, isDialog = isDialog)

    if (res.data.isNotEmpty()) {
        LazyColumn(

        ) {
            items(
                res.data,
                key = {
                    it.key!!
                }
            ) { item ->
                EachRow(itemState = item,
                    onUpdate = {
                        isUpdate.value = true
                        viewModel.setData(item)
                    }){
                    scope.launch (Dispatchers.Main){
                        viewModel.delete(item.key!!)
                            .collect{
                                when (it) {
                                    is ResultState.Failure -> {
                                        isDialog.value = false
                                        context.showMsg(it.msg.toString())
                                        viewModel.getItems()
                                    }

                                    ResultState.Loading -> {
                                        isDialog.value = true
                                    }

                                    is ResultState.Success -> {
                                        isDialog.value = false
                                        context.showMsg(it.data)
                                        viewModel.getItems()
                                    }
                                }
                            }
                    }
                }
            }
        }
    }
    if (res.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
    if (res.error.isNotEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = res.error)
        }
    }

}
}