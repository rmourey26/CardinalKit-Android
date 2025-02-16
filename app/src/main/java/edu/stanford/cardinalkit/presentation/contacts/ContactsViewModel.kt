package io.quantumone.cardinalkit.presentation.contacts

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.quantumone.cardinalkit.common.Constants
import io.quantumone.cardinalkit.domain.models.Contact
import io.quantumone.cardinalkit.domain.models.Response
import io.quantumone.cardinalkit.domain.usecases.contacts.ContactsUseCases
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ContactsViewModel @Inject constructor(
    @Named(Constants.CONTACTS_USE_CASES)
    private val useCases: ContactsUseCases
) : ViewModel() {
    var contactsState = mutableStateOf<Response<List<Contact>>>(Response.Loading)
        private set

    init {
        getContacts()
    }

    private fun getContacts() = viewModelScope.launch {
        useCases.getContacts().collect { response ->
            contactsState.value = response
        }
    }
}
