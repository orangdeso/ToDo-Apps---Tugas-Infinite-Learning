package com.example.todo_app.navigation

enum class NavigationScreen {
    UploadPhotos,
    RetrievePhotos,
    HomeScreen;
    fun fromRoute(route: String): NavigationScreen =
        when (route.substringBefore("/")) {
            UploadPhotos.name -> UploadPhotos
            RetrievePhotos.name -> RetrievePhotos
            HomeScreen.name -> HomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
}