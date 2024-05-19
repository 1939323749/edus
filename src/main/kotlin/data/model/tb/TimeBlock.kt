package data.model.tb


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TimeBlock(
    @SerialName("data")
    val `data`: List<Data?>? = null,
    @SerialName("msg")
    val msg: String? = null
) {
    @Serializable
    data class Data(
        @SerialName("day_of_week")
        val dayOfWeek: Int? = null,
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