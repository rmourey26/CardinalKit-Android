package io.quantumone.cardinalkit.domain.usecases.surveys

import io.quantumone.cardinalkit.domain.repositories.SurveyRepository

class GetSurvey(
    private val repository: SurveyRepository
) {
    suspend operator fun invoke(
        name: String
    ) = repository.getSurvey(name)
}
