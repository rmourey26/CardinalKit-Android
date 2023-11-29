package io.quantumone.cardinalkit.domain.repositories

import io.quantumone.cardinalkit.domain.models.Contact
import io.quantumone.cardinalkit.domain.models.Response
import kotlinx.coroutines.flow.Flow

interface ContactsRepository {
    fun getContacts(): Flow<Response<List<Contact>>>
}
