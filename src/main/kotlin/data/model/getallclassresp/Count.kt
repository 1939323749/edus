package data.model.getallclassresp


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Count(
    @SerialName("students")
    val students: Int? = null
)