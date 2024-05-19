package ui.student

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import data.datasource.RemoteDataSource
import data.model.exam.StudentExam
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

@OptIn(ExperimentalMaterial3Api::class,  ExperimentalFoundationApi::class)
@Composable
fun StudentExam(
    id: Int,
){
    var exams by remember { mutableStateOf(StudentExam()) }

    LaunchedEffect(Unit){
        exams = RemoteDataSource.getStudentExams(id)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "考试")
                },
            )
        },
    ) {paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            item {
                Text(text = "完成考试")
            }
            exams.data?.filter {
                Instant.from(DateTimeFormatter.ISO_INSTANT.parse(it?.end!!)) < Calendar.getInstance().toInstant()
            }?.forEach {
                item {
                    ListItem(
                        headlineContent = {
                            Text(text = it?.name!!)
                        },
                        supportingContent = {
                            Text(text = "考试课程：${it?.course?.get(0)?.name}")
                            Text(text = "考试描述：${it?.description}")
                            Text(text = "考试地点：${it?.location?.firstOrNull()?.name}")
                            Text(text = "考试时间：${it?.start?.toDate()} - ${it?.end?.toDate()}")
                        }
                    )
                }
            }
            item {
                Text(text = "未完成考试")
            }
            exams.data?.filter {
                Instant.from(DateTimeFormatter.ISO_INSTANT.parse(it?.start!!)) > Calendar.getInstance().toInstant()
            }?.forEach {
                item {
                    ListItem(
                        headlineContent = {
                            Text(text = it?.name!!)
                        },
                        supportingContent = {
                            Text(text = "考试课程：${it?.course?.get(0)?.name}")
                            Text(text = "考试描述：${it?.description}")
                            Text(text = "考试地点：${it?.location?.firstOrNull()?.name}")
                            Text(text = "考试时间：${it?.start?.toDate()} - ${it?.end?.toDate()}")
                        }
                    )
                }
            }
        }
    }
}

fun String.toDate(): String{
    return Instant.from(DateTimeFormatter.ISO_INSTANT.parse(this)).atZone(ZoneId.systemDefault()).run {
        "${this.year}-${this.monthValue}-${this.dayOfMonth} ${this.hour}:${this.minute.run { 
            if(this < 10) "0$this" else this
        }}"
    }
}