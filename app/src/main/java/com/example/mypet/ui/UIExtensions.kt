package com.example.mypet.ui

import android.app.Activity
import android.content.Context
import android.text.format.DateFormat
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mypet.app.R
import com.example.mypet.domain.pet.breed.PetBreedCat
import com.example.mypet.domain.pet.breed.PetBreedChameleon
import com.example.mypet.domain.pet.breed.PetBreedDog
import com.example.mypet.domain.pet.breed.PetBreedSnake
import com.example.mypet.domain.pet.breed.PetBreedSpider
import com.example.mypet.domain.pet.kind.PetKind
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar
import java.util.Calendar

fun View.snackMessage(text: String, length: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, text, length).show()
}

fun getPetsAge(timeMillis: Long): String {
    val birthDate = Calendar.getInstance()
    birthDate.timeInMillis = timeMillis

    val currentDate = Calendar.getInstance()

    val years = currentDate.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR)
    val months = currentDate.get(Calendar.MONTH) - birthDate.get(Calendar.MONTH)
    val days = currentDate.get(Calendar.DAY_OF_MONTH) - birthDate.get(Calendar.DAY_OF_MONTH)

    return if (years == 0 && months == 0) {
        getDaysString(days)
    } else if (months < 0) {
        val correctedYears = years - 1
        val correctedMonths = months + 12
        getAgeString(correctedYears, correctedMonths)
    } else {
        getAgeString(years, months)
    }
}

private fun getDaysString(days: Int): String {
    return when (days) {
        0 -> "Сегодня родился"
        1 -> "$days день"
        in 2..4 -> "$days дня"
        else -> "$days дней"
    }
}

private fun getAgeString(years: Int, months: Int): String {
    val yearsString = when {
        years == 0 -> ""
        years % 10 == 1 && years != 11 -> "$years год "
        years % 10 in 2..4 && (years % 100 < 10 || years % 100 >= 20) -> "$years года "
        else -> "$years лет "
    }

    val monthsString = when {
        months % 10 == 1 && months != 11 -> "$months месяц"
        months % 10 in 2..4 && (months % 100 < 10 || months % 100 >= 20) -> "$months месяца"
        else -> "$months месяцев"
    }

    return "$yearsString$monthsString"
}

fun Activity.showToast(text: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, length).show()
}

fun getPetName(kindOrdinal: Int, breedOrdinal: Int?) =
    getPetBreedName(kindOrdinal, breedOrdinal) ?: PetKind.entries[kindOrdinal].nameResId

private fun getPetBreedName(kindOrdinal: Int, breedOrdinal: Int?) =
    breedOrdinal?.let {
        if (breedOrdinal > 0) {
            try {
                when (kindOrdinal) {
                    0 -> PetBreedCat.entries[breedOrdinal].nameResId
                    1 -> PetBreedDog.entries[breedOrdinal].nameResId
                    12 -> PetBreedChameleon.entries[breedOrdinal].nameResId
                    14 -> PetBreedSnake.entries[breedOrdinal].nameResId
                    17 -> PetBreedSpider.entries[breedOrdinal].nameResId
                    else -> null
                }
            } catch (e: Exception) {
                println("UIExtensions -> getPetBreedIcon(): ${e.message}")
                null
            }
        } else null
    }

fun getPetBreedList(kindOrdinal: Int) =
    try {
        when (kindOrdinal) {
            0 -> PetBreedCat.entries.map { it.nameResId }
            1 -> PetBreedDog.entries.map { it.nameResId }
            12 -> PetBreedChameleon.entries.map { it.nameResId }
            14 -> PetBreedSnake.entries.map { it.nameResId }
            17 -> PetBreedSpider.entries.map { it.nameResId }
            else -> null
        }
    } catch (e: Exception) {
        println("UIExtensions -> getPetBreedIcon(): ${e.message}")
        null
    }

fun getPetIcon(kindOrdinal: Int, breedOrdinal: Int?) =
    getPetBreedIcon(kindOrdinal, breedOrdinal) ?: PetKind.entries[kindOrdinal].iconResId

private fun getPetBreedIcon(kindOrdinal: Int, breedOrdinal: Int?) =
    breedOrdinal?.let {
        try {
            when (kindOrdinal) {
                1 -> PetBreedCat.entries[breedOrdinal].iconResId
                2 -> PetBreedDog.entries[breedOrdinal].iconResId
                13 -> PetBreedChameleon.entries[breedOrdinal].iconResId
                15 -> PetBreedSnake.entries[breedOrdinal].iconResId
                18 -> PetBreedSpider.entries[breedOrdinal].iconResId
                else -> null
            }
        } catch (e: Exception) {
            println("UIExtensions -> getPetBreedIcon(): ${e.message}")
            null
        }
    }

fun Fragment.getActionBar() =
    (requireActivity() as? MainActivity)?.supportActionBar

fun Fragment.getToolbar() =
    (requireActivity() as? MainActivity)?.findViewById<MaterialToolbar>(R.id.toolbar)

fun MaterialToolbar?.clear() =
    this?.apply {
        title = null
        menu.clear()
    }

val Context.is24HourFormat
    get() = DateFormat.is24HourFormat(this)