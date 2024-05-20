package ui.teacher

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import compose.icons.TablerIcons
import compose.icons.tablericons.Download
import compose.icons.tablericons.FileImport
import data.datasource.RemoteDataSource
import data.model.exam.ExamStudent
import data.model.exam.TeacherExam
import kotlinx.coroutines.launch
import ui.student.toDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherExamScrren(
    id: Int,
){
    var exams by remember { mutableStateOf(TeacherExam()) }
    LaunchedEffect(Unit){
        exams = RemoteDataSource.getTeacherExam(id)
    }

    var showExamDetail by remember { mutableStateOf(false) }
    var selectedExam by remember { mutableStateOf(0) }
    if(showExamDetail){
        examDetail(
            id = selectedExam,
        ){
            showExamDetail = false
        }
    }
    else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("考试")
                    }
                )
            }
        ) {

            LazyColumn(
                modifier = Modifier.padding(it)
            ) {
                exams.data?.forEach {
                    item {
                        ListItem(
                            headlineContent = {
                                Text(it?.name ?: "")
                            },
                            supportingContent = {
                                Column {
                                    Text(it?.start?.toDate() ?: "")
                                    Text(it?.end?.toDate() ?: "")
                                }
                            },
                            modifier = Modifier.clickable {
                                selectedExam = it?.id ?: 0
                                showExamDetail = true
                            }
                        )
                    }
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
private fun examDetail(
    id: Int,
    onNavigateUp: () -> Unit
){
    var examDetail by remember { mutableStateOf(ExamStudent()) }
    LaunchedEffect(Unit){
        examDetail = RemoteDataSource.getExamStudent(id)
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
                        RemoteDataSource.importScore(id,row)
                    }
                }
            }
        }
    }
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                        Text("考试详情")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onNavigateUp()
                        }
                    ){
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            examDetail.data?.course?.firstOrNull()?.students?.let {
                                val writer = csvWriter()
                                writer.open(examDetail.data!!.course?.firstOrNull()?.name +"scores.csv") {
                                    it.forEach {
                                        writeRow(listOf(it?.id!!, it.name!!))
                                    }
                                }
                            }
                        }
                    ){
                        Icon(imageVector = TablerIcons.Download, contentDescription = null)
                    }
                    IconButton(onClick = {
                        showFilePicker = true
                    }){
                        Icon(imageVector = TablerIcons.FileImport, contentDescription = null)
                    }
                }
            )
        }
    ){
        LazyColumn (
            modifier = Modifier.padding(it)
        ){
            item {
                Text("学生")
            }
            examDetail.data?.course?.forEach {
                item {
                    Text(it?.name!!)
                    it.students?.forEach {
                        Text(it?.name!!)
                    }
                }
            }
        }
    }
}