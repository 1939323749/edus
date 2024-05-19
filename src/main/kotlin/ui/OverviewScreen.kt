package ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import data.datasource.RemoteDataSource
import data.model.overview.Overview

@Composable
fun OverviewScreen() {
    var overview by remember {
        mutableStateOf(Overview())
    }
    LaunchedEffect(Unit){
        overview = RemoteDataSource.getOverview()
    }
    Column {
        Text(text = "Classes: ${overview.data?.classesCount}")
        Text(text = "Courses: ${overview.data?.coursesCount}")
        Text(text = "Locations: ${overview.data?.locationsCount}")
        Text(text = "Students: ${overview.data?.studentsCount}")
        Text(text = "Teachers: ${overview.data?.teachersCount}")
        Text(text = "Time Blocks:${overview.data?.timeBlocksCount}")
    }
}