package pl.sc.contactsapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contact(
    val firstName: String,
    val lastName: String?,
    val phoneNumber: String,
    val email: String?,
    val iconColorR: Int,
    val iconColorG: Int,
    val iconColorB: Int,
    @PrimaryKey val id: Int? = null
)
