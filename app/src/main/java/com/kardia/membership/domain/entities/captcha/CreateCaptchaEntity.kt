package com.kardia.membership.domain.entities.captcha

import com.kardia.membership.data.entities.Captcha
import com.kardia.membership.domain.entities.BaseEntities

data class CreateCaptchaEntity(val data: Captcha?) :
    BaseEntities() {
    companion object {
        fun empty() = CreateCaptchaEntity(null)
    }
}