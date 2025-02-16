package io.quantumone.cardinalkit.domain.usecases.tasklogs

import com.google.common.truth.Truth.assertThat
import io.quantumone.cardinalkit.domain.models.Response
import io.quantumone.cardinalkit.domain.models.tasks.CKTaskLog
import io.quantumone.cardinalkit.domain.repositories.TaskLogRepository
import io.quantumone.cardinalkit.domain.usecases.tasklogs.data.repository.FakeTaskLogRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.util.*

class GetTaskLogsTest {
    private lateinit var getTaskLogs: GetTaskLogs
    private lateinit var fakeRepository: TaskLogRepository

    @Before
    fun setUp() {
        fakeRepository = FakeTaskLogRepository()
        getTaskLogs = GetTaskLogs(fakeRepository)

        val taskLogsToInsert = mutableListOf<CKTaskLog>()

        val fakeTaskLog0 = CKTaskLog(
            taskID = "XE7VeC86W2qU9J8Q3C0A",
            date = Date()
        )

        val fakeTaskLog1 = CKTaskLog(
            taskID = "sIbsVjdnpkcaC5tjJwjQ",
            date = Date()
        )

        taskLogsToInsert.add(fakeTaskLog0)
        taskLogsToInsert.add(fakeTaskLog1)

        runBlocking {
            taskLogsToInsert.forEach {
                (fakeRepository as FakeTaskLogRepository).insertTaskLog(it)
            }
        }
    }

    @Test
    fun getTaskLogsTest() = runBlocking {
        val response = getTaskLogs().first()
        var taskLogs = listOf<CKTaskLog>()

        if (response is Response.Success && response.data != null) {
            taskLogs = response.data as List<CKTaskLog>
        }

        assertThat(taskLogs[0].taskID).isEqualTo("XE7VeC86W2qU9J8Q3C0A")
        assertThat(taskLogs[1].taskID).isEqualTo("sIbsVjdnpkcaC5tjJwjQ")
    }
}
