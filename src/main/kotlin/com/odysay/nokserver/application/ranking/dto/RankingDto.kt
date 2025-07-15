package com.odysay.nokserver.application.ranking.dto

import com.odysay.nokserver.domain.stats.enumeration.StatCategoryType
import java.time.LocalDateTime

data class RankingResponse(
    val rank: Int,
    val username: String,
    val nickname: String,
    val level: Int,
    val guildName: String?,
    val joinDate: LocalDateTime,
    val totalPlayTime: Int
)