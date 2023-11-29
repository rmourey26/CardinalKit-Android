package io.quantumone.cardinalkit.domain.usecases.auth

import io.quantumone.cardinalkit.domain.repositories.AuthRepository

class ResetPassword(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        email: String
    ) = repository.resetPassword(email)
}
