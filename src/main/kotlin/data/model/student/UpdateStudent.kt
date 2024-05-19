package data.model.student


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateStudent(
    @SerialName("data")
    val `data`: Data? = null,
    @SerialName("msg")
    val msg: String? = null,
    @SerialName("updatedAt")
    val updatedAt: String? = null
) {
    @Serializable
    data class Data(
        @SerialName("class")
        val classX: Class? = null,
        @SerialName("email")
        val email: String? = null,
        @SerialName("id")
        val id: Int? = null,
        @SerialName("name")
        val name: String? = null,
        @SerialName("sex")
        val sex: String? = null
    ) {
        @Serializable
        data class Class(
            @SerialName("id")
            val id: Int? = null,
            @SerialName("name")
            val name: String? = null
        )
    }
}