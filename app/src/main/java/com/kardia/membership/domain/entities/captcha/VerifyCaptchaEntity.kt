package com.kardia.membership.domain.entities.captcha

import com.kardia.membership.domain.entities.BaseEntities

data class VerifyCaptchaEntity(val data: String?) :
    BaseEntities() {
    companion object {
        fun empty() = VerifyCaptchaEntity(null)
    }
}