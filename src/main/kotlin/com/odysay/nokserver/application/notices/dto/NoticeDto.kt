package com.odysay.nokserver.application.notices.dto

import com.odysay.nokserver.domain.notices.entity.NoticeEntity
import com.odysay.nokserver.domain.notices.enumeration.NoticeType
import java.time.LocalDateTime
import java.util.UUID

data class NoticeCreateRequest(
    val title: String,
    val content: String,
    val type: NoticeType
)

data class NoticeUpdateRequest(
    val title: String?,
    val content: String?,
    val type: NoticeType?
)

data class NoticeResponse(
    val id: UUID,
    val title: String,
    val content: String,
    val type: NoticeType,
    val createdAt: LocalDateTime?,
    val modifiedAt: LocalDateTime?
) {
    companion object {
        fun from(noticeEntity: NoticeEntity): NoticeResponse {
            return NoticeResponse(
                id = noticeEntity.id!!,
                title = noticeEntity.title,
                content = noticeEntity.content,
                type = noticeEntity.type,
                createdAt = noticeEntity.createdAt,
                modifiedAt = noticeEntity.modifiedAt
            )
        }
    }
}