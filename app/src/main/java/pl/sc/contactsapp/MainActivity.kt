package pl.sc.contactsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import pl.sc.contactsapp.data.Contact
import pl.sc.contactsapp.ui.add_edit_contact.AddEditContactScreen
import pl.sc.contactsapp.ui.contact_list.ContactListScreen
import pl.sc.contactsapp.ui.single_contact_action.SingleContactActionView
import pl.sc.contactsapp.ui.single_contact_action.SingleContactActionViewModel
import pl.sc.contactsapp.ui.theme.ContactAppTheme
import pl.sc.contactsapp.util.Route

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContactAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Route.CONTACT_LIST
                ) {
                    composable(
                        Route.CONTACT_LIST
                    ) {
                        ContactListScreen(
                            onNavigate = {
                                navController.navigate(it.route)
                            }
                        )
                    }
                    composable(
                        route = Route.SINGLE_CONTACT_ACTION + "?contactId={contactId}",
                        arguments = listOf(
                            navArgument(name = "contactId") {
                                type = NavType.IntType
                                defaultValue = -1
                            })
                    ) {
                        SingleContactActionView(
                            onNavigate = {
                                navController.navigate(it.route)
                            },
                            onPopBackStack = {
                                navController.popBackStack()
                            }
                        )
                    }
                    composable(
                        route = Route.ADD_EDIT_CONTACT + "?contactId={contactId}",
                        arguments = listOf(
                            navArgument(name = "contactId") {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ) {
                        AddEditContactScreen(onPopBackStack = {
                            navController.popBackStack()
                        }
                        )
                    }
                }
            }
        }
    }
}
