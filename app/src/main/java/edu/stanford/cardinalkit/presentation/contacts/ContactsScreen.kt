package io.quantumone.cardinalkit.presentation.contacts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import io.quantumone.cardinalkit.R
import io.quantumone.cardinalkit.domain.models.Response
import io.quantumone.cardinalkit.presentation.common.ProgressIndicator
import io.quantumone.cardinalkit.presentation.contacts.components.ContactCard
import timber.log.Timber

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsScreen(
    viewModel: ContactsViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.contacts_screen_title),
                        modifier = Modifier.padding(5.dp),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Light,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors()
            )
        },
        containerColor = MaterialTheme.colorScheme.surface,
        content = { padding ->
            Column(modifier = Modifier.padding(bottom = 50.dp)) {
                when (val contactsResponse = viewModel.contactsState.value) {
                    is Response.Error -> Timber.d(contactsResponse.e?.message.toString())
                    is Response.Loading -> ProgressIndicator()
                    is Response.Success -> LazyColumn(
                        modifier = Modifier
                            .padding(top = 60.dp)
                    ) {
                        if (contactsResponse.data != null) {
                            items(
                                items = contactsResponse.data
                            ) { contact ->
                                ContactCard(contact)
                            }
                        }
                    }
                }
            }
        }
    )
}
