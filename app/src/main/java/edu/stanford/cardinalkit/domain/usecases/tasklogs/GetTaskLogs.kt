package io.quantumone.cardinalkit.domain.usecases.tasklogs

import io.quantumone.cardinalkit.domain.repositories.TaskLogRepository

class GetTaskLogs(
    private val repository: TaskLogRepository
) {
    operator fun invoke() = repository.getTaskLogs()
}
