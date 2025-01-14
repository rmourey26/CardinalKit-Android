package io.quantumone.cardinalkit.di

import android.app.Application
import android.content.Context
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.quantumone.cardinalkit.R
import io.quantumone.cardinalkit.common.Constants
import io.quantumone.cardinalkit.data.repositories.AuthRepositoryImpl
import io.quantumone.cardinalkit.data.repositories.ContactsRepositoryImpl
import io.quantumone.cardinalkit.data.repositories.SurveyRepositoryImpl
import io.quantumone.cardinalkit.data.repositories.TaskLogRepositoryImpl
import io.quantumone.cardinalkit.data.repositories.TasksRepositoryImpl
import io.quantumone.cardinalkit.domain.repositories.AuthRepository
import io.quantumone.cardinalkit.domain.repositories.ContactsRepository
import io.quantumone.cardinalkit.domain.repositories.SurveyRepository
import io.quantumone.cardinalkit.domain.repositories.TaskLogRepository
import io.quantumone.cardinalkit.domain.repositories.TasksRepository
import io.quantumone.cardinalkit.domain.usecases.auth.AuthUseCases
import io.quantumone.cardinalkit.domain.usecases.auth.GetAuthStatus
import io.quantumone.cardinalkit.domain.usecases.auth.OneTapSignIn
import io.quantumone.cardinalkit.domain.usecases.auth.ResetPassword
import io.quantumone.cardinalkit.domain.usecases.auth.SaveUser
import io.quantumone.cardinalkit.domain.usecases.auth.SignInWithEmail
import io.quantumone.cardinalkit.domain.usecases.auth.SignInWithGoogle
import io.quantumone.cardinalkit.domain.usecases.auth.SignUpWithEmail
import io.quantumone.cardinalkit.domain.usecases.auth.UpdateLastActive
import io.quantumone.cardinalkit.domain.usecases.contacts.ContactsUseCases
import io.quantumone.cardinalkit.domain.usecases.contacts.GetContacts
import io.quantumone.cardinalkit.domain.usecases.surveys.GetSurvey
import io.quantumone.cardinalkit.domain.usecases.surveys.SurveysUseCases
import io.quantumone.cardinalkit.domain.usecases.surveys.UploadSurveyResult
import io.quantumone.cardinalkit.domain.usecases.tasklogs.GetTaskLogs
import io.quantumone.cardinalkit.domain.usecases.tasklogs.TaskLogUseCases
import io.quantumone.cardinalkit.domain.usecases.tasklogs.UploadTaskLog
import io.quantumone.cardinalkit.domain.usecases.tasks.GetTasks
import io.quantumone.cardinalkit.domain.usecases.tasks.TasksUseCases
import io.quantumone.cardinalkit.services.HealthConnectManager
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideContext(app: Application): Context = app.applicationContext

    @Provides
    fun provideFirebaseAuth() = Firebase.auth

    @Provides
    fun provideFirebaseFirestore() = Firebase.firestore

    @Provides
    @Named(Constants.USERS_REF)
    fun provideUsersRef(
        db: FirebaseFirestore
    ): CollectionReference {
        return db.collection(
            "${Constants.FIRESTORE_BASE_DOCUMENT}/${Constants.FIRESTORE_USERS_COLLECTION}"
        )
    }

    @Provides
    @Named(Constants.TASKS_REF)
    fun provideTasksRef(
        db: FirebaseFirestore
    ): CollectionReference? {
        return db.collection(
            "${Constants.FIRESTORE_BASE_DOCUMENT}/${Constants.FIRESTORE_TASKS_COLLECTION}"
        )
    }

    @Provides
    @Named(Constants.TASKLOG_REF)
    fun provideTaskLogRef(
        db: FirebaseFirestore
    ): CollectionReference? {
        val user = Firebase.auth.currentUser
        user?.let {
            return db.collection(
                "${Constants.FIRESTORE_BASE_DOCUMENT}/${Constants.FIRESTORE_USERS_COLLECTION}/${user.uid}/${Constants.FIRESTORE_TASKLOG_COLLECTION}"
            )
        }
        return null
    }

    @Provides
    @Named(Constants.SURVEYS_REF)
    fun provideSurveysRef(
        db: FirebaseFirestore
    ): CollectionReference? {
        val user = Firebase.auth.currentUser
        user?.let {
            return db.collection(
                "${Constants.FIRESTORE_BASE_DOCUMENT}/${Constants.FIRESTORE_USERS_COLLECTION}/${user.uid}/${Constants.FIRESTORE_SURVEYS_COLLECTION}"
            )
        }
        return null
    }

    @Provides
    @Named(Constants.CONTACTS_REF)
    fun provideContactsRef(
        db: FirebaseFirestore
    ): CollectionReference? {
        val user = Firebase.auth.currentUser
        user?.let {
            return db.collection(
                "${Constants.FIRESTORE_BASE_DOCUMENT}/${Constants.FIRESTORE_CONTACTS_COLLECTION}"
            )
        }
        return null
    }

    @Provides
    fun provideOneTapClient(context: Context) = Identity.getSignInClient(context)

    @Provides
    @Named(Constants.SIGN_IN_REQUEST)
    fun provideSignInRequest(
        app: Application
    ) = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(app.getString(R.string.web_client_id))
                .setFilterByAuthorizedAccounts(true)
                .build()
        )
        .setAutoSelectEnabled(true)
        .build()

    @Provides
    @Named(Constants.SIGN_UP_REQUEST)
    fun provideSignUpRequest(
        app: Application
    ) = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(app.getString(R.string.web_client_id))
                .setFilterByAuthorizedAccounts(false)
                .build()
        )
        .build()

    @Provides
    fun provideGoogleSignInOptions(
        app: Application
    ) = GoogleSignInOptions
        .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(app.getString(R.string.web_client_id))
        .requestEmail()
        .build()

    @Provides
    fun provideGoogleSignInClient(
        app: Application,
        options: GoogleSignInOptions
    ) = GoogleSignIn.getClient(app, options)

    @Provides
    fun provideAuthRepository(
        auth: FirebaseAuth,
        oneTapClient: SignInClient,
        @Named(Constants.SIGN_IN_REQUEST)
        signInRequest: BeginSignInRequest,
        signInClient: GoogleSignInClient,
        @Named(Constants.USERS_REF)
        usersRef: CollectionReference
    ): AuthRepository = AuthRepositoryImpl(
        auth = auth,
        oneTapClient = oneTapClient,
        signInRequest = signInRequest,
        signInClient = signInClient,
        usersRef = usersRef
    )

    @Provides
    @Named(Constants.SURVEY_REPOSITORY)
    fun provideSurveyRepository(
        @Named(Constants.SURVEYS_REF)
        surveysRef: CollectionReference?,
        context: Context
    ): SurveyRepository = SurveyRepositoryImpl(surveysRef, context)

    @Provides
    @Named(Constants.TASKS_REPOSITORY)
    fun provideTasksRepository(
        @Named(Constants.TASKS_REF)
        tasksRef: CollectionReference?,
        context: Context
    ): TasksRepository = TasksRepositoryImpl(tasksRef, context)

    @Provides
    @Named(Constants.TASKLOG_REPOSITORY)
    fun provideTaskLogRepository(
        @Named(Constants.TASKLOG_REF)
        taskLogRef: CollectionReference?
    ): TaskLogRepository = TaskLogRepositoryImpl(taskLogRef)

    @Provides
    @Named(Constants.CONTACTS_REPOSITORY)
    fun provideContactsRepository(
        @Named(Constants.CONTACTS_REF)
        contactsRef: CollectionReference?,
        context: Context
    ): ContactsRepository = ContactsRepositoryImpl(contactsRef, context)

    @Provides
    @Named(Constants.SURVEYS_USE_CASES)
    fun provideSurveysUseCases(
        @Named(Constants.SURVEY_REPOSITORY)
        surveyRepository: SurveyRepository
    ) = SurveysUseCases(
        uploadSurveyResult = UploadSurveyResult(surveyRepository),
        getSurvey = GetSurvey(surveyRepository)
    )

    @Provides
    @Named(Constants.CONTACTS_USE_CASES)
    fun provideContactsUseCases(
        @Named(Constants.CONTACTS_REPOSITORY)
        contactsRepository: ContactsRepository
    ) = ContactsUseCases(
        getContacts = GetContacts(contactsRepository)
    )

    @Provides
    @Named(Constants.TASKS_USE_CASES)
    fun provideTasksUseCases(
        @Named(Constants.TASKS_REPOSITORY)
        repository: TasksRepository
    ) = TasksUseCases(
        getTasks = GetTasks(repository)
    )

    @Provides
    @Named(Constants.TASKLOG_USE_CASES)
    fun provideTaskLogUseCases(
        @Named(Constants.TASKLOG_REPOSITORY)
        repository: TaskLogRepository
    ) = TaskLogUseCases(
        uploadTaskLog = UploadTaskLog(repository),
        getTaskLogs = GetTaskLogs(repository)
    )

    @Provides
    @Named(Constants.AUTH_USE_CASES)
    fun provideAuthUseCases(
        repository: AuthRepository
    ) = AuthUseCases(
        signInWithEmail = SignInWithEmail(repository),
        signUpWithEmail = SignUpWithEmail(repository),
        oneTapSignIn = OneTapSignIn(repository),
        signInWithGoogle = SignInWithGoogle(repository),
        saveUser = SaveUser(repository),
        updateLastActive = UpdateLastActive(repository),
        getAuthStatus = GetAuthStatus(repository),
        resetPassword = ResetPassword(repository)
    )

    @Provides
    fun provideHealthConnectManager(
        context: Context
    ) = HealthConnectManager(context)
}
