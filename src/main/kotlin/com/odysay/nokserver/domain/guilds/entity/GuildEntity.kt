package com.odysay.nokserver.domain.guilds.entity

import com.odysay.nokserver.domain.common.entity.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "guilds")
class GuildEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    val name: String,
    var description: String? = null,
    var level: Int = 0,
    var memberCount: Int = 0,
    var leaderMemberId: Long? = null,
    var viceLeaderMemberId: Long? = null,
    var creationDate: LocalDateTime = LocalDateTime.now(),
    var inquiryChannel: String? = null,
    @Column(columnDefinition = "TEXT")
    var introduction: String? = null,
): BaseTimeEntity() {

}