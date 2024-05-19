package data.model.courses


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateCourse(
    @SerialName("data")
    val `data`: Data? = null,
    @SerialName("msg")
    val msg: String? = null
) {
    @Serializable
    data class Data(
        @SerialName("createdAt")
        val createdAt: String? = null,
        @SerialName("credit")
        val credit: Int? = null,
        @SerialName("departmentId")
        val departmentId: Int? = null,
        @SerialName("description")
        val description: String? = null,
        @SerialName("id")
        val id: Int? = null,
        @SerialName("name")
        val name: String? = null,
        @SerialName("updatedAt")
        val updatedAt: String? = null
    )
}