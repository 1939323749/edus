package data.model.getallclassresp


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Student(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("name")
    val name: String? = null
)