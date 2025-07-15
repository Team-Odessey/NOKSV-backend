package com.odysay.nokserver.domain.member

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<Member, Long> {
    fun findByName(name: String): Member?
    fun findByNickname(nickname: String): Member?
}
