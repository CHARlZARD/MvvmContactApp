package pl.sc.contactsapp.ui.contact_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import pl.sc.contactsapp.data.Contact
import pl.sc.contactsapp.data.ContactRepository
import pl.sc.contactsapp.util.Route
import pl.sc.contactsapp.util.UiEvent
import javax.inject.Inject

@HiltViewModel
class ContactListViewModel @Inject constructor(
    private val repository: ContactRepository
) : ViewModel(){

    val contact = repository.getContacts()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: ContactListEvent){
        when (event){
            is ContactListEvent.OnAddContactClick -> {
                sendUiEvent(UiEvent.Navigate(Route.ADD_EDIT_CONTACT))
            }
            is ContactListEvent.OnContactClick -> {
                sendUiEvent(UiEvent.Navigate(Route.SINGLE_CONTACT_ACTION + "?contactId=${event.contact.id}"))
            }
        }
    }
    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
        _uiEvent.send(event)
        }
    }
}
