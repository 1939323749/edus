package data.model.getallclassresp


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("_count")
    val count: Count? = null,
    @SerialName("courses")
    val courses: List<Course?>? = null,
    @SerialName("id")
    val id: Int? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("students")
    val students: List<Student?>? = null
)