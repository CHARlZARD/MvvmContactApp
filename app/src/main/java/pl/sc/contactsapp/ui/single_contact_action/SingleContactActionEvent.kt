package pl.sc.contactsapp.ui.single_contact_action

import android.content.Context
import pl.sc.contactsapp.data.Contact

sealed class SingleContactActionEvent {
    data class OnPhoneClick(val phoneNumber: String, val context: Context): SingleContactActionEvent()
    data class OnSMSClick(val phoneNumber: String, val context: Context): SingleContactActionEvent()
    data class OnEditClick(val contact: Contact): SingleContactActionEvent()
    data class OnDeleteClick(val contact: Contact): SingleContactActionEvent()
}
