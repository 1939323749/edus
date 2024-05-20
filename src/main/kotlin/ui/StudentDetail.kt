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
import data.model.student.StudentDetail
import data.model.student.UpdateStudent
import kotlinx.coroutines.launch

private enum class StudentDetailState {
    Display,
    Edit
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentDetail(
    id: Int,
    onNavigateUp: (() -> Unit)? = null
){
    var student by remember { mutableStateOf(StudentDetail()) }
    var state by remember { mutableStateOf(StudentDetailState.Display) }
    LaunchedEffect(Unit){
        student = RemoteDataSource.getStudentDetails(id)
    }
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = "Student Detail") },
                navigationIcon = {
                    if (onNavigateUp != null) {
                        IconButton(onClick = {
                            onNavigateUp.invoke()
                        }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                },
                actions = {
                    IconButton(onClick = {
                        state = if(state == StudentDetailState.Display) StudentDetailState.Edit else StudentDetailState.Display
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
                StudentDetailState.Display -> {
                    student.data?.let { data ->
                        Column {
                            Text(text = "学生", style = MaterialTheme.typography.titleLarge)
                            Text(text = "姓名:" + data.name!!)
                            Text(text = "email:" + data.email!!)
                            Text(text = "性别:" + data.sex!!)
                            Text(text = "id:" + data.id!!)
                            Text(text = "入学时间:" + data.enrolledTime!!)
                            Text(text = "专业:" + data.major?.name!!)
                            Text(text = "学院/系:" + data.major.departments?.first()?.name)
                        }
                    }
                }
                StudentDetailState.Edit -> {
                    val coroutine = rememberCoroutineScope()
                    editStudent(student){
                        coroutine.launch {
                            student = RemoteDataSource.getStudentDetails(id)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun editStudent(
    studentData: StudentDetail,
    postEdit: () -> Unit
){
    var email by remember { mutableStateOf(studentData.data?.email) }
    var result by remember { mutableStateOf(UpdateStudent()) }
    val coroutine = rememberCoroutineScope()

    Column {
        OutlinedTextField(
            value = email?: "",
            onValueChange = {
                email = it
            },
            label = { Text(text = "Email") }
        )
    }
    Button(onClick = {
        coroutine.launch {
            result = RemoteDataSource.updateStudent(studentData.data?.id!!, studentData.copy(
                data = studentData.data.copy(
                    email = email,
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