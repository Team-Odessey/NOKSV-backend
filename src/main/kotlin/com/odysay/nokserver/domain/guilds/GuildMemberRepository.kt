package com.odysay.nokserver.domain.guilds

import com.odysay.nokserver.domain.guilds.entity.GuildMemberEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GuildMemberRepository : JpaRepository<GuildMemberEntity, Long> {
    fun findByGuildIdAndMemberId(guildId: Long, memberId: Long): GuildMemberEntity?
    fun findAllByGuildId(guildId: Long): List<GuildMemberEntity>
}