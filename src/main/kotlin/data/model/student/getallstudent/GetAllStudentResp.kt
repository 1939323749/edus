package data.model.student.getallstudent


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetAllStudentResp(
    @SerialName("data")
    val `data`: List<Data?>? = null,
    @SerialName("msg")
    val msg: String? = null
)