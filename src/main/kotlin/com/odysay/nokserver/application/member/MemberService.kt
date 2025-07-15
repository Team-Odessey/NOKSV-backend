package com.odysay.nokserver.application.member

import com.odysay.nokserver.application.member.dto.MemberProfileResponse
import com.odysay.nokserver.domain.guilds.GuildRepository
import com.odysay.nokserver.domain.member.MemberRepository
import com.odysay.nokserver.domain.stats.StatRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MemberService(
    private val memberRepository: MemberRepository,
    private val guildRepository: GuildRepository,
    private val statRepository: StatRepository
) {

    fun getMemberIdByUsername(username: String): Long {
        return memberRepository.findByUsername(username)?.id ?: throw IllegalArgumentException("Member not found with username: $username")
    }

    fun getMemberProfile(memberId: Long): MemberProfileResponse {
        val member = memberRepository.findByIdOrNull(memberId) ?: throw IllegalArgumentException("Member not found")
        val guildName = member.guildId?.let { guildRepository.findByIdOrNull(it)?.name }
        val stats = statRepository.findAllByMemberId(memberId)
        return MemberProfileResponse.from(member, guildName, stats)
    }
}