package data.model.courses


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AllCourses(
    @SerialName("data")
    val `data`: List<Data?>? = null,
    @SerialName("msg")
    val msg: String? = null
) {
    @Serializable
    data class Data(
        @SerialName("department")
        val department: Department? = null,
        @SerialName("description")
        val description: String? = null,
        @SerialName("id")
        val id: Int? = null,
        @SerialName("name")
        val name: String? = null
    ) {
        @Serializable
        data class Department(
            @SerialName("id")
            val id: Int? = null,
            @SerialName("name")
            val name: String? = null
        )
    }
}