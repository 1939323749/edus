
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import compose.icons.FeatherIcons
import compose.icons.TablerIcons
import compose.icons.feathericons.Home
import compose.icons.feathericons.User
import compose.icons.feathericons.Users
import compose.icons.tablericons.Certificate
import compose.icons.tablericons.MapPin
import compose.icons.tablericons.Pencil
import compose.icons.tablericons.Table
import ui.*
import ui.admin.TimeBlockScreen
import ui.data.Screen
import ui.student.*


enum class AdminNavigationItem(val title: String, val route: Screen, val icon: @Composable () -> Unit){
    Class("班级", Screen.Class, { Icon(imageVector = Icons.Default.Home, contentDescription = null) }),
    Student("学生", Screen.Student, { Icon(imageVector = Icons.Default.Home, contentDescription = null) }),
    Course("课程", Screen.Course, { Icon(imageVector = Icons.Default.Home, contentDescription = null) }),
    Teacher("教师", Screen.Teacher, { Icon(imageVector = Icons.Default.Home, contentDescription = null) }),
    Overview("总览", Screen.Overview, { Icon(imageVector = Icons.Default.Home, contentDescription = null) }),
    Location("地点", Screen.Location, { Icon(imageVector = Icons.Default.Home, contentDescription = null) }),
    TimeBlock("时间段", Screen.TimeBlock, { Icon(imageVector = Icons.Default.Home, contentDescription = null) }),
}

enum class StudentScreen(val title: String, val route: Screen, val icon: @Composable () -> Unit){
    Home("总览", Screen.Home, { Icon(imageVector = FeatherIcons.Home, contentDescription = null) }),
    Profile("个人信息", Screen.Profile, { Icon(imageVector = FeatherIcons.User, contentDescription = null) }),
    Class("班级", Screen.Class, { Icon(imageVector = FeatherIcons.Users, contentDescription = null) }),
    Course("课程", Screen.Course, { Icon(imageVector = TablerIcons.Table, contentDescription = null) }),
    Exam("考试", Screen.StudentExam, { Icon(imageVector = TablerIcons.Pencil, contentDescription = null) }),
    Score("成绩", Screen.StudentScore, { Icon(imageVector = TablerIcons.Certificate, contentDescription = null) }),
    AvailableClassRoom("空闲教室", Screen.AvailableClassRoom, { Icon(imageVector = TablerIcons.MapPin, contentDescription = null) }),
    CourseManager("课程管理", Screen.CourseManager, { Icon(imageVector = TablerIcons.Table, contentDescription = null) }),
}

enum class Role{
    Admin,
    Teacher,
    Student
}

@Composable
@Preview
fun App() {
    var adminScreen by remember { mutableStateOf(Screen.Overview) }
    var studentScreen by remember { mutableStateOf(StudentScreen.Profile) }
    var role by remember { mutableStateOf(Role.Student) }
    var selectedStudentId by remember { mutableStateOf(0) }
    var selectedClassId by remember { mutableStateOf(0) }
    var selectedCourseId by remember { mutableStateOf(0) }
    var selectedTeacherId by remember { mutableStateOf(0) }

    MaterialTheme {
        when(role){
            Role.Student -> {
                Row (
                    modifier = Modifier.fillMaxSize()
                ){
                    NavigationRail(
                        header = {
                            Text(text = "学生端")
                        }
                    ) {
                        StudentScreen.entries.forEach {
                            NavigationRailItem(
                                selected = studentScreen.route == it.route,
                                onClick = { studentScreen = it },
                                icon = it.icon,
                                label = { Text(it.title) }
                            )
                        }
                    }
                        Column (
                            modifier = Modifier.fillMaxSize()
                        ){
                            when(studentScreen){
                                StudentScreen.Home -> {
                                    StudentHomeScreen()
                                }
                                StudentScreen.Profile -> {
                                    StudentDetail(1)
                                }
                                StudentScreen.Class -> {
                                    StudentClassDetail(1)
                                }
                                StudentScreen.Course -> {
                                    CourseScreen(1)
                                }
                                StudentScreen.Exam -> {
                                    StudentExam(1)
                                }
                                StudentScreen.Score -> {
                                    StudentScoreScreen(1)
                                }
                                StudentScreen.AvailableClassRoom -> {
                                    AvailableClassRoomScreen()
                                }
                                StudentScreen.CourseManager -> {
                                    CourseManageScreen(1)
                                }
                                else -> {
                                    StudentDetail(1)
                                }
                            }
                        }

                }

        }
            Role.Admin -> {
                Row (
                    modifier = Modifier.fillMaxSize()
                ){
                    NavigationRail {
                        AdminNavigationItem.entries.forEach {
                            NavigationRailItem(
                                selected = adminScreen == it.route,
                                onClick = { adminScreen = it.route },
                                icon = it.icon,
                                label = { Text(it.title) }
                            )
                        }
                    }
                    when(adminScreen){
                        Screen.Class -> ClassScreen{
                            selectedClassId = it
                            adminScreen = Screen.ClassDetail
                        }
                        Screen.ClassDetail -> ClassDetail(
                            id = selectedClassId,
                            onNavigateUp = {
                                adminScreen = Screen.Class
                            }
                        )
                        Screen.Course -> Courses {
                            selectedCourseId = it
                            adminScreen = Screen.CourseDetail
                        }
                        Screen.CourseDetail -> CourseDetail(
                            id = selectedCourseId,
                            onNavigateUp = {
                                adminScreen = Screen.Course
                            }
                        )
                        Screen.Student -> StudentScreen(
                            onStudentClick = {
                                selectedStudentId = it
                                adminScreen = Screen.StudentDetail
                            }
                        )
                        Screen.StudentDetail -> StudentDetail(selectedStudentId){
                            adminScreen = Screen.Student
                        }
                        Screen.Teacher -> Teachers (
                            onTeacherClick = {
                                selectedTeacherId = it
                                adminScreen = Screen.TeacherDetail
                            }
                        )
                        Screen.TeacherDetail -> TeacherDetail(selectedTeacherId){
                            adminScreen = Screen.Teacher
                        }
                        Screen.Overview -> OverviewScreen()
                        Screen.TimeBlock -> {
                            TimeBlockScreen()
                        }
                        else -> {
                            Text("Not implemented")
                        }
                    }
                }

            }
            else -> {
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
