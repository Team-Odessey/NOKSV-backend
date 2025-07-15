package com.odysay.nokserver.presentation.member

import com.odysay.nokserver.application.member.MemberService
import com.odysay.nokserver.application.member.dto.MemberProfileResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/members")
class MemberController(
    private val memberService: MemberService
) {

    @GetMapping("/me/profile")
    fun getMyProfile(@AuthenticationPrincipal userDetails: UserDetails): ResponseEntity<MemberProfileResponse> {
        val memberId = memberService.getMemberIdByUsername(userDetails.username)
        return ResponseEntity.ok(memberService.getMemberProfile(memberId))
    }
}