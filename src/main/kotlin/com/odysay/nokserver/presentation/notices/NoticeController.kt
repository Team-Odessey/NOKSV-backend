package com.odysay.nokserver.presentation.notices

import com.odysay.nokserver.application.notices.NoticeService
import com.odysay.nokserver.application.notices.dto.NoticeCreateRequest
import com.odysay.nokserver.application.notices.dto.NoticeResponse
import com.odysay.nokserver.application.notices.dto.NoticeUpdateRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/v1/notices")
class NoticeController(
    private val noticeService: NoticeService
) {

    @GetMapping
    fun getAllNotices(): ResponseEntity<List<NoticeResponse>> {
        return ResponseEntity.ok(noticeService.getAllNotices())
    }

    @GetMapping("/{id}")
    fun getNoticeById(@PathVariable id: UUID): ResponseEntity<NoticeResponse> {
        return ResponseEntity.ok(noticeService.getNoticeById(id))
    }

    @PostMapping
    fun createNotice(@RequestBody request: NoticeCreateRequest): ResponseEntity<NoticeResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(noticeService.createNotice(request))
    }

    @PutMapping("/{id}")
    fun updateNotice(@PathVariable id: UUID, @RequestBody request: NoticeUpdateRequest): ResponseEntity<NoticeResponse> {
        return ResponseEntity.ok(noticeService.updateNotice(id, request))
    }

    @DeleteMapping("/{id}")
    fun deleteNotice(@PathVariable id: UUID): ResponseEntity<Void> {
        noticeService.deleteNotice(id)
        return ResponseEntity.noContent().build()
    }
}