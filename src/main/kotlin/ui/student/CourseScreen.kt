package ui.student

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.datasource.RemoteDataSource
import data.model.courses.StudentCourse

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseScreen(
    id: Int,
){
    var courseData by remember { mutableStateOf(StudentCourse()) }

    LaunchedEffect(Unit){
        courseData = RemoteDataSource.getStudentCourse(id)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "课表-第 ${1} 周")
            })
        }
    ){padding->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding)
        ) {
            item {
                courseData.data?.courses?.let {
                    CourseTable(week = 1, courseData = it)
                }
            }
        }
    }
}

@Composable
fun CourseTable(
    week: Int,
    courseData: List<StudentCourse.Data.Course?>
){
    LaunchedEffect(Unit){
        for (time in (1..12)) {
            for (dayOfWeek in (1..7)) {
                for (course in courseData) {
                    course?.timeBlocks?.filter {
                        it != null && it.weekStart!! <= week && it.weekEnd!! >= week && it.dayOfWeek == dayOfWeek
                    }?.forEach {
                        if (it!!.start!! <= time && it.end!! >= time) {
                            println("${it.start} ${it.location?.get(0)?.name}")
                        }
                    }
                }
            }
        }
    }
    Column {
        for (time in (1..12)) {
            Row(
            ) {
                Text(text = "$time")
                for (dayOfWeek in (1..7)) {
                    Card(
                        modifier = Modifier.padding(4.dp).weight(1f)
                    ) {
                        for (course in courseData) {
                            course?.timeBlocks?.filter {
                                it != null && it.weekStart!! <= week && it.weekEnd!! >= week && it.dayOfWeek == dayOfWeek
                            }?.forEach {
                                if (it!!.start!! <= time && it.end!! >= time) {
                                    Column {
                                        Text("${course.name}")
                                        Text("${it.location?.get(0)?.name}")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}