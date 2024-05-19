package data.model.student.getallstudent


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Class(
    @SerialName("name")
    val name: String? = null
)