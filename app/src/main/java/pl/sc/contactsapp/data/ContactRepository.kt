package pl.sc.contactsapp.data

import kotlinx.coroutines.flow.Flow

interface ContactRepository {

    suspend fun insertContact(contact: Contact)

    suspend fun deleteContact(contact: Contact)

    suspend fun getContactById(id: Int): Contact?

    fun getContacts(): Flow<List<Contact>>
}
