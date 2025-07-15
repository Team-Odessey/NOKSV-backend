package com.odysay.nokserver.domain.guilds.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "guilds")
class GuildEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var image: String? = null,
    val name: String,
    var description: String? = null,
    val level: Int = 0,
) {

}