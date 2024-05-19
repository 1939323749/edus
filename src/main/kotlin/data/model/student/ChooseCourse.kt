package data.model.student


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChooseCourse(
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
        @SerialName("majors")
        val majors: List<Major?>? = null,
        @SerialName("name")
        val name: String? = null,
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
        data class Major(
            @SerialName("id")
            val id: Int? = null,
            @SerialName("name")
            val name: String? = null
        )

        @Serializable
        data class TimeBlock(
            @SerialName("end")
            val end: Int? = null,
            @SerialName("id")
            val id: Int? = null,
            @SerialName("start")
            val start: Int? = null,
            @SerialName("week_end")
            val weekEnd: Int? = null,
            @SerialName("week_start")
            val weekStart: Int? = null
        )
    }
}