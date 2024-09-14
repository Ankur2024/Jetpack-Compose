package com.example.firebaseapp.firebaserealtimedb.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.firebaseapp.firebaserealtimedb.RealtimeModelResponse
import com.example.firebaseapp.utils.ResultState
import com.example.firebaseapp.utils.showMsg
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun RealtimeScreen(
    modifier: Modifier = Modifier,
    isInsert: MutableState<Boolean>,
    viewModel: RealtimeViewModel = hiltViewModel()
) {
    val title = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val isDialog = remember { mutableStateOf(false) }
    val res = viewModel.res.value
    val isUpdate = remember { mutableStateOf(false) }

    val updateKey = remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, bottom = 16.dp)
    ) {
        if (isInsert.value) {
            AlertDialog(
                onDismissRequest = { isInsert.value = false },
                text = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        TextField(
                            value = title.value,
                            onValueChange = { title.value = it },
                            placeholder = { Text(text = "Title") }
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        TextField(
                            value = description.value,
                            onValueChange = { description.value = it },
                            placeholder = { Text(text = "Description") }
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
                                if (isUpdate.value && updateKey.value != null) {
                                    viewModel.update(
                                        RealtimeModelResponse(
                                            key = updateKey.value!!,
                                            item = RealtimeModelResponse.RealtimeItems(
                                                title.value,
                                                description.value
                                            )
                                        )
                                    ).collect { result ->
                                        when (result) {
                                            is ResultState.Failure -> {
                                                context.showMsg(msg = result.msg.toString())
                                                isDialog.value = false
                                                isInsert.value = false
                                            }
                                            is ResultState.Success -> {
                                                context.showMsg(msg = result.data)
                                                isInsert.value = false
                                                title.value = ""
                                                description.value = ""
                                                isUpdate.value = false
                                            }
                                            ResultState.Loading -> {
                                                isDialog.value = true
                                            }
                                        }
                                    }
                                } else {
                                    viewModel.insert(
                                        RealtimeModelResponse.RealtimeItems(
                                            title.value,
                                            description.value
                                        )
                                    ).collect { result ->
                                        when (result) {
                                            is ResultState.Failure -> {
                                                context.showMsg(msg = result.msg.toString())
                                                isDialog.value = false
                                                isInsert.value = false
                                            }
                                            is ResultState.Success -> {
                                                context.showMsg(msg = "Insert Successfully...")
                                                isInsert.value = false
                                                title.value = ""
                                                description.value = ""
                                            }
                                            ResultState.Loading -> {
                                                isDialog.value = true
                                            }
                                        }
                                    }
                                }
                            }
                        }) {
                            Text(text = "Save")
                        }
                    }
                }
            )
        }

        if (res.item.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                items(
                    res.item,
                    key = {
                        it.key!!
                    }
                ) { res ->
                    EachRow(
                        itemState = res.item!!,
                        onUpdate = {
                            isUpdate.value = true
                            title.value = res.item.title!!
                            description.value = res.item.description!!
                            updateKey.value = res.key // Set the key for updating the record
                            isInsert.value = true
                        }
                    ) {
                        scope.launch(Dispatchers.Main) {
                            viewModel.delete(res.key!!).collect {
                                when (it) {
                                    is ResultState.Success -> {
                                        context.showMsg(msg = it.data)
                                        isDialog.value = false
                                    }
                                    is ResultState.Failure -> {
                                        context.showMsg(msg = it.msg.toString())
                                        isDialog.value = false
                                    }
                                    ResultState.Loading -> {
                                        isDialog.value = true
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }

        if (res.isLoader) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        if (res.error.isNotEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = res.error)
            }
        }
    }
}

@Composable
fun EachRow(
    itemState: RealtimeModelResponse.RealtimeItems,
    onUpdate: () -> Unit = {},
    onDelete: () -> Unit = {}
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(1.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onUpdate() }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = itemState.title!!,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    )
                    IconButton(
                        onClick = { onDelete() },
                        modifier = Modifier.align(Alignment.CenterVertically)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "",
                            tint = Color.Red
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = itemState.description!!,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray
                    )
                )
            }
        }
    }
}


//@Composable
//fun Update(
//    isUpdate:MutableState<Boolean>,
//    itemState: RealtimeModelResponse,
//    viewModel: RealtimeViewModel
//) {
//    val title = remember { mutableStateOf(itemState.item?.title) }
//    val des = remember { mutableStateOf(itemState.item?.description) }
//    val scope = rememberCoroutineScope()
//    val context = LocalContext.current
//
//    if(isUpdate.value){
//
//        AlertDialog(onDismissRequest = { isUpdate.value = false },
//            text = {
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(10.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    TextField(value = title.value!!, onValueChange = {
//                        title.value = it
//                    },
//                        placeholder = { Text(text = "Title") }
//                    )
//                    Spacer(modifier = Modifier.height(10.dp))
//                    TextField(value = des.value!!, onValueChange = {
//                        des.value = it
//                    },
//                        placeholder = { Text(text = "description") }
//                    )
//                }
//            },
//            buttons = {
//                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Center) {
//                    Button(onClick = {
//                        scope.launch(Dispatchers.Main) {
//                            viewModel.update(
//                                RealtimeModelResponse(
//                                    item = RealtimeModelResponse.RealtimeItems(
//                                        title.value,
//                                        des.value
//                                    ),
//                                    key = itemState.key
//                                )
//                            ).collect {
//                                when (it) {
//                                    is ResultState.Success -> {
//                                        context.showMsg(
//                                            msg = it.data
//                                        )
//                                        isUpdate.value = false
//
//                                    }
//                                    is ResultState.Failure -> {
//                                        context.showMsg(
//                                            msg = it.msg.toString()
//                                        )
//                                    }
//                                    ResultState.Loading -> {
//                                    }
//                                }
//                            }
//                        }
//                    }) {
//                        Text(text = "Save")
//                    }
//                }
//            }
//        )
//
//    }
//
//}

