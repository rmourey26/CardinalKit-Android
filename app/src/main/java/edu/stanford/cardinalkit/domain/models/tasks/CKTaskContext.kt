package io.quantumone.cardinalkit.domain.models.tasks

data class CKTaskContext(
    val category: CKTaskCategory = CKTaskCategory.MISC,
    val uri: String = "",
    val integerGoal: Int = 0
)
