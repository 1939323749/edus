package ui.student

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.datasource.RemoteDataSource
import data.model.student.Classroom
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AvailableClassRoomScreen(){
    var classroom by remember { mutableStateOf(Classroom()) }
    var showWeekSelector by remember { mutableStateOf(false) }
    var showDayOfWeekSelector by remember { mutableStateOf(false) }
    var showStartSelector by remember { mutableStateOf(false) }
    var showEndSelector by remember { mutableStateOf(false) }
    var start by remember { mutableStateOf(8) }
    var end by remember { mutableStateOf(10) }
    var dayOfWeek by remember { mutableStateOf(1) }
    var week by remember { mutableStateOf(1) }
    LaunchedEffect(Unit){
        classroom = RemoteDataSource.getAvailableClassRoom(
            start = 8,
            end = 10,
            dayOfWeek = 1,
            week = 1
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "空教室")
            })
        }
    ) {paddingValues ->

        LazyColumn (
            modifier = Modifier.padding(paddingValues)
        ){
            item {
                FlowRow {
                    Column(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        AssistChip(onClick = { showWeekSelector = true }, label = {
                            Text(text = "周数: $week")
                        })
                        DropdownMenu(expanded = showWeekSelector, onDismissRequest = { showWeekSelector = false }){
                            for (i in 1..20){
                                DropdownMenuItem(text = {
                                    Text(text = "$i")
                                }, onClick = {
                                    week = i
                                    showWeekSelector = false
                                })
                            }
                        }
                    }
                    Column (
                        modifier = Modifier.padding(8.dp)
                    ){
                        AssistChip(onClick = { showDayOfWeekSelector = true }, label = {
                            Text(text = "星期: $dayOfWeek")
                        })
                        DropdownMenu(expanded = showDayOfWeekSelector, onDismissRequest = { showDayOfWeekSelector = false }){
                            for (i in 1..7){
                                DropdownMenuItem(text = {
                                    Text(text = "$i")
                                }, onClick = {
                                    dayOfWeek = i
                                    showDayOfWeekSelector = false
                                })
                            }
                        }
                    }
                    Column (
                        modifier = Modifier.padding(8.dp)
                    ){
                        AssistChip(onClick = { showStartSelector = true }, label = {
                            Text(text = "开始: $start")
                        })
                        DropdownMenu(expanded = showStartSelector, onDismissRequest = { showStartSelector = false }){
                            for (i in 1..12){
                                DropdownMenuItem(text = {
                                    Text(text = "$i")
                                }, onClick = {
                                    start = i
                                    showStartSelector = false
                                })
                            }
                        }
                    }
                    Column (
                        modifier = Modifier.padding(8.dp)
                    ){
                        AssistChip(onClick = { showEndSelector = true }, label = {
                            Text(text = "结束: $end")
                        })
                        DropdownMenu(expanded = showEndSelector, onDismissRequest = { showEndSelector = false }){
                            for (i in 1..12){
                                DropdownMenuItem(text = {
                                    Text(text = "$i")
                                }, onClick = {
                                    end = i
                                    showEndSelector= false
                                })
                            }
                        }
                    }
                    val coroutine = rememberCoroutineScope()
                    TextButton(onClick = {
                        coroutine.launch { classroom = RemoteDataSource.getAvailableClassRoom(
                            start = start,
                            end = end,
                            dayOfWeek = dayOfWeek,
                            week = week
                        )}
                    }){
                        Text(text = "查询")
                    }
                }
            }

            classroom.data?.size?.let {
                items(it){
                    ListItem(
                        headlineContent = {
                            Text(text = "${classroom.data?.get(it)?.name}")
                        },
                        supportingContent = {
                            Text(text = "${classroom.data?.get(it)?.address}")
                        }
                    )
                }
            }
        }
    }
}