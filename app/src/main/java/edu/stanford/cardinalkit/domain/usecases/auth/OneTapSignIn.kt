package io.quantumone.cardinalkit.domain.usecases.auth

import io.quantumone.cardinalkit.domain.repositories.AuthRepository

class OneTapSignIn(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() = repository.oneTapSignInWithGoogle()
}
