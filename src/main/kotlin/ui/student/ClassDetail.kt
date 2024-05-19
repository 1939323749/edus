package ui.student

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import data.datasource.RemoteDataSource
import data.model.classes.ClassByIdResp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentClassDetail(
    id: Int,
){
    var classData by remember { mutableStateOf(ClassByIdResp()) }
    LaunchedEffect(Unit){
        classData = RemoteDataSource.getClassByStudentId(id)
    }

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = "Class Detail") },
            )
        }
    ){padding->
        Column(
            modifier = Modifier.padding(padding)
        ) {
            Text(text = "class", style = MaterialTheme.typography.titleLarge)
            classData.data?.let { data ->
                Column {
                    Text(text = "name:" + data.name!!)
                    Text(text = "id:" + data.id!!)
                    Text(text = "courses", style = MaterialTheme.typography.titleLarge)
                    data.courses?.forEach {
                        if (it != null) {
                            Text(text = "id:" + it.id!!)
                            Text(text = "name:" + it.name!!)
                        }
                    }
                    Text(text = "students", style = MaterialTheme.typography.titleLarge)
                    data.students?.forEach {
                        if (it != null) {
                            Text(text = "id:" + it.id!!)
                            Text(text = "name:" + it.name!!)
                        }
                    }
                }
            }
        }
    }
}