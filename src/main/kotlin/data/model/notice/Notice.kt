package data.model.notice


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Notice(
    @SerialName("data")
    val `data`: List<Data?>? = null,
    @SerialName("msg")
    val msg: String? = null
) {
    @Serializable
    data class Data(
        @SerialName("content")
        val content: String? = null,
        @SerialName("createdAt")
        val createdAt: String? = null,
        @SerialName("id")
        val id: Int? = null,
        @SerialName("title")
        val title: String? = null
    )
}