package com.odysay.nokserver.application.guilds.dto

import com.odysay.nokserver.domain.guilds.entity.GuildEntity
import com.odysay.nokserver.domain.guilds.entity.GuildMemberEntity
import com.odysay.nokserver.domain.guilds.enumeration.GuildMemberRoleType
import java.time.LocalDateTime

data class GuildMemberResponse(
    val id: Long,
    val memberId: Long,
    val memberNickname: String,
    val role: GuildMemberRoleType
) {
    companion object {
        fun from(guildMemberEntity: GuildMemberEntity): GuildMemberResponse {
            return GuildMemberResponse(
                id = guildMemberEntity.id!!,
                memberId = guildMemberEntity.member.id!!,
                memberNickname = guildMemberEntity.member.nickname,
                role = guildMemberEntity.role
            )
        }
    }
}

data class GuildResponse(
    val id: Long,
    val name: String,
    val level: Int,
    val memberCount: Int,
    val leaderMemberId: Long?,
    val viceLeaderMemberId: Long?,
    val creationDate: LocalDateTime,
    val inquiryChannel: String?,
    val introduction: String?,
    val description: String?,
    val members: List<GuildMemberResponse>? = null
) {
    companion object {
        fun from(guildEntity: GuildEntity, members: List<GuildMemberResponse>? = null): GuildResponse {
            return GuildResponse(
                id = guildEntity.id!!,
                name = guildEntity.name,
                level = guildEntity.level,
                memberCount = guildEntity.memberCount,
                leaderMemberId = guildEntity.leaderMemberId,
                viceLeaderMemberId = guildEntity.viceLeaderMemberId,
                creationDate = guildEntity.creationDate,
                inquiryChannel = guildEntity.inquiryChannel,
                introduction = guildEntity.introduction,
                description = guildEntity.description,
                members = members
            )
        }
    }
}