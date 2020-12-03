package com.android.transformer.model

/**
 * Transformer with their specifications
 */
data class Transformer(
    var id: String = "",
    var name: String,
    var team: String,
    var strength: Int,
    var intelligence: Int,
    var speed: Int,
    var endurance: Int,
    var rank: Int,
    var courage: Int,
    var firepower: Int,
    var skill: Int,
    var team_icon: String? = ""
)