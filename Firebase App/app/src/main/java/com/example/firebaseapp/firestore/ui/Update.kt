package com.example.firebaseapp.firestore.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import com.example.firebaseapp.firestore.FirestoreModelResponse
import com.example.firebaseapp.utils.ResultState
import com.example.firebaseapp.utils.showMsg
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun UpdateData(
    item: FirestoreModelResponse,
    viewModel: FirestoreViewModel,
    isUpdate: MutableState<Boolean>,
    isDialog: MutableState<Boolean>
){
    val title = remember { mutableStateOf(item.item?.title) }
    val des = remember { mutableStateOf(item.item?.description) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = { isUpdate.value = false },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                TextField(
                    value = title.value!!,
                    onValueChange = { title.value = it },
                    placeholder = { Text(text = "Enter Text") }
                )
                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    value = des.value!!,
                    onValueChange = { des.value = it },
                    placeholder = { Text(text = "Enter Description") }
                )
            }
        },
        confirmButton = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                Button(onClick = {
                    scope.launch(Dispatchers.Main) {
                        viewModel.update(
                            FirestoreModelResponse(
                                item  = FirestoreModelResponse.FirestoreItem(
                                    title = title.value,
                                    description = des.value
                                ),
                                key = item.key
                            )
                        ).collect{
                            when(it){
                                is ResultState.Failure -> {
                                    isDialog.value = false
                                    context.showMsg(it.msg.toString())
                                }
                                ResultState.Loading -> {
                                    isDialog.value = true
                                }
                                is ResultState.Success -> {
                                    isUpdate.value = false
                                    isDialog.value = false
                                    context.showMsg(it.data)
                                    viewModel.getItems()
                                }
                            }
                        }
                    }
                }) {
                    Text(text = "Update")

                }
            }
        },
    )
}