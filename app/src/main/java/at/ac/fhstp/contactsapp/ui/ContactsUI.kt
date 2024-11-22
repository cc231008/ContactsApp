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
    List("list"), // Route for the list of contacts
    Detail("detail/{contactId}") //"Detail" has details of one user who hold a unique "contactId"
}

// Main composable function for the Contacts app
@Composable
fun ContactsApp(modifier: Modifier = Modifier, navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = Routes.List.route
    ) {
        composable(Routes.List.route) {
            ContactsListView() {
                //This contactId receives id of a user from onContactClick.
                contactId -> navController.navigate("detail/$contactId")
            }
        }
        //This composable will show details of one contact.
        composable(Routes.Detail.route, listOf(navArgument("contactId"){
            type = NavType.IntType
        })) {
            ContactDetailView() //Show the detail view of a contact
        }
    }
}

//Composable function to display the list of contacts
@Composable
fun ContactsListView(
    modifier: Modifier = Modifier,
    contactsViewModel: ContactsViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onContactClick: (Int) -> Unit // Lambda function to handle contact clicks
    ) {

    /*
    "state" - variable holds the current state of the contactsUiState which primarily has all contacts.
    collectAsStateWithLifecycle - updates the UI composable whenever the data in the flow changes,
    so that UI is provided with the latest data from the ViewModel.
     */
    val state by contactsViewModel.contactsUiState.collectAsStateWithLifecycle()

    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {

        Button(onClick = {
            contactsViewModel.onAddButtonClicked() //Add a new contact when the button is clicked
        }) {
            Text("Add new contact!")
        }
        Spacer(Modifier.height(16.dp))

        // We use LazyColumn for large lists. It's done by loading only the visible items on the screen to improve performance.
        LazyColumn {
            //itemsIndexed - iterates over the list of contacts (state) and provides the index with the item (contact) to the lambda function.
            itemsIndexed(state) { index, contact ->
                //For each contact, it calls the ContactListItem composable, passing the contact and a click handler.
                //onCardClick - when a contact item is clicked, it triggers the onContactClick function.
                ContactListItem(contact, onCardClick = {
                    //Once a contact item is clicked, the contactId of that contact is then obtained.
                    onContactClick(contact.id)
                })
            }
        }
    }
}

//Composable function to display a single contact item
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

//Composable function to display the detail view of a contact
@Composable
fun ContactDetailView (
    contactDetailViewModel: ContactDetailViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val state = contactDetailViewModel.contactDetailUiState.collectAsStateWithLifecycle()

    ContactDetails(state.value.contact) //Display contact details
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