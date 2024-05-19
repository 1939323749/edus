package data.model.student.getallstudent


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("class")
    val classX: Class? = null,
    @SerialName("id")
    val id: Int? = null,
    @SerialName("name")
    val name: String? = null
)