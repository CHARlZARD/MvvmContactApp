package pl.sc.contactsapp.ui.add_edit_contact

sealed class AddEditContactEvent {
    data class OnFirstNameChange(val firstName : String) : AddEditContactEvent()
    data class OnLastNameChange(val lastName : String) : AddEditContactEvent()
    data class OnPhoneNumberChange(val phoneNumber : String) : AddEditContactEvent()
    data class OnEmailChange(val email : String) : AddEditContactEvent()
    object OnSaveClick : AddEditContactEvent()
}
