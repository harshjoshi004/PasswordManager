package com.harshjoshi.passwordmanager.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.harshjoshi.passwordmanager.PasswordManagerViewModel
import com.harshjoshi.passwordmanager.data.Password

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordsScreen(vm: PasswordManagerViewModel) {
    val passwords = vm.allPasswords.collectAsState(initial = emptyList())
    val bottomSheetState = rememberModalBottomSheetState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    var passwordInBottomSheet = remember {
        mutableStateOf<Password?>(null)
    }
    var isModalBottomSheetOpen = remember {
        mutableStateOf(false)
    }

    //Screen Content
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(
                text = "Password Manager",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MyBlack) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MyLightGrey,
                    scrolledContainerColor = MyDarkGrey
                ),
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        floatingActionButton = {
            Box(modifier = Modifier
                .wrapContentSize()
                .clip(RoundedCornerShape(30))
                .background(MyBlue))
            {
                IconButton(onClick = {
                    //Add
                    passwordInBottomSheet.value = null
                    isModalBottomSheetOpen.value = true
                }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(48.dp)
                            .padding(8.dp)
                    )
                }
            }
        }
    ) {pv->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MyLightGrey)
                .padding(pv),
        ){
            items(passwords.value){passwordObject->
                PasswordViewCard(password = passwordObject, isModalBottomSheetOpen, passwordInBottomSheet)
            }
        }
    }


    if (isModalBottomSheetOpen.value) {
        ModalBottomSheet(
            onDismissRequest = { isModalBottomSheetOpen.value = false },
            sheetState = bottomSheetState
        ) {
            if(passwordInBottomSheet.value == null){
                BottomSheetAddView(vm, isModalBottomSheetOpen)
            }else{
                passwordInBottomSheet.value?.let {passwordItem->
                    BottomSheetEditView(passwordObject = passwordItem, vm, isModalBottomSheetOpen)
                }
            }
        }
    }
}

fun passwordIsCorrect(password: String, passwordValue: MutableState<String>): Boolean {
    val errors = mutableListOf<String>()

    if (password.length < 8) {
        errors.add("Password must be at least 8 characters long.")
    }
    if (!password.any { it.isUpperCase() }) {
        errors.add("Password must contain at least one uppercase letter.")
    }
    if (!password.any { it.isLowerCase() }) {
        errors.add("Password must contain at least one lowercase letter.")
    }
    if (!password.any { it.isDigit() }) {
        errors.add("Password must contain at least one digit.")
    }
    if (!password.any { "!@#$%^&*()-_=+[]{};:'\",.<>/?\\|`~".contains(it) }) {
        errors.add("Password must contain at least one special character.")
    }

    return if (errors.isNotEmpty()) {
        passwordValue.value = errors.joinToString(separator = "\n")
        false
    } else {
        passwordValue.value = ""
        true
    }
}

