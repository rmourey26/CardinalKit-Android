package io.quantumone.cardinalkit.domain.usecases.auth

import io.quantumone.cardinalkit.domain.repositories.AuthRepository

class UpdateLastActive(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() = repository.updateLastActive()
}
