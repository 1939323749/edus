package data.model.overview


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Overview(
    @SerialName("data")
    val `data`: Data? = null,
    @SerialName("msg")
    val msg: String? = null
) {
    @Serializable
    data class Data(
        @SerialName("classes_count")
        val classesCount: Int? = null,
        @SerialName("courses_count")
        val coursesCount: Int? = null,
        @SerialName("locations_count")
        val locationsCount: Int? = null,
        @SerialName("students_count")
        val studentsCount: Int? = null,
        @SerialName("teachers_count")
        val teachersCount: Int? = null,
        @SerialName("time_blocks_count")
        val timeBlocksCount: Int? = null
    )
}