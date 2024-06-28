package com.harshjoshi.passwordmanager.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.harshjoshi.passwordmanager.data.Password

@Composable
fun PasswordViewCard(
    password: Password,
    isModalBottomSheetOpen: MutableState<Boolean>,
    passwordInBottomSheet: MutableState<Password?>
){

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            .clip(RoundedCornerShape(50))
            .border(1.dp, MyDarkGrey, RoundedCornerShape(50))
            .background(Color.White)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 16.dp, bottom = 8.dp)
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                Text(text = password.accountType,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MyBlack
                )
                Spacer(modifier = Modifier.size(16.dp))
                BasicTextField(
                    enabled = false,
                    visualTransformation = PasswordVisualTransformation(),
                    textStyle = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MyDarkGrey
                    ),
                    value = password.password,
                    onValueChange = {})
            }
            IconButton(onClick = {
                passwordInBottomSheet.value = password
                isModalBottomSheetOpen.value = true
            }) {
                Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null)
            }
        }
    }

}