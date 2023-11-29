package io.quantumone.cardinalkit.domain.usecases.contacts

import io.quantumone.cardinalkit.domain.repositories.ContactsRepository

class GetContacts(
    private val repository: ContactsRepository
) {
    operator fun invoke() = repository.getContacts()
}
