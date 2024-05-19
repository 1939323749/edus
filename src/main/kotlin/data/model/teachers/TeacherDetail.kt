package data.model.teachers


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TeacherDetail(
    @SerialName("data")
    val `data`: Data? = null,
    @SerialName("msg")
    val msg: String? = null
) {
    @Serializable
    data class Data(
        @SerialName("email")
        val email: String? = null,
        @SerialName("id")
        val id: Int? = null,
        @SerialName("name")
        val name: String? = null,
        @SerialName("title")
        val title: String? = null
    )
}