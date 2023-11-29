package io.quantumone.cardinalkit.domain.usecases.auth

import com.google.firebase.auth.AuthCredential
import io.quantumone.cardinalkit.domain.repositories.AuthRepository

class SignInWithGoogle(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        googleCredential: AuthCredential
    ) = repository.firebaseSignInWithGoogle(googleCredential)
}
