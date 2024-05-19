package ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.onClick
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import data.datasource.RemoteDataSource
import data.model.courses.AllCourses

@OptIn(ExperimentalMaterial3Api::class,  ExperimentalFoundationApi::class)
@Composable
fun Courses(
    onCourseClick: (Int) -> Unit
){
    var courses by remember { mutableStateOf(AllCourses()) }

    LaunchedEffect(Unit){
        courses = RemoteDataSource.getAllCourses()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Courses")
                },
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