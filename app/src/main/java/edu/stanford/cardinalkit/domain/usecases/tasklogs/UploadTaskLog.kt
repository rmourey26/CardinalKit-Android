package io.quantumone.cardinalkit.domain.usecases.tasklogs

import io.quantumone.cardinalkit.domain.models.tasks.CKTaskLog
import io.quantumone.cardinalkit.domain.repositories.TaskLogRepository

class UploadTaskLog(
    private val repository: TaskLogRepository
) {
    suspend operator fun invoke(log: CKTaskLog) = repository.uploadTaskLog(log)
}
