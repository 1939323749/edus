package ui.admin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import compose.icons.TablerIcons
import compose.icons.tablericons.Plus
import data.datasource.RemoteDataSource
import data.model.tb.TimeBlock
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeBlockScreen(){
    var timeBlocks by remember { mutableStateOf(TimeBlock()) }
    LaunchedEffect(Unit){
        timeBlocks = RemoteDataSource.getTimeBlocks()
    }
    var showAddTimeBlockDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "时间段管理")
            },
                actions = {
                    IconButton(onClick = {
                        showAddTimeBlockDialog = true
                    }){
                        Icon(imageVector = TablerIcons.Plus, contentDescription = null)
                    } })
        }
    ) {paddingValues ->
        if (showAddTimeBlockDialog){
            addTimeBlock {
                showAddTimeBlockDialog = false
            }

        }
        LazyColumn (
            modifier = Modifier.padding(paddingValues = paddingValues)
        ){
            items(timeBlocks.data?.size ?: 0){ index ->
                ListItem(
                    headlineContent = {
                        Text(text = "第" + timeBlocks.data?.get(index)?.weekStart.toString() + " - " + timeBlocks.data?.get(index)?.weekEnd.toString() + "周")
                        Text(text = "第"+ timeBlocks.data?.get(index)?.start.toString() + " - " + timeBlocks.data?.get(index)?.end.toString() + "节")
                    },
                    leadingContent = {
                        Text(text = "星期" + timeBlocks.data?.get(index)?.dayOfWeek?.toWeekCn())
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun addTimeBlock(onDismissRequest: () -> Unit){
    var weekStart by remember { mutableStateOf(1) }
    var weekEnd by remember { mutableStateOf(1) }
    var dayOfWeek by remember { mutableStateOf(1) }
    var start by remember { mutableStateOf(1) }
    var end by remember { mutableStateOf(1) }
    AlertDialog(
        onDismissRequest = onDismissRequest
        ){
        Column {
            Text(text = "添加时间段")
            TextField(
                value = weekStart.toString(),
                onValueChange = {
                    weekStart = it.toInt()
                },
                label = {
                    Text(text = "开始周")
                }
            )
            TextField(
                value = weekEnd.toString(),
                onValueChange = {
                    weekEnd = it.toInt()
                },
                label = {
                    Text(text = "结束周")
                }
            )
            TextField(
                value = dayOfWeek.toString(),
                onValueChange = {
                    dayOfWeek = it.toInt()
                },
                label = {
                    Text(text = "星期几")
                }
            )

            TextField(
                value = start.toString(),
                onValueChange = {
                    start = it.toInt()
                },
                label = {
                    Text(text = "开始节")
                }
            )
            TextField(
                value = end.toString(),
                onValueChange = {
                    end = it.toInt()
                },
                label = {
                    Text(text = "结束节")
                }
            )
            }
        val coroutine = rememberCoroutineScope()
            Button(
                onClick = {
                    coroutine.launch {
                        RemoteDataSource.addTimeBlock(weekStart, weekEnd, dayOfWeek, start, end)
                        onDismissRequest.invoke()
                    }
                }
            ) {
                Text(text = "添加")
            }
    }
}

fun Int.toWeekCn(): String {
    return when(this){
        1 -> "一"
        2 -> "二"
        3 -> "三"
        4 -> "四"
        5 -> "五"
        6 -> "六"
        7 -> "日"
        else -> "未知"
    }
}