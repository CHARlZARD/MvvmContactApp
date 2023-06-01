package pl.sc.contactsapp.ui.contact_list

import androidx.compose.runtime.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import pl.sc.contactsapp.data.Contact

class Searching(
  //  contacts: State<List<Contact>>,
    scope: CoroutineScope
) {
//
//    fun doesMatchSearchQuery(query: String, contact: Contact): Boolean {
//        val matchingCombinations = listOf(
//            "${contact.firstName}${contact.lastName}",
//            "${contact.firstName} ${contact.lastName}",
//            "${contact.firstName.first()}${contact.lastName?.first()}"
//        )
//        return matchingCombinations.any() {
//            it.contains(query, ignoreCase = true)
//        }
//    }
//    private val _searchText = MutableStateFlow("")
//    val searchText = _searchText.asStateFlow()
//
//    private val _isSearching = MutableStateFlow(false)
//    val isSearching = _isSearching.asStateFlow()
//
//    private val _contacts = MutableStateFlow(contacts)
//    val contact = searchText
//        .combine(_contacts) { text, contact ->
//            if (text.isBlank()) {
//                contact
//            } else {
//                contact.filter {
//                  doesMatchSearchQuery(text, it)
//                }
//            }
//        }
//        .stateIn(
//            ;
//            SharingStarted.WhileSubscribed(5000),
//            _contacts.value
//        )

    fun onSearchTextChange(text: String) {
//        _searchText.value = text
    }

}
