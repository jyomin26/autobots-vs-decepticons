package com.android.transformer.util

import com.android.transformer.model.Transformer

/**
 * Generall Utility CLass for the Application
 */
object Utility {

    fun getOverallRating(transformer: Transformer): Int {
        //overall rating = (Strength + Intelligence + Speed + Endurance + Firepower).
        return transformer.strength + transformer.intelligence + transformer.speed + transformer.endurance + transformer.firepower
    }
}