package io.quantumone.cardinalkit.domain.usecases.auth

import io.quantumone.cardinalkit.domain.repositories.AuthRepository

class SignUpWithEmail(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ) = repository.signUp(email, password)
}
