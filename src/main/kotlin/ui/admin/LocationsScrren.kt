package ui.admin

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import compose.icons.TablerIcons
import compose.icons.tablericons.Edit
import compose.icons.tablericons.FileImport
import data.datasource.RemoteDataSource
import data.model.location.AllLocations
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationScreen(){
    var locations by remember { mutableStateOf(AllLocations()) }
    LaunchedEffect(Unit){
        locations = RemoteDataSource.getAllLocations()
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
                        RemoteDataSource.importLocations(row)
                        locations = RemoteDataSource.getAllLocations()
                    }
                }
            }
        }
    }

    var showEdit by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(title = {
                              Text(text = "地点")
            },
                actions = {
                    IconButton(onClick = {
                        showFilePicker = true
                    }){
                        Icon(imageVector = TablerIcons.FileImport, contentDescription = null)
                    }
                    IconButton(onClick = {
                        showEdit = !showEdit
                    }){
                        Icon(imageVector = TablerIcons.Edit, contentDescription = null)
                    }
                }
                )
        },
    ) {
        LazyColumn (
            modifier = Modifier.padding(it)
        ){
            locations.data?.forEach {
                item {
                    ListItem(
                        headlineContent = {
                            Text(text = it?.name ?: "")
                        },
                        supportingContent = {
                            Text(text = it?.address ?: "")
                        },
                        trailingContent = {
                            if (showEdit) {
                                TextButton(onClick = {
                                    coroutine.launch {
                                        RemoteDataSource.deleteLocation(it?.id ?: 0)
                                        locations = RemoteDataSource.getAllLocations()
                                    }
                                }) {
                                    Text(text = "删除")
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}