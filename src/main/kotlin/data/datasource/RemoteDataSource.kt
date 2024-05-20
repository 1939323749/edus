package data.datasource

import com.google.gson.Gson
import data.model.classes.ClassByIdResp
import data.model.courses.*
import data.model.exam.AllExams
import data.model.exam.ExamStudent
import data.model.exam.StudentExam
import data.model.exam.TeacherExam
import data.model.getallclassresp.GetAllClassesResp
import data.model.location.AllLocations
import data.model.notice.Notice
import data.model.overview.Overview
import data.model.student.*
import data.model.student.getallstudent.GetAllStudentResp
import data.model.tb.TimeBlock
import data.model.teachers.AllTeachers
import data.model.teachers.TeacherDetail
import data.model.teachers.UpdateTeacher
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

object RemoteDataSource {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    private val gson by lazy {
        Gson()
    }

    private const val BASEURL = "http://localhost:3000/api"

    suspend fun getAllClasses(): GetAllClassesResp {
        return try {
            client.get("$BASEURL/class").body()
        } catch (e: Exception){
            GetAllClassesResp()
        }
    }

    suspend fun getClass(id: Int): ClassByIdResp {
        return try {
            client.get("$BASEURL/class/$id").body()
        }catch (e: Exception){
            ClassByIdResp()
        }
    }

    suspend fun getAllStudents(): GetAllStudentResp {
        return try {
            client.get("$BASEURL/student").body()
        } catch (e: Exception){
            GetAllStudentResp()
        }
    }

    suspend fun getStudentDetails(id: Int): StudentDetail {
        return try{
            client.get("$BASEURL/student/$id").body()
        }catch (e: Exception){
            StudentDetail()
        }
    }

    suspend fun getOverview(): Overview {
        return try{
            client.get("$BASEURL/overview").body()
        }catch (e: Exception){
            Overview()
        }
    }

    suspend fun getAllCourses(): AllCourses {
        return try {
            client.get("$BASEURL/course").body()
        } catch (e: Exception){
            AllCourses()
        }
    }

    suspend fun getCourseDetail(id: Int): CourseDetail {
        return try {
            client.get("$BASEURL/course/$id").body()
        } catch (e: Exception){
            CourseDetail()
        }
    }

    suspend fun getAllTeachers(): AllTeachers {
        return try {
            client.get("$BASEURL/teacher").body()
        } catch (e: Exception){
            AllTeachers()
        }
    }

    suspend fun updateStudent(id: Int, studentData: StudentDetail): UpdateStudent {
        return try {
            client.put("$BASEURL/student/$id") {
                setBody(
                    mapOf(
                        "name" to (studentData.data?.name ?: "unknown"),
                        "sex" to (studentData.data?.sex ?: "unknown"),
                        "email" to (studentData.data?.email ?: "unknown"),
                    )
                )
                contentType(ContentType.Application.Json)
            }.body()
        } catch (e: Exception) {
            UpdateStudent()
        }
    }

    suspend fun updateCourse(courseId: Int,map: Map<String,Any?>): UpdateCourse {
        return try {
            client.put("$BASEURL/course/$courseId") {
                setBody(
                    gson.toJson(
                        map
                    )
                )
                contentType(ContentType.Application.Json)
            }.also { println(it.bodyAsText()) }.body()
        } catch (e: Exception){
            UpdateCourse()
        }
    }

    suspend fun getTeacherDetail(id: Int): TeacherDetail {
        return try {
            client.get("$BASEURL/teacher/$id").body()
        } catch (e: Exception) {
            TeacherDetail()
        }
    }

    suspend fun updateTeacher(id: Int, data: TeacherDetail): UpdateTeacher {
        return try {
            client.put("$BASEURL/teacher/$id") {
                setBody(
                    gson.toJson(
                        mapOf(
                            "name" to (data.data?.name ?: "unknown"),
                            "email" to (data.data?.email ?: "unknown"),
                            "title" to (data.data?.title ?: "unknown")
                        )
                    )
                )
                contentType(ContentType.Application.Json)
            }.body()
        } catch (e: Exception) {
            UpdateTeacher()
        }
    }

    suspend fun getStudentExams(id: Int): StudentExam {
        return client.get("$BASEURL/student/$id/exam").body()
    }

    suspend fun getAllLocations(): AllLocations {
        return try {
            client.get("$BASEURL/loca").body()
        } catch (e: Exception) {
            println(e)
            AllLocations()
        }
    }

    suspend fun updateCourseLocation(courseId: Int, oldLocation: Int, newLocation: Int) {
        client.put("$BASEURL/course/loca") {
            setBody(
                gson.toJson(
                    mapOf(
                        "course_id" to courseId,
                        "loca_id" to oldLocation,
                        "new_loca_id" to newLocation
                    )
                )
            )
            contentType(ContentType.Application.Json)
        }
    }

    suspend fun getClassByStudentId(studentId: Int): ClassByIdResp {
        return try {
            client.get("$BASEURL/class/$studentId").body()
        } catch (e: Exception) {
            ClassByIdResp()
        }
    }

    suspend fun getStudentCourse(studentId: Int): StudentCourse {
        return try {
            client.get("$BASEURL/student/$studentId/course").body()
        } catch (e: Exception){
            StudentCourse()
        }
    }

    suspend fun getStudentScore(id: Int): StudentScore {
        return try {
            client.get("$BASEURL/student/$id/score").body()
        } catch (e: Exception){
            StudentScore()
        }
    }

    suspend fun getAvailableClassRoom(
        start: Int,
        end: Int,
        dayOfWeek: Int,
        week: Int,
    ): Classroom {
        return try {
            client.post("$BASEURL/classroom"){
                setBody(gson.toJson(mapOf(
                    "start" to start,
                    "end" to end,
                    "day_of_week" to dayOfWeek,
                    "week" to week
                )))
                contentType(ContentType.Application.Json)
            }.body()
        } catch (e: Exception){
            Classroom()
        }
    }

    suspend fun getAllNotices(): Notice {
        return try {
            client.get("$BASEURL/notice").body()
        } catch (e: Exception){
            Notice()
        }
    }

    suspend fun getCourseByStudentId(studentId: Int): ChooseCourse {
        return try {
            client.get("$BASEURL/course/student/$studentId").body()
        } catch (e: Exception){
            ChooseCourse()
        }
    }

    suspend fun chooseCourse(studentId: Int, id: Int) {
        try {
            client.post("$BASEURL/student/$studentId/course"){
                setBody(gson.toJson(
                    mapOf(
                        "course_id" to id
                    )
                ))
                contentType(ContentType.Application.Json)
            }
        } catch (e: Exception){
            println(e)
        }
    }

    suspend fun deleteStudentCourse(studentId: Int,id: Int) {
        try {
            client.delete("$BASEURL/student/$studentId/course"){
                setBody(gson.toJson(
                    mapOf(
                        "course_id" to id
                    )
                ))
                contentType(ContentType.Application.Json)
            }
        } catch (e: Exception){
            println(e)
        }
    }

    suspend fun importStudent(row: Map<String, String>) {
        try {
            client.post("$BASEURL/student"){
                setBody(gson.toJson(row))
                contentType(ContentType.Application.Json)
            }
        } catch (e: Exception) {
            println(e)
        }
    }

    suspend fun importTeacher(row: Map<String, String>) {
        try {
            client.post("$BASEURL/teacher"){
                setBody(gson.toJson(row))
                contentType(ContentType.Application.Json)
            }
        } catch (e: Exception) {
            println(e)
        }
    }

    suspend fun getTimeBlocks(): TimeBlock {
        return try {
            client.get("$BASEURL/tb").body()
        } catch (e: Exception){
            TimeBlock()
        }
    }

    suspend fun addTimeBlock(weekStart: Int, weekEnd: Int, dayOfWeek: Int, start: Int, end: Int) {
        client.post("$BASEURL/tb"){
            setBody(gson.toJson(
                mapOf(
                    "week_start" to weekStart,
                    "week_end" to weekEnd,
                    "day_of_week" to dayOfWeek,
                    "start" to start,
                    "end" to end
                )
            ))
            contentType(ContentType.Application.Json)
        }
    }

    suspend fun importCourse(row: Map<String, String>) {
        try {
            client.post("$BASEURL/course"){
                setBody(gson.toJson(row))
                contentType(ContentType.Application.Json)
            }.bodyAsText().let {
                println(it)
            }
        } catch (e: Exception) {
            println(e)
        }
    }

    suspend fun importClasses(row: Map<String, String>) {
        try {
            client.post("$BASEURL/class"){
                setBody(gson.toJson(row))
                contentType(ContentType.Application.Json)
            }
        } catch (e: Exception){
            println(e)
        }
    }

    suspend fun importLocations(row: Map<String, String>) {
        try {
            client.post("$BASEURL/loca"){
                setBody(gson.toJson(row))
                contentType(ContentType.Application.Json)
            }
        } catch (e: Exception){
            println(e)
        }
    }

    suspend fun deleteLocation(i: Int) {
        if (i!=0){
            try {
                client.delete("$BASEURL/loca"){
                    setBody(gson.toJson(mapOf(
                        "id" to i
                    )))
                }.bodyAsText().let {
                    println(it)
                }
            } catch (e: Exception){
                println(e)
            }
        }
    }

    suspend fun getAllExams(): AllExams {
        return try {
            client.get("$BASEURL/exam").body<AllExams>().also { println(it) }
        } catch (e: Exception){
            println(e)
            AllExams()
        }
    }

    suspend fun getTeacherCourse(id: Int): TeacherCourse {
        return try {
            client.get("$BASEURL/teacher/$id/course").body()
        } catch (e: Exception){
            TeacherCourse()
        }
    }

    suspend fun getTeacherExam(id: Int): TeacherExam {
        return  try {
            client.get("$BASEURL/teacher/$id/exam").body()
        } catch (e: Exception){
            println(e)
            TeacherExam()
        }
    }

    suspend fun getExamStudent(id: Int): ExamStudent {
        return try {
            println(id)
            client.get("$BASEURL/exam/$id").body()
        } catch (e: Exception){
            println(e)
            ExamStudent()
        }
    }

    suspend fun importScore(examId: Int,row: Map<String, String>) {
        try {
            client.post("$BASEURL/score/$examId"){
                setBody(gson.toJson(
                    mapOf(
                        "student_id" to row["student_id"]!!,
                        "score" to row["score"]!!
                    )
                ))
                contentType(ContentType.Application.Json)
            }.bodyAsText().let {
                println(it)
            }
        } catch (e: Exception){
            println(e)
        }
    }
}