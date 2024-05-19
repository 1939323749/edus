package ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import compose.icons.TablerIcons
import compose.icons.tablericons.FileImport
import data.datasource.RemoteDataSource
import data.model.student.getallstudent.GetAllStudentResp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentScreen(
    onStudentClick: (Int) -> Unit,
){
    var students by remember { mutableStateOf(GetAllStudentResp()) }
    LaunchedEffect(Unit){
        students = RemoteDataSource.getAllStudents()
    }
    val coroutine = rememberCoroutineScope()
    var showFilePicker by remember { mutableStateOf(false) }
    val fileType = listOf("csv")
    FilePicker(show = showFilePicker, fileExtensions = fileType) { platformFile ->
        showFilePicker = false
        platformFile?.let {
            csvReader().open(it.path) {
                readAllWithHeaderAsSequence().forEach { row: Map<String, String> ->
                    coroutine.launch {
                        RemoteDataSource.importStudent(row)
                        students = RemoteDataSource.getAllStudents()
                    }
                }
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Students")
            },
                actions = {
                    IconButton(onClick = {
                        showFilePicker = true
                    }){
                        Icon(imageVector = TablerIcons.FileImport, contentDescription = null)
                    }
                }
                )
        },
    ) {
        LazyColumn(
            modifier = Modifier.padding(it)
        ) {
            items(students.data?.size?:0){
                ListItem(
                    headlineContent = {
                        Text(text = students.data?.get(it)?.name!!)
                    },
                    modifier = Modifier.clickable {
                        onStudentClick(students.data?.get(it)?.id?:1)
                    }
                )
            }
        }
    }

}