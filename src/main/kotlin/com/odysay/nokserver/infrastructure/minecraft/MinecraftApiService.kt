package com.odysay.nokserver.infrastructure.minecraft

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.util.UUID

@Service
class MinecraftApiService(
    private val webClientBuilder: WebClient.Builder
) {

    fun getUuidByUsername(username: String): Mono<UUID> {
        return webClientBuilder.build()
            .get()
            .uri("https://api.mojang.com/users/profiles/minecraft/{username}", username)
            .retrieve()
            .bodyToMono(MojangProfileResponse::class.java)
            .map { 
                val uuidString = it.id
                val formattedUuid = "${uuidString.substring(0, 8)}-${uuidString.substring(8, 12)}-${uuidString.substring(12, 16)}-${uuidString.substring(16, 20)}-${uuidString.substring(20, 32)}"
                UUID.fromString(formattedUuid)
            }
            .doOnError { error ->
                println("Error fetching Minecraft UUID for $username: ${error.message}")
            }
    }
}

data class MojangProfileResponse(
    val id: String,
    val name: String
)
