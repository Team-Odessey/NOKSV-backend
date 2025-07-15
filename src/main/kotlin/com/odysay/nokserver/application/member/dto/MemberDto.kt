package com.odysay.nokserver.application.member.dto

import com.odysay.nokserver.domain.member.Member
import com.odysay.nokserver.domain.stats.entity.StatEntity
import com.odysay.nokserver.domain.stats.enumeration.StatCategoryType
import java.time.LocalDateTime

data class MemberProfileResponse(
    val id: Long,
    val username: String,
    val nickname: String,
    val guildName: String?,
    val totalPlayTime: Int,
    val killCount: Int,
    val joinDate: LocalDateTime?,
    val stats: Map<StatCategoryType, StatResponse>
) {
    companion object {
        fun from(member: Member, guildName: String?, stats: List<StatEntity>): MemberProfileResponse {
            val statMap = stats.associate { it.category to StatResponse.from(it) }
            return MemberProfileResponse(
                id = member.id!!,
                username = member.username,
                nickname = member.nickname,
                guildName = guildName,
                totalPlayTime = member.totalPlayTime,
                killCount = member.killCount,
                joinDate = member.joinDate,
                stats = statMap
            )
        }
    }
}

data class MemberUpdateRequest(
    val nickname: String?
)

data class StatResponse(
    val category: StatCategoryType,
    val level: Int,
    val exp: Long
) {
    companion object {
        fun from(statEntity: StatEntity): StatResponse {
            return StatResponse(
                category = statEntity.category,
                level = statEntity.level,
                exp = statEntity.exp
            )
        }
    }
}