package com.dra.speakeaseapppatient.navigation

sealed class NavRoute(val route: String) {
    object Speak : NavRoute("speak")
    object Pain : NavRoute("pain")
    object Need : NavRoute("need")
    object Person : NavRoute("person")
    object Profile : NavRoute("profile")
    object Emergency : NavRoute("emergency")
}
