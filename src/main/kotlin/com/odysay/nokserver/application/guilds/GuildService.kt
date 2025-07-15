package com.odysay.nokserver.application.guilds

import com.odysay.nokserver.application.guilds.dto.GuildResponse
import com.odysay.nokserver.application.guilds.dto.GuildMemberResponse
import com.odysay.nokserver.domain.guilds.GuildMemberRepository
import com.odysay.nokserver.domain.guilds.GuildRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class GuildService(
    private val guildRepository: GuildRepository,
    private val guildMemberRepository: GuildMemberRepository
) {

    fun getAllGuilds(): List<GuildResponse> {
        return guildRepository.findAll().map { GuildResponse.from(it) }
    }

    fun getGuildByName(name: String): GuildResponse {
        val guild = guildRepository.findByName(name) ?: throw IllegalArgumentException("Guild not found with name: $name")
        val members = guildMemberRepository.findAllByGuildId(guild.id!!).map { GuildMemberResponse.from(it) }
        return GuildResponse.from(guild, members)
    }
}