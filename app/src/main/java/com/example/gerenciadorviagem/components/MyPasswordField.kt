package com.example.gerenciadorviagem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff


@Composable
fun MyPasswordField(value:String, onValueChange: (String) -> Unit, label:String, required:Boolean){
    var isTouched = remember {
        mutableStateOf(false)
    }
    var focus = remember {
        FocusRequester()
    }
    var shown = remember {
        mutableStateOf(false)
    }
    Row (modifier = Modifier.fillMaxWidth().padding(5.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
        OutlinedTextField(value = value,
            onValueChange = {
                isTouched.value = true
                onValueChange(it)
            },
            Modifier.focusRequester(focus).onFocusEvent{if (it.hasFocus) {
                isTouched.value = true
            }}.weight(4f),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                focusedLabelColor = Color.White,
                cursorColor = Color.White,
                unfocusedBorderColor = Color.Gray,
                unfocusedLabelColor = Color.Gray),
            label = { Text(text = label) },
            supportingText = {if (required) {if (isTouched.value && value.isBlank()){
                Text(text = "$label é obrigatório")
            } }},
            trailingIcon = {
                val image = if (shown.value)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff
                IconButton (onClick = { shown.value = !shown.value }) {
                    Icon(imageVector = image, contentDescription = if (shown.value) "Esconder senha" else "Mostrar senha")
                }
            },
            visualTransformation = if (shown.value) VisualTransformation.None else PasswordVisualTransformation(),
            isError = if (required) {value.isBlank() && isTouched.value} else false

        )
    }
}

@Composable
@Override
fun MyPasswordField(value:String, confirmValue:String, onValueChange: (String) -> Unit, label:String, required:Boolean){
    var isTouched = remember {
        mutableStateOf(false)
    }
    var focus = remember {
        FocusRequester()
    }
    var shown = remember {
        mutableStateOf(false)
    }

    Row (modifier = Modifier.fillMaxWidth().padding(5.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
        OutlinedTextField(value = value,
            onValueChange = {
                isTouched.value = true
                onValueChange(it)
            },
            Modifier.focusRequester(focus).onFocusEvent{if (it.hasFocus) {
                isTouched.value = true
            }}.weight(4f),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                focusedLabelColor = Color.White,
                cursorColor = Color.White,
                unfocusedBorderColor = Color.Gray,
                unfocusedLabelColor = Color.Gray),
            label = { Text(text = label) },
            supportingText = {if (required) {if (isTouched.value && !value.equals(confirmValue)){
                Text(text = "As senhas não conferem")
            } }},
            trailingIcon = {
                val image = if (shown.value)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff
                IconButton (onClick = { shown.value = !shown.value }) {
                    Icon(imageVector = image, contentDescription = if (shown.value) "Esconder senha" else "Mostrar senha")
                }
            },
            visualTransformation = if (shown.value) VisualTransformation.None else PasswordVisualTransformation(),
            isError = if (required) {!value.equals(confirmValue) && isTouched.value} else false

        )
    }
}
