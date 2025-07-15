package com.odysay.nokserver.domain.notices.entity

import com.odysay.nokserver.domain.common.entity.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "notices")
class NoticeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,
    val title: String,
    @Column(columnDefinition = "TEXT")
    val content: String,
): BaseTimeEntity() {
}