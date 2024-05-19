package ui.student

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import data.datasource.RemoteDataSource
import data.model.notice.Notice

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentHomeScreen(){
    var notice by remember { mutableStateOf(
        Notice()
    ) }
    LaunchedEffect(Unit){
        notice = RemoteDataSource.getAllNotices()
    }
    Scaffold (
        topBar = {
            TopAppBar(title = {
                Text("欢迎使用教务管理系统")
            })
        },
    ){
        LazyColumn (
            modifier = Modifier.padding(it)
        ){
            item {
                Text("教务通知")
            }
            notice.data?.forEach {
                item {
                    it?.let {
                        ListItem(
                            leadingContent = { Text((it.id?:0).toString()) },
                            headlineContent = {
                                Text(it.title?:"")
                            },
                            supportingContent = {
                                Text(it.content?:"")
                            },
                            trailingContent = {
                                Text(it.createdAt?.toDate()?:"")
                            }
                        )
                    }

                }
            }
        }
    }
}