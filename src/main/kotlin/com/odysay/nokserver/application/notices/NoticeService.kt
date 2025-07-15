package com.odysay.nokserver.application.notices

import com.odysay.nokserver.application.notices.dto.NoticeCreateRequest
import com.odysay.nokserver.application.notices.dto.NoticeResponse
import com.odysay.nokserver.application.notices.dto.NoticeUpdateRequest
import com.odysay.nokserver.domain.notices.NoticeRepository
import com.odysay.nokserver.domain.notices.entity.NoticeEntity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional(readOnly = true)
class NoticeService(
    private val noticeRepository: NoticeRepository
) {

    fun getAllNotices(): List<NoticeResponse> {
        return noticeRepository.findAll().map { NoticeResponse.from(it) }
    }

    fun getNoticeById(id: UUID): NoticeResponse {
        val notice = noticeRepository.findByIdOrNull(id) ?: throw IllegalArgumentException("Notice not found")
        return NoticeResponse.from(notice)
    }

    @Transactional
    fun createNotice(request: NoticeCreateRequest): NoticeResponse {
        val notice = NoticeEntity(
            title = request.title,
            content = request.content,
            type = request.type
        )
        return NoticeResponse.from(noticeRepository.save(notice))
    }

    @Transactional
    fun updateNotice(id: UUID, request: NoticeUpdateRequest): NoticeResponse {
        val notice = noticeRepository.findByIdOrNull(id) ?: throw IllegalArgumentException("Notice not found")
        request.title?.let { notice.title = it }
        request.content?.let { notice.content = it }
        request.type?.let { notice.type = it }
        return NoticeResponse.from(noticeRepository.save(notice))
    }

    @Transactional
    fun deleteNotice(id: UUID) {
        if (!noticeRepository.existsById(id)) {
            throw IllegalArgumentException("Notice not found")
        }
        noticeRepository.deleteById(id)
    }
}