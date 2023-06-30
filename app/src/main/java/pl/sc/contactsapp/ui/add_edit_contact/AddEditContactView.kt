package pl.sc.contactsapp.ui.add_edit_contact

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import pl.sc.contactsapp.util.UiEvent

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddEditContactScreen(
    onPopBackStack: () -> Unit,
    viewModel: AddEditContactViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.PopBackStack -> onPopBackStack()
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                }
                else -> Unit
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(AddEditContactEvent.OnSaveClick)
            }) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Save")
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = viewModel.firstName,
                singleLine = true,
                placeholder = {
                              Text(text = "First Name")
                },
                onValueChange = {
                    viewModel.onEvent(AddEditContactEvent.OnFirstNameChange(it))
            })
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = viewModel.lastName,
                singleLine = true,
                placeholder = {
                    Text(text = "Last Name")
                },
                onValueChange = {
                    viewModel.onEvent(AddEditContactEvent.OnLastNameChange(it))
                })
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = viewModel.phoneNumber,
                singleLine = true,
                placeholder = {
                    Text(text = "Phone Number")
                },
                onValueChange = {
                    viewModel.onEvent(AddEditContactEvent.OnPhoneNumberChange(it))
                })
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = viewModel.email,
                singleLine = true,
                placeholder = {
                    Text(text = "Email")
                },
                onValueChange = {
                    viewModel.onEvent(AddEditContactEvent.OnEmailChange(it))
                })
            Spacer(modifier = Modifier.height(8.dp))
        }

    }
}
