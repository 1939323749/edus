package data.model.exam


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExamStudent(
    @SerialName("data")
    val `data`: Data? = null,
    @SerialName("msg")
    val msg: String? = null
) {
    @Serializable
    data class Data(
        @SerialName("course")
        val course: List<Course?>? = null
    ) {
        @Serializable
        data class Course(
            @SerialName("name")
            val name: String? = null,
            @SerialName("students")
            val students: List<Student?>? = null
        ) {
            @Serializable
            data class Student(
                @SerialName("id")
                val id: Int? = null,
                @SerialName("name")
                val name: String? = null
            )
        }
    }
}