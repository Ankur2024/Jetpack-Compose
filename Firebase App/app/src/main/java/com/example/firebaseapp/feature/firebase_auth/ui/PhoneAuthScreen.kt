package com.example.firebaseapp.feature.firebase_auth.ui

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.firebaseapp.common.CommonDialog
import com.example.firebaseapp.common.OtpView
import com.example.firebaseapp.utils.ResultState
import com.example.firebaseapp.utils.showMsg
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun PhoneAuthScreen(
    activity: Activity,
    viewModel: AuthViewModel = hiltViewModel()
) {
    var mobile by remember { mutableStateOf("")}
    var otp by remember { mutableStateOf("")}
    val scope = rememberCoroutineScope()
    var isDialog by remember{ mutableStateOf(false)}

    if(isDialog)
        CommonDialog()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Enter Mobile Number")
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(value = mobile, onValueChange = {
                mobile = it
            }, label = {Text("+91")}, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {
                scope.launch(Dispatchers.Main){
                    viewModel.createUserWithPhone(
                        mobile,
                        activity
                    ).collect{
                        when(it){
                            is ResultState.Success->{
                                isDialog = false
                                activity.showMsg(it.data)
                            }
                            is ResultState.Failure->{
                                isDialog = false
                                activity.showMsg(it.msg.toString())
                            }
                            ResultState.Loading->{
                                isDialog = true
                            }
                        }
                    }
                }
            }) {
                Text(text = "Submit")
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Enter Otp")
            Spacer(modifier = Modifier.height(20.dp))
            OtpView(otpText = otp){
                otp = it
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {
                scope.launch(Dispatchers.Main){
                    viewModel.signInWithCredential(
                        otp
                    ).collect{
                        when(it){
                            is ResultState.Success->{
                                isDialog = false
                                activity.showMsg(it.data)
                            }
                            is ResultState.Failure->{
                                isDialog = false
                                activity.showMsg(it.msg.toString())
                            }
                            ResultState.Loading->{
                                isDialog = true
                            }
                        }
                    }
                }
            }) {
                Text(text = "Verify")
            }
        }
    }

}