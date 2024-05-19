package data.model.teachers


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AllTeachers(
    @SerialName("data")
    val `data`: List<Data?>? = null,
    @SerialName("msg")
    val msg: String? = null
) {
    @Serializable
    data class Data(
        @SerialName("department")
        val department: Department? = null,
        @SerialName("email")
        val email: String? = null,
        @SerialName("id")
        val id: Int? = null,
        @SerialName("name")
        val name: String? = null,
        @SerialName("title")
        val title: String? = null
    ) {
        @Serializable
        data class Department(
            @SerialName("name")
            val name: String? = null
        )
    }
}