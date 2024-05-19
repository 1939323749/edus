package data.model.student


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StudentScore(
    @SerialName("data")
    val `data`: List<Data?>? = null,
    @SerialName("msg")
    val msg: String? = null
) {
    @Serializable
    data class Data(
        @SerialName("exam")
        val exam: Exam? = null,
        @SerialName("grade")
        val grade: Int? = null,
        @SerialName("id")
        val id: Int? = null,
        @SerialName("score")
        val score: Int? = null
    ) {
        @Serializable
        data class Exam(
            @SerialName("name")
            val name: String? = null
        )
    }
}