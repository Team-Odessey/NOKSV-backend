package com.odysay.nokserver.domain.stats

import com.odysay.nokserver.domain.stats.entity.StatEntity
import com.odysay.nokserver.domain.stats.enumeration.StatCategoryType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StatRepository : JpaRepository<StatEntity, Long> {
    fun findByMemberIdAndCategory(memberId: Long, category: StatCategoryType): StatEntity?
    fun findAllByMemberId(memberId: Long): List<StatEntity>
    fun findTopByCategoryOrderByLevelDesc(category: StatCategoryType, pageable: org.springframework.data.domain.Pageable): List<StatEntity>
}