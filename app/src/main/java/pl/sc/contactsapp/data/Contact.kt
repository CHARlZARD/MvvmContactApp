package pl.sc.contactsapp.data

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contact(
    val firstName: String,
    val lastName: String?,
    val phoneNumber: String,
    val email: String?,
    @PrimaryKey val id: Int? = null
)
