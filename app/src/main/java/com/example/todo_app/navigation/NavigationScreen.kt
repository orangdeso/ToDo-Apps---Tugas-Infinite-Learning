package com.example.todo_app.navigation

enum class NavigationScreen {
    AddData;
    fun fromRoute(route: String): NavigationScreen =
        when (route.substringBefore("/")) {
            AddData.name -> AddData
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
}