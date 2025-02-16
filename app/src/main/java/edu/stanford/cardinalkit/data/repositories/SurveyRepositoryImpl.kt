package io.quantumone.cardinalkit.data.repositories

import android.content.Context
import com.google.firebase.firestore.CollectionReference
import io.quantumone.cardinalkit.common.Constants
import io.quantumone.cardinalkit.domain.models.Response
import io.quantumone.cardinalkit.domain.repositories.SurveyRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class SurveyRepositoryImpl @Inject constructor(
    @Named(Constants.SURVEYS_REF) private val surveysRef: CollectionReference?,
    private val context: Context
) : SurveyRepository {

    override suspend fun uploadSurveyResult(result: Map<String, Any>) = flow {
        surveysRef?.let {
            try {
                emit(Response.Loading)
                val id = result["id"] as String
                val upload = surveysRef.document(id).set(result).await()
                emit(Response.Success(upload))
            } catch (e: Exception) {
                emit(Response.Error(e))
            }
        }
    }

    override suspend fun getSurvey(name: String) = flow {
        try {
            emit(Response.Loading)
            val questionnaireJsonString =
                context.assets.open(name)
                    .bufferedReader().use { it.readText() }
            emit(Response.Success(questionnaireJsonString))
        } catch (e: IOException) {
            emit(Response.Error(e))
        }
    }
}
