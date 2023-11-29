package io.quantumone.cardinalkit.domain.usecases.auth

import io.quantumone.cardinalkit.domain.repositories.AuthRepository

class GetAuthStatus(
    private val repository: AuthRepository
) {
    operator fun invoke() = repository.getAuthStatus()
}
