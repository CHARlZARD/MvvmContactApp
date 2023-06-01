package pl.sc.contactsapp.ui.single_contact_action

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import pl.sc.contactsapp.data.Contact
import pl.sc.contactsapp.data.ContactRepository
import pl.sc.contactsapp.util.Route
import pl.sc.contactsapp.util.UiEvent
import javax.inject.Inject

@HiltViewModel
class SingleContactActionViewModel @Inject constructor(
    private val repository: ContactRepository,
    safeStateHandle: SavedStateHandle
) : ViewModel() {

    val contactId = safeStateHandle.get<Int>("contactId")!!
    private val _state = mutableStateOf<Contact?>(null)
    val state: MutableState<Contact?> = _state

    fun searchContact(): Contact {
        return runBlocking {
            repository.getContactById(contactId)!!
        }
    }

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SingleContactActionEvent) {
        when (event) {
            is SingleContactActionEvent.OnPhoneClick -> {
                startDial(event.phoneNumber, event.context)
            }
            is SingleContactActionEvent.OnSMSClick -> {
                startSMS(event.phoneNumber, event.context)
            }
            is SingleContactActionEvent.OnEditClick -> {
                sendUiEvent(UiEvent.Navigate(Route.ADD_EDIT_CONTACT + "?contactId=${event.contact.id}"))
            }
            is SingleContactActionEvent.OnDeleteClick -> {
                viewModelScope.launch {
                    repository.deleteContact(event.contact)
                    sendUiEvent(
                        UiEvent.ShowSnackbar(
                            message = "Contact deleted",
                            action = "Undo"
                        )
                    )
                }
            }
        }
    }

    private fun startSMS(phoneNumber: String, context: Context) {
        viewModelScope.launch {
            val uri = Uri.parse("smsto: $phoneNumber")
            val intent = Intent(Intent.ACTION_SEND, uri)
            try {
                context.startActivity(intent)
            } catch (s: SecurityException) {
                Toast.makeText(context, "An error occurred", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun startDial(phoneNumber: String, context: Context) {
        viewModelScope.launch {
            val uri = Uri.parse("tel:$phoneNumber")
            val intent = Intent(Intent.ACTION_DIAL, uri)
            try {
                context.startActivity(intent)
            } catch (s: SecurityException) {
                Toast.makeText(context, "An error occurred", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}
