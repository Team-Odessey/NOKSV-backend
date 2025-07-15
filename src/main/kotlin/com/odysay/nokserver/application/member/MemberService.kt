package com.odysay.nokserver.application.member

import com.odysay.nokserver.application.member.dto.MemberProfileResponse
import com.odysay.nokserver.application.member.dto.MemberUpdateRequest
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

    fun getMemberIdByNickname(nickname: String): Long {
        return memberRepository.findByNickname(nickname)?.id ?: throw IllegalArgumentException("Member not found with nickname: $nickname")
    }

    fun getMemberProfile(memberId: Long): MemberProfileResponse {
        val member = memberRepository.findByIdOrNull(memberId) ?: throw IllegalArgumentException("Member not found")
        val guildName = member.guildId?.let { guildRepository.findByIdOrNull(it)?.name }
        val stats = statRepository.findAllByMemberId(memberId)
        return MemberProfileResponse.from(member, guildName, stats)
    }

    @Transactional
    fun updateMemberProfile(memberId: Long, request: MemberUpdateRequest): MemberProfileResponse {
        val member = memberRepository.findByIdOrNull(memberId) ?: throw IllegalArgumentException("Member not found")
        request.nickname?.let { member.nickname = it }
        val updatedMember = memberRepository.save(member)

        val guildName = updatedMember.guildId?.let { guildRepository.findByIdOrNull(it)?.name }
        val stats = statRepository.findAllByMemberId(memberId)
        return MemberProfileResponse.from(updatedMember, guildName, stats)
    }
}