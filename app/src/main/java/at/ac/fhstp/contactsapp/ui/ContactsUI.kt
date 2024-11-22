package at.ac.fhstp.contactsapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import at.ac.fhstp.contactsapp.data.Contact
import at.ac.fhstp.contactsapp.ui.theme.AppViewModelProvider
import at.ac.fhstp.contactsapp.ui.theme.Typography

enum class Routes(val route: String) {
    List("list"),
    //Detail has details of one user who hold a unique "contactId"
    Detail("detail/{contactId}")
}

@Composable
fun ContactsApp(modifier: Modifier = Modifier,
                navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = Routes.List.route
    ) {
        composable(Routes.List.route) {
            ContactsListView() {
                //This contactId receives id of a user from onContactClick.
                //this id is then used in the path for navigation.
                contactId -> navController.navigate("detail/$contactId")
            }
        }
        //This composable will show details of one user.
        composable(Routes.Detail.route, listOf(navArgument("contactId"){
            type = NavType.IntType
        })) {
            ContactDetailView()
        }
    }
}

@Composable
fun ContactsListView(
    modifier: Modifier = Modifier,
    contactsViewModel: ContactsViewModel = viewModel(factory = AppViewModelProvider.Factory),
    //This parameter (below) can hold Integer, or in this context a "contactId".
    onContactClick: (Int) -> Unit
    ) {
    val state by contactsViewModel.contactsUiState.collectAsStateWithLifecycle()

    // Log.i("ContactsApp", "Selected: ${state.selectedCardIndex}")

    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        // We use lazy column for dynamic lists or large lists
        // it will only draw the visible contacts

        Button(onClick = {
            contactsViewModel.onAddButtonClicked()
        }) {
            Text("Add new contact!")
        }
        Spacer(Modifier.height(16.dp))
        LazyColumn {
            itemsIndexed(state) { index, contact ->
                //if (index == state.selectedCardIndex) {
                //    ContactDetails(contact)
                //} else {
                ContactListItem(contact, onCardClick = {
                    //Once a contact's space area is selected, you will get his "contactId"
                    //This "contactId is then passed to contactId in 50th line"
                    onContactClick(contact.id)
                    //contactsViewModel.onCardClick(index)
                })
                // }
            }
        }
        /*Column {
            state.contacts.forEach {
                ContactListItem(it)
            }
        } */
    }
}

//ContactListItem function returns details for one single contact using Contact object.
@Composable
fun ContactListItem(contact: Contact, onCardClick: () -> Unit, modifier: Modifier = Modifier) {
    OutlinedCard(onClick = { onCardClick() }, modifier = modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Column(Modifier.padding(16.dp)) {
            Text(contact.name, style = Typography.headlineMedium)
        }
    }
}

@Composable
fun ContactDetailView (
    contactDetailViewModel: ContactDetailViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val state = contactDetailViewModel.contactDetailUiState.collectAsStateWithLifecycle()

    ContactDetails(state.value.contact)
}

// Shows details of a user
@Composable
fun ContactDetails(contact: Contact, modifier: Modifier = Modifier) {
    OutlinedCard(modifier.fillMaxWidth()
        .padding(8.dp)) {
        Column(Modifier.padding(16.dp)) {
            Text(contact.name, style = Typography.headlineMedium)
            Row {
                Text("Tel: ${contact.telephoneNumber}", style = Typography.headlineSmall)
                Spacer(Modifier.width(16.dp))
                Text("Age: ${contact.age}", style = Typography.headlineSmall)
            }
        }
    }
}

@Preview
@Composable
private fun ContactDetailsPreview() {
    ContactDetails(Contact(0, "Andrea", "+4354897", 28))
}

@Preview
@Composable
private fun ContactListItemPreview() {
    ContactListItem(Contact(0, "Andrea", "+4354897", 28), {})
}