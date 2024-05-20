package ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import compose.icons.TablerIcons
import compose.icons.tablericons.FileImport
import data.datasource.RemoteDataSource
import data.model.getallclassresp.GetAllClassesResp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassScreen(
    onClassClick: (Int) -> Unit
) {
    var allClasses by remember { mutableStateOf(GetAllClassesResp()) }

    LaunchedEffect(Unit){
        allClasses = RemoteDataSource.getAllClasses()
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
                        RemoteDataSource.importClasses(row)
                        allClasses = RemoteDataSource.getAllClasses()
                    }
                }
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Class")
            },
                actions = {
                    IconButton(onClick = {
                        showFilePicker = true
                    }){
                        Icon(imageVector = TablerIcons.FileImport, contentDescription = null)
                    }
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