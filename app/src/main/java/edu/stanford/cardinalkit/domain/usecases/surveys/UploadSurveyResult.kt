package io.quantumone.cardinalkit.domain.usecases.surveys

import io.quantumone.cardinalkit.domain.repositories.SurveyRepository

class UploadSurveyResult(
    private val repository: SurveyRepository
) {
    suspend operator fun invoke(
        result: Map<String, Any>
    ) = repository.uploadSurveyResult(result)
}
