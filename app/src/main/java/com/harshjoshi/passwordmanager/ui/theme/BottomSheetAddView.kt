package com.harshjoshi.passwordmanager.ui.theme

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.harshjoshi.passwordmanager.PasswordManagerViewModel
import com.harshjoshi.passwordmanager.data.Password
import com.harshjoshi.passwordmanager.R


@Composable
fun BottomSheetAddView(vm: PasswordManagerViewModel, isBottomSheetOpen: MutableState<Boolean>){
    var accountName by remember { mutableStateOf("") }
    var usernameOrEmail by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context: Context = LocalContext.current

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Enter Account Name") },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                focusedIndicatorColor = MyBlue,
                focusedLabelColor = MyBlue,
                focusedTextColor = MyBlue,
                unfocusedContainerColor = Color.White,
                unfocusedIndicatorColor = MyDarkGrey,
                unfocusedLabelColor = MyDarkGrey,
                unfocusedTextColor = MyDarkGrey
            ),
            shape = RoundedCornerShape(30),
            value = accountName,
            onValueChange = { accountName = it })
        Spacer(modifier = Modifier.size(16.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Enter Username/Email") },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                focusedIndicatorColor = MyBlue,
                focusedLabelColor = MyBlue,
                focusedTextColor = MyBlue,
                unfocusedContainerColor = Color.White,
                unfocusedIndicatorColor = MyDarkGrey,
                unfocusedLabelColor = MyDarkGrey,
                unfocusedTextColor = MyDarkGrey
            ),
            shape = RoundedCornerShape(30),
            value = usernameOrEmail,
            onValueChange = { usernameOrEmail = it })
        Spacer(modifier = Modifier.size(16.dp))

        var passwordVisible by remember { mutableStateOf(false) }
        val passwordError = remember { mutableStateOf(false) }
        val passwordErrorValue = remember { mutableStateOf("") }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Enter Password") },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                focusedIndicatorColor = MyBlue,
                focusedLabelColor = MyBlue,
                focusedTextColor = MyBlue,
                unfocusedContainerColor = Color.White,
                unfocusedIndicatorColor = MyDarkGrey,
                unfocusedLabelColor = MyDarkGrey,
                unfocusedTextColor = MyDarkGrey
            ),
            shape = RoundedCornerShape(30),
            value = password,
            onValueChange = {
                password = it
                passwordError.value = !passwordIsCorrect(password, passwordErrorValue)
            },

            isError = passwordError.value,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible) {
                    painterResource(id = R.drawable.fish_eye_24)
                } else {
                    painterResource(id = R.drawable.eye_24)
                }
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(image, contentDescription = null)
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(autoCorrect = false),
            supportingText = {
                if(passwordError.value)
                    Text(text = passwordErrorValue.value)
            }
        )
        Spacer(modifier = Modifier.size(16.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = MyBlack
            ),
            onClick = {
                if(!passwordError.value){
                    val passwordToAdd = Password(
                        password = password,
                        username = usernameOrEmail,
                        accountType = accountName
                    )
                    vm.upsertPass(passwordToAdd)
                    password = ""
                    usernameOrEmail = ""
                    accountName = ""
                    isBottomSheetOpen.value = false
                }else{
                    Toast.makeText(
                        context,
                        "Password is Incorrect: "+passwordError.value,
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        ) {
            Text(
                text = "Add New Account",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.size(48.dp))
    }
}
