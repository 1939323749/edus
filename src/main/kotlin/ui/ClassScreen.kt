package ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import data.datasource.RemoteDataSource
import data.model.getallclassresp.GetAllClassesResp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassScreen(
    onClassClick: (Int) -> Unit
) {
    var allClasses by remember { mutableStateOf(GetAllClassesResp()) }

    LaunchedEffect(Unit){
        allClasses = RemoteDataSource.getAllClasses()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Class")
            })
        }
    ) {paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            allClasses.data?.forEach {
                ListItem(
                    headlineContent = {
                    if (it != null) {
                        it.name?.let { it1 -> Text(text = it1) }
                    } },
                    modifier = Modifier.clickable {
                        onClassClick.invoke(it!!.id!!)
                    },
                )
            }
        }
    }
}