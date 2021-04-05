package com.kardia.membership.data.entities

data class UpdateProgressMission(val is_completed: Boolean?) {
    companion object {
        fun empty() = UpdateProgressMission(false)
    }
}