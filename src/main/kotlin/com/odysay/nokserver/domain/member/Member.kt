package com.odysay.nokserver.domain.member

import com.odysay.nokserver.domain.common.entity.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "members")
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(length = 100)
    var username: String,
    @Column(length = 255)
    var password: String,
    @Column(unique = true, nullable = false)
    val minecraftId: UUID,
    @Column(name = "fk_guild_id")
    var guildId: Long? = null,
    var totalPlayTime: Int = 0,
    @Column(nullable = false, length = 100, unique = true)
    var nickname: String,
    var killCount: Int = 0,
    var joinDate: LocalDateTime = LocalDateTime.now(),
): BaseTimeEntity() {
}