package data.model.exam


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TeacherExam(
    @SerialName("data")
    val `data`: List<Data?>? = null,
    @SerialName("msg")
    val msg: String? = null
) {
    @Serializable
    data class Data(
        @SerialName("createdAt")
        val createdAt: String? = null,
        @SerialName("description")
        val description: String? = null,
        @SerialName("end")
        val end: String? = null,
        @SerialName("id")
        val id: Int? = null,
        @SerialName("name")
        val name: String? = null,
        @SerialName("start")
        val start: String? = null,
        @SerialName("updatedAt")
        val updatedAt: String? = null
    )
}