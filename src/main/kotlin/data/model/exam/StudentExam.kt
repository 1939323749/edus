package data.model.exam


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StudentExam(
    @SerialName("data")
    val `data`: List<Data?>? = null,
    @SerialName("msg")
    val msg: String? = null
) {
    @Serializable
    data class Data(
        @SerialName("course")
        val course: List<Course?>? = null,
        @SerialName("description")
        val description: String? = null,
        @SerialName("end")
        val end: String? = null,
        @SerialName("id")
        val id: Int? = null,
        @SerialName("location")
        val location: List<Location?>? = null,
        @SerialName("name")
        val name: String? = null,
        @SerialName("start")
        val start: String? = null
    ) {
        @Serializable
        data class Course(
            @SerialName("id")
            val id: Int? = null,
            @SerialName("name")
            val name: String? = null
        )

        @Serializable
        data class Location(
            @SerialName("id")
            val id: Int? = null,
            @SerialName("name")
            val name: String? = null
        )
    }
}