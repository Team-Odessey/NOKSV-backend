package com.odysay.nokserver.presentation.guilds

import com.odysay.nokserver.application.guilds.GuildService
import com.odysay.nokserver.application.guilds.dto.GuildResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/guilds")
class GuildController(
    private val guildService: GuildService
) {

    @GetMapping
    fun getAllGuilds(): ResponseEntity<List<GuildResponse>> {
        return ResponseEntity.ok(guildService.getAllGuilds())
    }

    @GetMapping("/search")
    fun searchGuildsByName(@RequestParam name: String): ResponseEntity<GuildResponse> {
        return ResponseEntity.ok(guildService.getGuildByName(name))
    }
}