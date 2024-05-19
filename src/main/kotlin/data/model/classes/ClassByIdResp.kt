package data.model.classes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ClassByIdResp(
    @SerialName("data")
    val `data`: Data? = null,
    @SerialName("msg")
    val msg: String? = null
) {
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
    ) {
        @Serializable
        data class Count(
            @SerialName("students")
            val students: Int? = null
        )

        @Serializable
        data class Course(
            @SerialName("id")
            val id: Int? = null,
            @SerialName("name")
            val name: String? = null
        )

        @Serializable
        data class Student(
            @SerialName("id")
            val id: Int? = null,
            @SerialName("name")
            val name: String? = null
        )
    }
}