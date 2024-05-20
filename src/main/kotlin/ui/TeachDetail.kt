package ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import data.datasource.RemoteDataSource
import data.model.teachers.TeacherDetail
import data.model.teachers.UpdateTeacher
import kotlinx.coroutines.launch

private enum class TeachDetailState {
    Display,
    Edit
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherDetail(
    id: Int,
    onNavigateUp: (() -> Unit)? = null
){
    var teacher by remember { mutableStateOf(TeacherDetail()) }
    var state by remember { mutableStateOf(TeachDetailState.Display) }
    LaunchedEffect(Unit){
        teacher = RemoteDataSource.getTeacherDetail(id)
    }
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = "Teacher Detail") },
                navigationIcon = {
                    onNavigateUp?.let {
                        IconButton(onClick = {
                            onNavigateUp.invoke()
                        }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                },
                actions = {
                    IconButton(onClick = {
                        state = if(state == TeachDetailState.Display) TeachDetailState.Edit else TeachDetailState.Display
                    }){
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                    }
                }
            )
        }
    ){padding->
        Column(
            modifier = Modifier.padding(padding)
        ) {
            when(state){
                TeachDetailState.Display -> {
                    teacher.data?.let { data ->
                        Column {
                            Text(text = "ID: ${data.id}")
                            Text(text = "Name: ${data.name}")
                            Text(text = "Email: ${data.email}")
                            Text(text = "Title: ${data.title}")
                        }
                    }
                }
                TeachDetailState.Edit -> {
                    val coroutine = rememberCoroutineScope()
                    editStudent(teacher){
                        coroutine.launch {
                            teacher = RemoteDataSource.getTeacherDetail(id)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun editStudent(
    teacherData: TeacherDetail,
    postEdit: () -> Unit
){
    var name by remember { mutableStateOf(teacherData.data?.name) }
    var email by remember { mutableStateOf(teacherData.data?.email) }
    var title by remember { mutableStateOf(teacherData.data?.title) }
    var result by remember { mutableStateOf(UpdateTeacher()) }
    val coroutine = rememberCoroutineScope()
    Column {
        OutlinedTextField(
            value = name?: "",
            onValueChange = {
                name = it
            },
            label = { Text(text = "Name") }
        )
        OutlinedTextField(
            value = email?: "",
            onValueChange = {
                email = it
            },
            label = { Text(text = "Email") }
        )
        OutlinedTextField(
            value = title?: "",
            onValueChange = {
                title = it
            },
            label = { Text(text = "Title") }
        )
    }
    Button(onClick = {
        coroutine.launch {
            result = RemoteDataSource.updateTeacher(teacherData.data?.id!!, teacherData.copy(
                data = teacherData.data.copy(
                    name = name,
                    email = email,
                    title = title
                )
            ))
            if (result.msg == "success") postEdit.invoke()
        }
    }){
        Text(text = "Save")
    }
    result.msg?.let {
        Text(text = it)
    }
}