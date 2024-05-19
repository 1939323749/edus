package data.model.location


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AllLocations(
    @SerialName("data")
    val `data`: List<Data?>? = null,
    @SerialName("msg")
    val msg: String? = null
) {
    @Serializable
    data class Data(
        @SerialName("address")
        val address: String? = null,
        @SerialName("id")
        val id: Int? = null,
        @SerialName("name")
        val name: String? = null
    )
}