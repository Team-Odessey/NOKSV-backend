package com.odysay.nokserver.domain.guilds

import com.odysay.nokserver.domain.guilds.entity.GuildEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GuildRepository : JpaRepository<GuildEntity, Long> {
    fun findByName(name: String): GuildEntity?
}