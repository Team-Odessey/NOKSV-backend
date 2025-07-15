package com.odysay.nokserver.domain.guilds.entity

import com.odysay.nokserver.domain.guilds.enumeration.GuildMemberRoleType
import com.odysay.nokserver.domain.member.Member
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "guild_members")
class GuildMemberEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_guild_id")
    val guild: GuildEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_member_id")
    val member: Member,

    var role: GuildMemberRoleType = GuildMemberRoleType.MEMBER,
) {
}