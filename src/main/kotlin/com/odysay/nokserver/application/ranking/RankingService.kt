package com.odysay.nokserver.application.ranking

import com.odysay.nokserver.application.ranking.dto.RankingResponse
import com.odysay.nokserver.domain.guilds.GuildRepository
import com.odysay.nokserver.domain.member.MemberRepository
import com.odysay.nokserver.domain.stats.StatRepository
import com.odysay.nokserver.domain.stats.enumeration.StatCategoryType
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class RankingService(
    private val statRepository: StatRepository,
    private val memberRepository: MemberRepository,
    private val guildRepository: GuildRepository
) {

    fun getRankingByCategory(category: StatCategoryType, limit: Int = 100): List<RankingResponse> {
        val pageable = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "level"))
        val stats = statRepository.findTopByCategoryOrderByLevelDesc(category, pageable)
        return stats.mapIndexed { index, stat ->
            val member = stat.member
            val guildName = member.guildId?.let { guildRepository.findById(it).orElse(null)?.name }
            RankingResponse(
                rank = index + 1,
                username = member.username,
                nickname = member.nickname,
                level = stat.level,
                guildName = guildName,
                joinDate = member.joinDate,
                totalPlayTime = member.totalPlayTime
            )
        }
    }
}