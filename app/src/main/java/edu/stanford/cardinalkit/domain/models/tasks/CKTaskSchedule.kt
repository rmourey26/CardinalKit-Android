package io.quantumone.cardinalkit.domain.models.tasks

import io.quantumone.cardinalkit.common.toLocalDate
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.*

data class CKTaskSchedule(
    val startDate: Date = Date(),
    val endDate: Date? = null,
    val interval: Int = 1,
    val description: String? = null
) {
    fun isScheduledOn(date: LocalDate): Boolean {
        val startLocalDate = startDate.toLocalDate()

        var endLocalDate = date
        endDate?.let {
            endLocalDate = it.toLocalDate()
        }

        if (date < startLocalDate || date > endLocalDate || interval < 1) {
            return false
        }

        return (ChronoUnit.DAYS.between(startLocalDate, date)) % interval == 0L
    }
}
