package io.quantumone.cardinalkit.domain.usecases.tasks

import io.quantumone.cardinalkit.domain.repositories.TasksRepository

class GetTasks(
    private val repository: TasksRepository
) {
    operator fun invoke() = repository.getTasks()
}
