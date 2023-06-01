package pl.sc.contactsapp.ui.add_edit_contact

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import pl.sc.contactsapp.data.Contact
import pl.sc.contactsapp.data.ContactRepository
import pl.sc.contactsapp.util.UiEvent
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class AddEditContactViewModel @Inject constructor(
    private val repository: ContactRepository,
    safeStateHandle: SavedStateHandle
) : ViewModel() {

    var contact by mutableStateOf<Contact?>(null)
        private set

    var firstName by mutableStateOf("")
        private set

    var lastName by mutableStateOf("")
        private set

    var phoneNumber by mutableStateOf("")
        private set

    var email by mutableStateOf("")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val contactId =
            safeStateHandle.get<Int>("contactId")!! //jeżeli kontak już istnieje to chcemy se go załadować
        if (contactId != -1) {
            viewModelScope.launch {
                repository.getContactById(contactId)?.let { contact ->
                    firstName = contact.firstName
                    lastName = contact.lastName ?: ""
                    phoneNumber = contact.phoneNumber
                    email = contact.email ?: ""
                    this@AddEditContactViewModel.contact = contact
                }
            }
        }
    }

    fun onEvent(event: AddEditContactEvent) {
        when (event) {
            is AddEditContactEvent.OnFirstNameChange -> {
                firstName = event.firstName
            }
            is AddEditContactEvent.OnLastNameChange -> {
                lastName = event.lastName
            }
            is AddEditContactEvent.OnEmailChange -> {
                email = event.email
            }
            is AddEditContactEvent.OnPhoneNumberChange -> {
                phoneNumber = event.phoneNumber
            }
            is AddEditContactEvent.OnSaveClick -> {
                viewModelScope.launch {
                    if (firstName.isBlank()) {
                        sendUiEvent(
                            UiEvent.ShowSnackbar(
                                message = "The First Name can't be empty"
                            )
                        )
                        return@launch
                    }
                    else if(phoneNumber.isBlank()){
                        sendUiEvent(
                            UiEvent.ShowSnackbar(
                                message = "The Phone Number can't be empty"
                            )
                        )
                        return@launch
                    }
                    repository.insertContact(
                        Contact(
                            firstName = firstName,
                            lastName = lastName,
                            phoneNumber = phoneNumber,
                            email = email,
                            id = contact?.id
                        )
                    )
                    sendUiEvent(UiEvent.PopBackStack)
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}
