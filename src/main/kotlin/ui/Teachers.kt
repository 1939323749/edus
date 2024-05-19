package ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.onClick
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import compose.icons.TablerIcons
import compose.icons.tablericons.FileImport
import data.datasource.RemoteDataSource
import data.model.teachers.AllTeachers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class,  ExperimentalFoundationApi::class)
@Composable
fun Teachers(
    onTeacherClick: (Int) -> Unit
){
    var teachers by remember { mutableStateOf(AllTeachers()) }

    LaunchedEffect(Unit){
        teachers = RemoteDataSource.getAllTeachers()
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
                        RemoteDataSource.importTeacher(row)
                        teachers = RemoteDataSource.getAllTeachers()
                    }
                }
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Teachers")
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
            teachers.data?.forEach {
                item {
                    ListItem(
                        headlineContent = {
                        Text(text = it?.name!!)
                        },
                        supportingContent = {
                                            Text(it?.department?.name!!)
                        },
                        modifier = Modifier.onClick {
                            onTeacherClick.invoke(it?.id!!)
                        }
                    )
                }
            }
        }
    }
}