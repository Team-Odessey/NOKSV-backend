package com.odysay.nokserver.domain.members.entity

import com.odysay.nokserver.domain.common.entity.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "members")
class MemberEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var name: String,
    var password: String,
    @Column(unique = true, nullable = false)
    val minecraftId: UUID,
    var totalPlayTime: Long = 0L
): BaseTimeEntity() {
}