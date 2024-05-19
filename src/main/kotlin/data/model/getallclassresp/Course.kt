package data.model.getallclassresp


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Course(
    @SerialName("name")
    val name: String? = null
)