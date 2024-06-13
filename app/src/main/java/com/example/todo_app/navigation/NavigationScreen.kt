package com.example.todo_app.navigation

enum class NavigationScreen {
    AddData,
    RetrieveData;
    fun fromRoute(route: String): NavigationScreen =
        when (route.substringBefore("/")) {
            AddData.name -> AddData
            RetrieveData.name -> RetrieveData
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
}