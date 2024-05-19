package data.model.student


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StudentDetail(
    @SerialName("data")
    val `data`: Data? = null,
    @SerialName("msg")
    val msg: String? = null
) {
    @Serializable
    data class Data(
        @SerialName("class")
        val classX: Class? = null,
        @SerialName("email")
        val email: String? = null,
        @SerialName("enrolled_time")
        val enrolledTime: String? = null,
        @SerialName("graduated_time")
        val graduatedTime: String? = null,
        @SerialName("id")
        val id: Int? = null,
        @SerialName("major")
        val major: Major? = null,
        @SerialName("name")
        val name: String? = null,
        @SerialName("sex")
        val sex: String? = null
    ) {
        @Serializable
        data class Class(
            @SerialName("name")
            val name: String? = null
        )

        @Serializable
        data class Major(
            @SerialName("departments")
            val departments: List<Department?>? = null,
            @SerialName("name")
            val name: String? = null
        ) {
            @Serializable
            data class Department(
                @SerialName("name")
                val name: String? = null
            )
        }
    }
}