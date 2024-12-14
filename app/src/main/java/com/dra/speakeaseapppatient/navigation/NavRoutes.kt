package com.dra.speakeaseapppatient.navigation

sealed class NavRoute(val route: String) {
    object Bicara : NavRoute("bicara")
    object Sakit : NavRoute("sakit")
    object Butuh : NavRoute("butuh")
    object Orang : NavRoute("orang")
    object Profil : NavRoute("profil")
    object Emergency : NavRoute("emergency")
}
