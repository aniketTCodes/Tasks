package com.example.tasks

interface Destinations {
    val route:String
}

object HomeScreen:Destinations{
    override val route: String="HomeScreen"

}

object  SecondScreen:Destinations{
    override  val route:String="SecondScreen"
}

