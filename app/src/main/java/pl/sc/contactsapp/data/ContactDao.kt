package pl.sc.contactsapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: Contact)

    @Delete
    suspend fun  deleteContact(contact: Contact)

    @Query("SELECT * FROM contact WHERE id = :id") // use ':' because we want to use parameter
    suspend fun getContactById(id: Int): Contact?

    @Query("SELECT * FROM contact")     //if something change room will update in real time
    fun getContacts(): Flow<List<Contact>>
}
