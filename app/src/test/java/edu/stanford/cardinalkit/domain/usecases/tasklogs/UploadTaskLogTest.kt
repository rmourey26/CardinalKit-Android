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

class UploadTaskLogTest {
    private lateinit var uploadTaskLog: UploadTaskLog
    private lateinit var fakeRepository: TaskLogRepository

    @Before
    fun setUp() {
        fakeRepository = FakeTaskLogRepository()
        uploadTaskLog = UploadTaskLog(fakeRepository)
    }

    @Test
    fun uploadTaskLogTest() = runBlocking {
        val log = CKTaskLog(
            taskID = "XE7VeC86W2qU9J8Q3C0A",
            date = Date()
        )

        val response = uploadTaskLog(log).first()
        assertThat(response).isInstanceOf(Response.Success::class.java)
    }
}
