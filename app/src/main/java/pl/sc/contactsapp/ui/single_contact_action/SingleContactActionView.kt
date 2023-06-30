package pl.sc.contactsapp.ui.single_contact_action

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import pl.sc.contactsapp.data.Contact
import pl.sc.contactsapp.ui.contact_list.ContactIcon
import pl.sc.contactsapp.ui.single_contact_action.SingleContactActionEvent.*
import pl.sc.contactsapp.util.UiEvent

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SingleContactActionView(
    onNavigate: (UiEvent.Navigate) -> Unit,
    onPopBackStack: () -> Unit,
    viewModel: SingleContactActionViewModel = hiltViewModel()
) {
    val contact: Contact = viewModel.searchContact()
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
                is UiEvent.Navigate -> onNavigate(event)
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            Row(
            modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
            Arrangement.Center,
            ) {
                ContactIcon(
                    symbol = contact.firstName[0].toString().uppercase(),
                    modifier = Modifier
                        .background(
                            Color(
                                contact.iconColorR,
                                contact.iconColorG,
                                contact.iconColorB,
                            )
                        )
                        .width(130.dp)
                        .height(130.dp)
                        .padding(10.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "${contact.firstName} ${contact.lastName}",
                fontSize = 25.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = contact.phoneNumber,
                fontSize = 20.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                Arrangement.Center,
            ) {
                val context = LocalContext.current
                Button(
                    onClick = {
                        viewModel.onEvent(OnPhoneClick(contact.phoneNumber, context))
                    }) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = "Call",
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(
                    onClick = {
                        viewModel.onEvent((OnSMSClick(contact.phoneNumber, context)))
                    }) {
                    Icon(
                        imageVector = Icons.Default.MailOutline,
                        contentDescription = "SMS",
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(
                    onClick = {
                        viewModel.onEvent(OnEditClick(contact))
                    }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit"
                    )
                }
            }
        }
    }
}
