package com.example.tasks

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tasks.ui.theme.TasksTheme
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : ComponentActivity() {
    lateinit var viewModel: TasksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel= ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application))[TasksViewModel::class.java]

        val context=this.applicationContext
        super.onCreate(savedInstanceState)
        setContent {
            TasksTheme {
                val allTask=viewModel.allTask.observeAsState(emptyList())
                val date=Date()
               val sdf=SimpleDateFormat("MMM d , EEEE ")
                Log.e("MainActivity",date.toString())

                val navController= rememberNavController()
                NavHost(navController,startDestination = HomeScreen.route){
                    composable(HomeScreen.route){
                        HomeScreen(context,allTask.value,navController,viewModel,sdf.format(date))
                    }
                    composable(SecondScreen.route){
                        AddTask(navController,viewModel)
                    }
                }
            }
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(

    context: Context,
    menuDatabaseItem: List<Task>,
    navController: NavHostController,
    viewModel: TasksViewModel,
    date:String
) {
    var listisEmpty by remember{
        mutableStateOf(false)
    }

    Scaffold(topBar = {TopAppBar(date = date)}, floatingActionButton = { FloatingButton(navController)}) {
        Surface(modifier = Modifier
            .padding(it)
            .fillMaxSize(), color = Color.Black) {
            Column(Modifier.padding(top=15.dp)) {
                listisEmpty= viewModel.isEmpty()
                if(!listisEmpty)
                TasksList(context,menuDatabaseItem,viewModel)
                else{
                    Text(text = "You have no tasks", color = Color.Gray)
                }

            }


        }
    }

}

