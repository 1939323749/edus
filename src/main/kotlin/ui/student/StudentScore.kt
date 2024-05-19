package ui.student

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import data.datasource.RemoteDataSource
import data.model.student.StudentScore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentScoreScreen(
    id: Int,
) {
    var score by remember { mutableStateOf(StudentScore()) }
    LaunchedEffect(Unit){
        score = RemoteDataSource.getStudentScore(id)
    }
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "成绩")
            })
        }
    ) {padding->
        LazyColumn (
            modifier = Modifier.fillMaxSize().padding(padding)
        ){
            item {
                score.data?.forEach {
                    ListItem(
                        headlineContent = {
                            Text(text = "${it?.exam?.name}: ${it?.score}")
                        },
                        supportingContent = {
                            Text(text = "绩点： ${it?.grade}")
                        }
                    )
                }
            }
        }
    }
}