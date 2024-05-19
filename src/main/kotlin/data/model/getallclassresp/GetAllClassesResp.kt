package data.model.getallclassresp


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetAllClassesResp(
    @SerialName("data")
    val `data`: List<Data?>? = null,
    @SerialName("msg")
    val msg: String? = null
)