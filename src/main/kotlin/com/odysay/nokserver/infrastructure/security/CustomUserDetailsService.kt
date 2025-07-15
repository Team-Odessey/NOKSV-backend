package com.odysay.nokserver.infrastructure.security

import com.odysay.nokserver.domain.member.MemberRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val memberRepository: MemberRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val member = memberRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found with username: $username")
        return User(member.username, member.password, emptyList())
    }
}
