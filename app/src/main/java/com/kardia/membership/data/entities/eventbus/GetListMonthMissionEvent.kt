package com.kardia.membership.data.entities.eventbus

import com.kardia.membership.data.entities.Quest

data class GetListMonthMissionEvent(val list: ArrayList<Quest>)