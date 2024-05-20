package ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.onClick
import androidx.compose.material.Scaffold
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import compose.icons.TablerIcons
import compose.icons.tablericons.FileImport
import data.datasource.RemoteDataSource
import data.model.courses.AllCourses
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class,  ExperimentalFoundationApi::class)
@Composable
fun Courses(
    onCourseClick: (Int) -> Unit
){
    var courses by remember { mutableStateOf(AllCourses()) }

    LaunchedEffect(Unit){
        courses = RemoteDataSource.getAllCourses()
    }
    val coroutine = rememberCoroutineScope()
    var showFilePicker by remember { mutableStateOf(false) }
    val fileType = listOf("csv")
    FilePicker(show = showFilePicker, fileExtensions = fileType) { platformFile ->
        showFilePicker = false
        platformFile?.let {
            csvReader().open(it.path) {
                readAllWithHeaderAsSequence().forEach { row: Map<String, String> ->
                    println(row)
                    coroutine.launch {
                        RemoteDataSource.importCourse(row)
                        courses = RemoteDataSource.getAllCourses()
                    }
                }
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Courses")
                },
                actions = {
                    IconButton(onClick = {
                        showFilePicker = true
                    }){
                        Icon(imageVector = TablerIcons.FileImport, contentDescription = null)
                    }
                }
            )
        }
    ) {paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            courses.data?.forEach {
                item {
                    ListItem(
                        headlineContent = {
                        Text(text = it?.name!!)
                        },
                        modifier = Modifier.onClick {
                            onCourseClick.invoke(it?.id!!)
                        }
                    )
                }
            }
        }
    }
}