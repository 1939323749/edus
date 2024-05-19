package ui


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import data.datasource.RemoteDataSource
import data.model.courses.CourseDetail
import data.model.courses.UpdateCourse
import data.model.location.AllLocations
import data.model.tb.TimeBlock
import kotlinx.coroutines.launch

private enum class CourseDetailScreenState {
    Display,
    Edit
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseDetail(
    id: Int,
    onNavigateUp: () -> Unit
) {
    var courseData by remember { mutableStateOf(CourseDetail()) }
    var currentScreenState by remember { mutableStateOf(CourseDetailScreenState.Display) }
    LaunchedEffect(Unit) {
        courseData = RemoteDataSource.getCourseDetail(id)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Course Detail") },
                navigationIcon = {
                    IconButton(onClick = {
                        onNavigateUp.invoke()
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        currentScreenState = when(currentScreenState) {
                            CourseDetailScreenState.Display -> CourseDetailScreenState.Edit
                            CourseDetailScreenState.Edit -> CourseDetailScreenState.Display
                        }
                    }){
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                    }
                }
            )
        }
    ) { padding ->
        when(currentScreenState){
            CourseDetailScreenState.Display -> {
                LazyColumn {
                    item {
                        Column(
                            modifier = Modifier.padding(padding)
                        ) {
                            Text(text = "课程", style = MaterialTheme.typography.titleLarge)
                            courseData.data?.let { data ->
                                Column {
                                    Text(text = "名称：" + data.name!!)
                                    Text(text = "序号：" + data.id!!)
                                    Divider()
                                    Text(text = "选课学生：")
                                    Divider()
                                    data.students?.forEach {
                                        it?.let {
                                            Text(text = "序号：" + it.id!!)
                                            Text(text = "名字：" + it.name!!)
                                        }
                                    }
                                    Divider()
                                    Text(text = "任教教师")
                                    data.teachers?.forEach {
                                        it?.let {
                                            Text(text = "序号：" + it.id!!)
                                            Text(text = "名字: " + it.name!!)
                                        }
                                    }
                                    Divider()
                                    Text("时间")
                                    data.timeBlocks?.forEach{
                                        it?.let {
                                            Text(text = "id:" + it.id!!)
                                            Text(text = it.weekStart.toString() + "~" + it.weekEnd.toString() + "周" + it.start.toString() + "~" + it.end.toString() + "节")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            CourseDetailScreenState.Edit -> {
                Column (
                    modifier = Modifier.padding(padding)
                ){
                    editClassDetail(courseData)
                }

            }
        }

    }
}

@Composable
fun editClassDetail(
    courseData: CourseDetail
){
    var name by remember { mutableStateOf(courseData.data?.name) }
    var credit by remember { mutableStateOf(courseData.data?.credit) }
    var description by remember { mutableStateOf(courseData.data?.description) }
    var result by remember { mutableStateOf(UpdateCourse()) }

    var locations by remember { mutableStateOf(AllLocations()) }
    var timeBlocks by remember { mutableStateOf(TimeBlock()) }

    var showLocations by remember { mutableStateOf(false) }
    var selectedLocationId by remember { mutableStateOf(0) }
    var selectedTimeBlockId by remember { mutableStateOf(0) }

    LaunchedEffect(Unit){
        locations = RemoteDataSource.getAllLocations()
        timeBlocks = RemoteDataSource.getTimeBlocks()
    }

    Column {
        OutlinedTextField(
            value = name?: "",
            onValueChange = {
                name = it
            },
            label = {
                Text("name")
            }
        )
        OutlinedTextField(
            value = credit?.toString()?: "",
            onValueChange = {
                credit = it.toIntOrNull()
            },
            label = {
                Text("credit")
            }
        )
        OutlinedTextField(
            value = description?: "",
            onValueChange = {
                description = it
            },
            label = {
                Text("description")
            }
        )
        Column {
            var show by remember { mutableStateOf(false) }
            AssistChip(
                onClick = {
                    show = !show
                },
                label = {
                    Text(("地点: " + when(selectedLocationId){
                        0 -> "未选择"
                        else -> locations.data?.find { it?.id == selectedLocationId }?.name
                    }))
                }
            )
            DropdownMenu(
                expanded = show,
                onDismissRequest = { show = false },
            ) {
                locations.data?.forEach {
                    DropdownMenuItem(
                        text = {
                            Text(text = it?.name!!)
                        },
                        onClick = {
                            if (it != null) {
                                selectedLocationId = it.id!!
                            }
                            show = false
                        }
                    )
                }
            }
        }
        Column {
            var show by remember { mutableStateOf(false) }
            AssistChip(
                onClick = {
                    show = !show
                },
                label = {
                    Text(("时间: " + when(selectedTimeBlockId){
                        0 -> "未选择"
                        else -> timeBlocks.data?.find { it?.id == selectedTimeBlockId }?.let { it1 ->
                            "${it1.weekStart}~${it1.weekEnd}周${it1.start}~${it1.end}节"
                        }
                    }))
                }
            )
            DropdownMenu(
                expanded = show,
                onDismissRequest = { show = false },
            ) {
                timeBlocks.data?.forEach {
                    DropdownMenuItem(
                        text = {
                            Text(text = it?.let { it1 ->
                                "${it1.weekStart}~${it1.weekEnd}周${it1.start}~${it1.end}节"
                            }!!)
                        },
                        onClick = {
                           if (it != null) {
                               selectedTimeBlockId = it.id!!
                           }
                       }
                    )
                   }
               }
        }

        val coroutine = rememberCoroutineScope()
        Button(onClick = {
            courseData.data?.let {
                coroutine.launch {
                    result = RemoteDataSource.updateCourse(courseId = it.id!!,mapOf(
                        "name" to name,
                        "credit" to credit,
                        "description" to description,
                        "location" to mapOf(
                            "loca_id" to selectedLocationId,
                            "time_block_id" to selectedTimeBlockId,
                        )
                    ))
                }
            }
        }){
            Text(text = "Save")
        }
        if (result.msg != null){
            Text(text = result.msg!!)
        }
    }
}