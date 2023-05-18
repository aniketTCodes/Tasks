package com.example.tasks

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Surface
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.ThresholdConfig
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tasks.ui.theme.Purple80
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@Composable
fun TopAppBar(date:String){
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(color = Color.Black), horizontalAlignment = Alignment.Start) {
        Text(text = "Tasks", style = MaterialTheme.typography.headlineSmall, fontSize = 64.sp, color = Color.White, modifier = Modifier.padding(start=10.dp, bottom = 4.dp))
        Text(text = date, fontSize = 21.sp, color = Color.White, modifier = Modifier.padding(start=18.dp, bottom = 20.dp))
    }

}

@Composable
fun TaskItem(context: Context, task: Task, database: TasksViewModel){
    Card(modifier = Modifier
        .fillMaxWidth(),
        colors = CardDefaults.cardColors(contentColor = Color.Black, containerColor = Color.Black),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        shape = RoundedCornerShape(1.dp)
        ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Checkbox(checked = task.done,
                modifier = Modifier.fillMaxWidth(0.15f),
                onCheckedChange = {
                    database.update(!task.done,task.id)
                    if(!task.done){
                        Toast.makeText(context,"You completed a task ! ",Toast.LENGTH_LONG).show()
                    }
                }
            )
            Text(
                text = task.task,
                modifier = Modifier.fillMaxWidth(),
                color = Color.White,
                maxLines = 1,
                fontSize = 20.sp,
                textAlign = TextAlign.Start
            )
        }
    }
}


@Composable
@OptIn( ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
fun TasksList(context: Context, tasks: List<Task>, database: TasksViewModel){
    LazyColumn(){

        items(
            items=tasks,
            key = {tasksItem->tasksItem.id},
            itemContent =
        {item->
            val currentItem by rememberUpdatedState(item)
                val dismissState= rememberDismissState(

                    confirmStateChange = {
                        if(it==DismissValue.DismissedToStart){
                        database.deleteTask(currentItem)
                        true}
                        else false
                    }

                )


            SwipeToDismiss(state = dismissState, background = {swipeBackground(dismissState = dismissState)},
                modifier=Modifier.animateItemPlacement(),
                directions = setOf(DismissDirection.EndToStart),
               dismissThresholds = {FractionalThreshold(0.5f)},
                dismissContent = {TaskItem(context = context, task = item, database = database)}
            )
        })
    }
}

@Composable
fun FloatingButton(navController: NavHostController) {
    Button(onClick = {navController.navigate(SecondScreen.route)},
        colors = ButtonDefaults.buttonColors(containerColor = Purple80),
        modifier = Modifier
            .clip(CircleShape)
            .size(110.dp)
            .padding(15.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Image(painter = painterResource(id = R.drawable.add_foreground), contentDescription = "Add task")
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun swipeBackground(dismissState: DismissState){
    Row(modifier= Modifier
        .background(color = Color.Red)
        .padding(10.dp)
        .fillMaxWidth(),
    horizontalArrangement = Arrangement.End) {
        Image(painter = painterResource(id = R.drawable.delete_foreground), contentDescription = "")
    }
}



