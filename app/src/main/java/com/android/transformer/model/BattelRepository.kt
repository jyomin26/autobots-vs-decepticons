package com.android.transformer.model

import android.util.Log
import com.android.transformer.util.Utility
import java.util.*
import kotlin.collections.ArrayList

/**
 *Repository to perform the result of battel between autobots and decepticons
 */
object BattelRepository {
    private var autobotsList = ArrayList<Transformer>()
    private var decepticonsList = ArrayList<Transformer>()
    private var survivors = ArrayList<Transformer>()
    private var fightCount = 0
    private var autobotWinCount = 0
    private var deceptionWinCount = 0
    private var optimusPrime = "OPTIMUS PRIME"
    private var predaking = "PREDAKING"
    private var isGameEndBySpecialRule = false

    fun startBattel(transformers: ArrayList<Transformer>) {
        //separate the list by team
        autobotsList = transformers.filter { it.team == "A" } as ArrayList<Transformer>
        decepticonsList = transformers.filter { it.team == "D" } as ArrayList<Transformer>

        //sort teams by rank
        Collections.sort(autobotsList, { lhs, rhs -> rhs.rank.compareTo(lhs.rank) })
        Collections.sort(decepticonsList, { lhs, rhs -> rhs.rank.compareTo(lhs.rank) })

        //Check number of battel counts
        if (autobotsList.size < decepticonsList.size) {
            fightCount = autobotsList.size
            createSurviourList(fightCount, decepticonsList)
        } else {
            fightCount = decepticonsList.size
            createSurviourList(fightCount, autobotsList)
        }

        //start the battel against each competitor
        for (i in 0..fightCount - 1) {
            if (!isGameEndBySpecialRule)
                faceOff(autobotsList.get(i), decepticonsList.get(i))
        }
    }

    /**
     * Faceoff between autobot and decepticon
     */
    private fun faceOff(autobot: Transformer, decepticon: Transformer) {
        //Special Rule 2 : If names are same for the opponents then battel ends
        if ((autobot.name.toUpperCase().equals(optimusPrime) && decepticon.name.toUpperCase()
                .equals(optimusPrime)) || (autobot.name.toUpperCase().equals(
                predaking
            )) && decepticon.name.toUpperCase().equals(
                predaking
            )
        ) {
            isGameEndBySpecialRule = true
            return
        }

        //Special Rule 1 : check which transformer has Name Optimus Prime or Predaking inorder to win the battel
        if (autobot.name.toUpperCase().equals(optimusPrime) || autobot.name.toUpperCase()
                .equals(predaking)
        ) {
            autobotWinCount = autobotWinCount + 1
            return
        }

        if (decepticon.name.toUpperCase().equals(optimusPrime) || decepticon.name.toUpperCase()
                .equals(
                    predaking
                )
        ) {
            deceptionWinCount = deceptionWinCount + 1
            return
        }

        //Check courage and strength
        if (autobot.courage <= 4 && autobot.strength <= 3 && decepticon.courage <= 4 && decepticon.strength <= 3) {
            //event of a tie, both Transformers are considered destroyed
            return
        }

        if (autobot.courage <= 4 && autobot.strength <= 3) {
            deceptionWinCount = deceptionWinCount + 1
            return
        }

        if (decepticon.courage <= 4 && decepticon.strength <= 3) {
            autobotWinCount = autobotWinCount + 1
            return
        }

        //check strength
        if (autobot.skill >= 3 && decepticon.skill >= 3) {
            //event of a tie, both Transformers are considered destroyed
            return
        }

        if (autobot.skill >= 3) {
            autobotWinCount = autobotWinCount + 1
            return
        }

        if (decepticon.skill >= 3) {
            deceptionWinCount = deceptionWinCount + 1
            return
        }

        //check the overall rating
        if (Utility.getOverallRating(autobot) == Utility.getOverallRating(decepticon)) {
            //event of a tie, both Transformers are considered destroyed
            return
        }

        if (Utility.getOverallRating(autobot) > Utility.getOverallRating(decepticon)) {
            autobotWinCount = autobotWinCount + 1
        } else
            deceptionWinCount = deceptionWinCount + 1
    }

    private fun createSurviourList(
        fightCount: Int,
        transformers: ArrayList<Transformer>
    ) {
        for (i in fightCount..transformers.size - 1)
            survivors.add(transformers.get(i))
    }


    fun generateBattelResult(): String {
        var result = "$fightCount battel \n"

        if (fightCount == 0)
            return "No battel take place because of insufficient teams"

        if (isGameEndBySpecialRule)
            result = "The game was immediately ends with all competitors destroyed"
        else if (autobotWinCount == deceptionWinCount) {
            result = result + "It's a tie between both the teams"
        } else {
            if (autobotWinCount > deceptionWinCount) {
                result = result + "\n Winning team (Autobots):"
                for (transfor in autobotsList) {
                    result = result + " ${transfor.name}"
                }
                result = result + "\n Survivors from the losing team (Decepticons):"
                if (survivors.size > 0 && survivors.get(0).team.equals("D")) {
                    for (obj in survivors) {
                        result = result + obj.name
                    }
                }
            } else {
                result = result + "\n Winning team (Decepticons):"
                for (transfor in decepticonsList) {
                    result = result + " ${transfor.name}"
                }
                result = result + "\n Survivors from the losing team (Autobots):"
                if (survivors.size > 0 && survivors.get(0).team.equals("A")) {
                    for (obj in survivors) {
                        result = result + obj.name
                    }
                }
            }
        }
        return result
    }

    /**
     * Clear last battel if exists
     */
    fun clearLastBattel() {
        autobotsList.clear()
        decepticonsList.clear()
        survivors.clear()
        fightCount = 0
        autobotWinCount = 0
        deceptionWinCount = 0
        isGameEndBySpecialRule = false
    }

}