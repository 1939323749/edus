package ui.teacher

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import data.datasource.RemoteDataSource
import data.model.courses.TeacherCourse

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherCourseScrren(
    id: Int,
){
    var courses by remember { mutableStateOf(TeacherCourse()) }
    LaunchedEffect(Unit){
        courses = RemoteDataSource.getTeacherCourse(id)
    }

    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "课程")
                }
            )
        }
    ){
        LazyColumn (
            modifier = Modifier.padding(it)
        ){
            courses.data?.forEach {
                item {
                    ListItem(
                        headlineContent = {
                            Text(text = it?.name!!)
                        },
                        supportingContent = {
                            Text(text = it?.department?.name!!)
                        }
                    )
                }
            }
        }
    }
}