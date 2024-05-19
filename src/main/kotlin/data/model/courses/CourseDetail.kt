package data.model.courses


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CourseDetail(
    @SerialName("data")
    val `data`: Data? = null,
    @SerialName("msg")
    val msg: String? = null
) {
    @Serializable
    data class Data(
        @SerialName("credit")
        val credit: Int? = null,
        @SerialName("department")
        val department: Department? = null,
        @SerialName("description")
        val description: String? = null,
        @SerialName("id")
        val id: Int? = null,
        @SerialName("name")
        val name: String? = null,
        @SerialName("students")
        val students: List<Student?>? = null,
        @SerialName("teachers")
        val teachers: List<Teacher?>? = null,
        @SerialName("time_blocks")
        val timeBlocks: List<TimeBlock?>? = null
    ) {
        @Serializable
        data class Department(
            @SerialName("id")
            val id: Int? = null,
            @SerialName("name")
            val name: String? = null
        )

        @Serializable
        data class Student(
            @SerialName("id")
            val id: Int? = null,
            @SerialName("major")
            val major: Major? = null,
            @SerialName("name")
            val name: String? = null
        ) {
            @Serializable
            data class Major(
                @SerialName("id")
                val id: Int? = null,
                @SerialName("name")
                val name: String? = null
            )
        }

        @Serializable
        data class Teacher(
            @SerialName("id")
            val id: Int? = null,
            @SerialName("name")
            val name: String? = null
        )

        @Serializable
        data class TimeBlock(
            @SerialName("day_of_week")
            val dayOfWeek: Int? = null,
            @SerialName("end")
            val end: Int? = null,
            @SerialName("id")
            val id: Int? = null,
            @SerialName("location")
            val location: List<Location?>? = null,
            @SerialName("start")
            val start: Int? = null,
            @SerialName("week_end")
            val weekEnd: Int? = null,
            @SerialName("week_start")
            val weekStart: Int? = null
        ) {
            @Serializable
            data class Location(
                @SerialName("address")
                val address: String? = null,
                @SerialName("name")
                val name: String? = null
            )
        }
    }
}