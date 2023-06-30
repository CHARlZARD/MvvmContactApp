package pl.sc.contactsapp.ui.contact_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.sc.contactsapp.data.Contact

@Composable
fun ContactItem(
    contact: Contact,
    onEvent: (ContactListEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ContactIcon(
            symbol = contact.firstName[0].toString().uppercase(),
            modifier = Modifier
                .background(
                    Color(
                        contact.iconColorR,
                        contact.iconColorG,
                        contact.iconColorB,
                    )
                )
                .aspectRatio(1f)
                .weight(1f)
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            modifier = Modifier
                .weight(6f),
        ) {
            Text(
                text = contact.firstName + " " + contact.lastName,
                fontSize = 20.sp,
                color = Color.Black,
            )
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}
