package io.quantumone.cardinalkit.presentation.tasks.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import io.quantumone.cardinalkit.domain.models.tasks.CKTask
import io.quantumone.cardinalkit.domain.models.tasks.CKTaskLog
import io.quantumone.cardinalkit.presentation.health.HealthViewModel
import io.quantumone.cardinalkit.presentation.tasks.TasksViewModel

@Composable
fun StepGoalProgress(
    task: CKTask,
    completed: Boolean,
    healthViewModel: HealthViewModel = hiltViewModel(),
    tasksViewModel: TasksViewModel = hiltViewModel()
) {
    // Fetch total step count and calculate metrics
    healthViewModel.getTotalStepsToday()
    val goal = task.context.integerGoal
    val totalStepsToday = healthViewModel.totalStepsToday.value
    var progress = healthViewModel.totalStepsToday.value.toFloat() / goal
    if (progress > 1.0F) {
        progress = 1.0F
    }

    // Autocomplete the task if goal is met
    // and the task isn't already marked as complete
    if (!completed && totalStepsToday >= goal) {
        tasksViewModel.uploadTaskLog(CKTaskLog(task.id))
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Text(
            text = "$totalStepsToday",
            fontSize = 30.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "/ $goal",
            fontSize = 30.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
    LinearProgressIndicator(
        progress = progress,
        modifier = Modifier.fillMaxWidth().padding(5.dp),
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}
