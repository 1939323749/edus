package ui.admin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import data.datasource.RemoteDataSource
import data.model.exam.AllExams
import ui.student.toDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamScreen(){
    var exam by remember { mutableStateOf(AllExams()) }
    LaunchedEffect(Unit){
        exam = RemoteDataSource.getAllExams()
    }

    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text("考试")
                }
            )
        }
    ){
        LazyColumn (
            modifier = Modifier.padding(it)
        ){
            exam.data?.exams?.forEach {
                item {
                    ListItem(
                        headlineContent = {
                            Text(it?.name!!)
                        },
                        supportingContent = {
                            Column {
                                Text(it?.start?.toDate()+ "-" + it?.end?.toDate())
                                it?.course?.firstOrNull()?.let { it1 -> Text(it1.name!!) }
                                it?.location?.firstOrNull()?.let { it1 -> Text(it1.name!!) }
                            }
                        }
                    )
                }
            }
        }
    }
}