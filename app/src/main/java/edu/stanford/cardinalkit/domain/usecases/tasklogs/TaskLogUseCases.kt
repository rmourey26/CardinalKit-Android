package io.quantumone.cardinalkit.domain.usecases.tasklogs

data class TaskLogUseCases(
    val uploadTaskLog: UploadTaskLog,
    val getTaskLogs: GetTaskLogs
)
