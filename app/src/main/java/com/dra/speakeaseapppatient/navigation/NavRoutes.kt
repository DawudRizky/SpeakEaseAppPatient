package com.dra.speakeaseapppatient.navigation

sealed class NavRoute(val route: String) {
    data object Speak : NavRoute("speak")
    data object Pain : NavRoute("pain")
    data object Need : NavRoute("need")
    data object Person : NavRoute("person")
    data object Profile : NavRoute("profile")
    data object Emergency : NavRoute("emergency")
}
