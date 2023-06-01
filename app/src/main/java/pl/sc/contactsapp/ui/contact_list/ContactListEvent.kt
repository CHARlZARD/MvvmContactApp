package pl.sc.contactsapp.ui.contact_list

import pl.sc.contactsapp.data.Contact

sealed class ContactListEvent {
    data class OnContactClick(val contact: Contact) : ContactListEvent()
    object OnAddContactClick: ContactListEvent()
    object OnUndoDeleteClick: ContactListEvent()
}
