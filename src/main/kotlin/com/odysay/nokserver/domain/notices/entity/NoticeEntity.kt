package com.odysay.nokserver.domain.notices.entity

import com.odysay.nokserver.domain.common.entity.BaseTimeEntity
import com.odysay.nokserver.domain.notices.enumeration.NoticeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
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
    var title: String,
    @Column(columnDefinition = "TEXT")
    var content: String,
    @Enumerated(EnumType.STRING)
    var type: NoticeType,
): BaseTimeEntity() {
}