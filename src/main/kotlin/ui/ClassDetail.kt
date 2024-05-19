package ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import data.datasource.RemoteDataSource
import data.model.classes.ClassByIdResp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassDetail(
    id: Int,
    onNavigateUp: () -> Unit
){
    var classData by remember { mutableStateOf(ClassByIdResp()) }
    LaunchedEffect(Unit){
        classData = RemoteDataSource.getClass(id)
    }

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = "Class Detail") },
                navigationIcon = {
                    IconButton(onClick = {
                        onNavigateUp.invoke()
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
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