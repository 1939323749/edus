package data.model.student


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Classroom(
    @SerialName("data")
    val `data`: List<Data?>? = null,
    @SerialName("msg")
    val msg: String? = null
) {
    @Serializable
    data class Data(
        @SerialName("address")
        val address: String? = null,
        @SerialName("name")
        val name: String? = null
    )
}