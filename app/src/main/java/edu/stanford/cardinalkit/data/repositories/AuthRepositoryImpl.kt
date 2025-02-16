package io.quantumone.cardinalkit.data.repositories

import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue.serverTimestamp
import com.google.firebase.firestore.SetOptions
import io.quantumone.cardinalkit.common.Constants
import io.quantumone.cardinalkit.domain.models.Response
import io.quantumone.cardinalkit.domain.repositories.AuthRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private var oneTapClient: SignInClient,
    @Named(Constants.SIGN_IN_REQUEST)
    private var signInRequest: BeginSignInRequest,
    private var signInClient: GoogleSignInClient,
    @Named(Constants.USERS_REF)
    private val usersRef: CollectionReference
) : AuthRepository {
    override fun isAuthenticated() = auth.currentUser != null

    override suspend fun oneTapSignInWithGoogle() = flow {
        try {
            emit(Response.Loading)
            val result = oneTapClient.beginSignIn(signInRequest).await()
            emit(Response.Success(result))
        } catch (e: Exception) {
            emit(Response.Error(e))
        }
    }

    override suspend fun firebaseSignInWithGoogle(googleCredential: AuthCredential) = flow {
        try {
            emit(Response.Loading)
            val authResult = auth.signInWithCredential(googleCredential).await()
            val isNewUser = authResult.additionalUserInfo?.isNewUser
            emit(Response.Success(isNewUser))
        } catch (e: Exception) {
            emit(Response.Error(e))
        }
    }

    override suspend fun signIn(email: String, password: String) = flow {
        try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            val isNewUser = authResult.additionalUserInfo?.isNewUser
            emit(Response.Success(isNewUser))
        } catch (e: Exception) {
            emit(Response.Error(e))
        }
    }

    override suspend fun resetPassword(email: String) = flow {
        try {
            auth.sendPasswordResetEmail(email).await()
            emit(Response.Success(true))
        } catch (e: Exception) {
            emit(Response.Error(e))
        }
    }

    override suspend fun signUp(email: String, password: String) = flow {
        try {
            auth.createUserWithEmailAndPassword(email, password).await()
            auth.currentUser?.apply {
                usersRef.document(uid).set(
                    mapOf(
                        "userID" to uid,
                        "name" to displayName,
                        "email" to email,
                        "lastActive" to serverTimestamp(),
                        "createdDate" to serverTimestamp()
                    ),
                    SetOptions.merge()
                ).await()
                emit(Response.Success(true))
            }
        } catch (e: Exception) {
            emit(Response.Error(e))
        }
    }

    override fun getAuthStatus() = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser != null)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }

    override suspend fun saveUser() = flow {
        try {
            emit(Response.Loading)
            auth.currentUser?.apply {
                usersRef.document(uid).set(
                    mapOf(
                        "userID" to uid,
                        "name" to displayName,
                        "email" to email,
                        "lastActive" to serverTimestamp(),
                        "createdDate" to serverTimestamp()
                    ),
                    SetOptions.merge()
                ).await()
                emit(Response.Success(true))
            }
        } catch (e: Exception) {
            emit(Response.Error(e))
        }
    }

    override suspend fun updateLastActive() = flow {
        try {
            emit(Response.Loading)
            auth.currentUser?.apply {
                usersRef.document(uid).set(
                    mapOf(
                        "lastActive" to serverTimestamp()
                    ),
                    SetOptions.merge()
                ).await()
                emit(Response.Success(true))
            }
        } catch (e: Exception) {
            emit(Response.Error(e))
        }
    }

    override fun getFullName() = auth.currentUser?.displayName.toString()

    override fun getUserID() = auth.currentUser?.uid.toString()

    override suspend fun signOut() = flow {
        try {
            emit(Response.Loading)
            auth.signOut()
            oneTapClient.signOut().await()
            emit(Response.Success(true))
        } catch (e: Exception) {
            emit(Response.Error(e))
        }
    }
}
