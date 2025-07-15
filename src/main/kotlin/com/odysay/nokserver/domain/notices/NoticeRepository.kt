package com.odysay.nokserver.domain.notices

import com.odysay.nokserver.domain.notices.entity.NoticeEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface NoticeRepository : JpaRepository<NoticeEntity, UUID> {
}