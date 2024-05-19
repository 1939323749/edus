package ui.student

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import compose.icons.FeatherIcons
import compose.icons.feathericons.Check
import compose.icons.feathericons.Edit
import compose.icons.feathericons.X
import data.datasource.RemoteDataSource
import data.model.courses.StudentCourse
import data.model.student.ChooseCourse
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseManageScreen(
    studentId: Int,
) {
    var courses by remember { mutableStateOf(StudentCourse()) }
    var courseData by remember { mutableStateOf(ChooseCourse()) }
    var showEditCourse by remember { mutableStateOf(false) }
    val coroutine = rememberCoroutineScope()
    LaunchedEffect(Unit){
        courses = RemoteDataSource.getStudentCourse(studentId)
        courseData = RemoteDataSource.getCourseByStudentId(studentId)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "课程管理")
                },
                actions = {
                    IconButton(onClick = {
                        showEditCourse = !showEditCourse
                    }){
                        Icon(imageVector = FeatherIcons.Edit, contentDescription = null)
                    }
                }
            )
        }
    ) {
        Row (
            modifier = Modifier.padding(it)
        ){
            Column (
                modifier = Modifier.weight(1f)
            ){
                LazyColumn {
                    item {
                        Text("已选课程")
                    }
                    courses.data?.courses?.forEach {
                        item {
                            ListItem(
                                headlineContent = {
                                    if (it != null) {
                                        it.name?.let { it1 -> Text(text = it1) }
                                    }
                                },
                                trailingContent = {
                                    if (showEditCourse) {
                                        IconButton(onClick = {
                                            coroutine.launch {
                                                RemoteDataSource.deleteStudentCourse(studentId, it?.id!!)
                                                courses = RemoteDataSource.getStudentCourse(studentId)
                                            }
                                        }){
                                            Icon(imageVector = FeatherIcons.X, contentDescription = null)
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
            Column (
                modifier = Modifier.weight(1f)
            ){
                LazyColumn {
                    item {
                        Text("可选课程")
                    }
                    courseData.data?.forEach {
                        item {
                            ListItem(
                                headlineContent = {
                                    if (it != null) {
                                        it.name?.let { it1 -> Text(text = it1) }
                                    }
                                },
                                supportingContent = {
                                    if (it != null) {
                                        it.department?.name?.let { it1 -> Text(text = it1) }
                                        it.timeBlocks?.forEach {
                                            Column {
                                                if (it != null) {
                                                    Text(text = it.weekStart.toString() + "~" + it.weekEnd.toString() + "周" + it.start.toString() + "~" + it.end.toString() + "节")
                                                }
                                            }
                                        }
                                    }
                                },
                                trailingContent = {
                                    if (showEditCourse) {
                                        IconButton(onClick = {
                                            coroutine.launch {
                                                RemoteDataSource.chooseCourse(studentId, it!!.id!!)
                                                courses = RemoteDataSource.getStudentCourse(studentId)
                                            }
                                        }){
                                            Icon(imageVector = FeatherIcons.Check, contentDescription = null)
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}