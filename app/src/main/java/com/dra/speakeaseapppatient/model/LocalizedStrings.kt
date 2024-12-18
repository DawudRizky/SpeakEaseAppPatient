package com.dra.speakeaseapppatient.model

import com.dra.speakeaseapppatient.R
import java.util.Locale

object LocalizedStrings {
    fun getNeedButtonLabels(locale: Locale): List<Pair<Int, String>> {
        return when (locale) {
            Locale("id") -> listOf(
                Pair(R.drawable.need_help, "Tolong"),
                Pair(R.drawable.need_drink, "Minum"),
                Pair(R.drawable.need_meal, "Makan"),
                Pair(R.drawable.need_medicine, "Obat"),
                Pair(R.drawable.need_wc, "Kamar Mandi"),
                Pair(R.drawable.need_luggage, "Barang")
            )
            else -> listOf(
                Pair(R.drawable.need_help, "Help"),
                Pair(R.drawable.need_drink, "Drink"),
                Pair(R.drawable.need_meal, "Meal"),
                Pair(R.drawable.need_medicine, "Medicine"),
                Pair(R.drawable.need_wc, "Bathroom"),
                Pair(R.drawable.need_luggage, "Luggage")
            )
        }
    }

    fun getPersonButtonLabels(locale: Locale): List<Pair<Int, String>> {
        return when (locale) {
            Locale("id") -> listOf(
                Pair(R.drawable.person_doctor, "Dokter"),
                Pair(R.drawable.person_nurse, "Perawat"),
                Pair(R.drawable.person_parent, "Orang Tua"),
                Pair(R.drawable.person_children, "Anak"),
                Pair(R.drawable.person_marriage, "Pasangan"),
                Pair(R.drawable.person_siblings, "Saudara")
            )
            else -> listOf(
                Pair(R.drawable.person_doctor, "Doctor"),
                Pair(R.drawable.person_nurse, "Nurse"),
                Pair(R.drawable.person_parent, "Parent"),
                Pair(R.drawable.person_children, "Children"),
                Pair(R.drawable.person_marriage, "Partner"),
                Pair(R.drawable.person_siblings, "Sibling")
            )
        }
    }

    fun getPainTabs(locale: Locale): List<String> {
        return when (locale) {
            Locale("id") -> listOf(
                "Tingkatan",
                "Lokasi"
            )
            else -> listOf(
                "Level",
                "Location"
            )
        }
    }

    fun getPainButtonLabels(locale: Locale): List<Pair<Int, String>> {
        return when (locale) {
            Locale("id") -> listOf(
                Pair(R.drawable.pain_0, "Tidak Sakit"),
                Pair(R.drawable.pain_2, "Sedikit Sakit"),
                Pair(R.drawable.pain_4, "Sedikit Lebih Sakit"),
                Pair(R.drawable.pain_6, "Lebih Sakit"),
                Pair(R.drawable.pain_8, "Sangat Sakit"),
                Pair(R.drawable.pain_10, "Tersakit")
            )
            else -> listOf(
                Pair(R.drawable.pain_0, "No Pain"),
                Pair(R.drawable.pain_2, "A Little Pain"),
                Pair(R.drawable.pain_4, "A Little More Pain"),
                Pair(R.drawable.pain_6, "More Pain"),
                Pair(R.drawable.pain_8, "Much Pain"),
                Pair(R.drawable.pain_10, "Most Pain")
            )
        }
    }

    fun getEmergencyText(locale: Locale): List<String> {
        return when (locale) {
            Locale("id") -> listOf(
                "DARURAT",
                "MEMANGGIL TENAGA MEDIS",
                "Tenaga medis dalam perjalanan",
                "Hentikan Panggilan Darurat"
            )
            else -> listOf(
                "EMERGENCY",
                "CALLING MEDICAL STAFF",
                "Medical staff are on their way",
                "Stop Emergency Call"
            )
        }
    }

    fun getSpeakText(locale: Locale): List<String> {
        return when (locale) {
            Locale("id") -> listOf(
                "Masukan Teks",
                "Bicara",
                "Riwayat"
            )
            else -> listOf(
                "Enter Text",
                "Speak",
                "History"
            )
        }
    }

    fun getNavLabels(locale: Locale): List<BottomNavItem> {
        return when (locale) {
            Locale("id") -> listOf(
                BottomNavItem("speak", "Bicara", R.drawable.nav_speak),
                BottomNavItem("pain", "Sakit", R.drawable.nav_pain),
                BottomNavItem("need", "Butuh", R.drawable.nav_need),
                BottomNavItem("person", "Orang", R.drawable.nav_person),
                BottomNavItem("profile", "profil", R.drawable.nav_profile)
            )
            else -> listOf(
                BottomNavItem("speak", "Speak", R.drawable.nav_speak),
                BottomNavItem("pain", "Pain", R.drawable.nav_pain),
                BottomNavItem("need", "Need", R.drawable.nav_need),
                BottomNavItem("person", "Person", R.drawable.nav_person),
                BottomNavItem("profile", "Profile", R.drawable.nav_profile)
            )
        }
    }
}
