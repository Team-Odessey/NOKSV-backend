package com.odysay.nokserver.presentation.member

import com.odysay.nokserver.application.member.MemberService
import com.odysay.nokserver.application.member.dto.MemberProfileResponse
import com.odysay.nokserver.application.member.dto.MemberUpdateRequest
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
        // userDetails.username은 Member 엔티티의 name 필드와 매핑된다고 가정
        // 실제 Member ID를 가져오는 로직은 인증 방식에 따라 달라질 수 있음
        val memberId = memberService.getMemberIdByNickname(userDetails.username)
        return ResponseEntity.ok(memberService.getMemberProfile(memberId))
    }

    @PutMapping("/me/profile")
    fun updateMyProfile(@AuthenticationPrincipal userDetails: UserDetails, @RequestBody request: MemberUpdateRequest): ResponseEntity<MemberProfileResponse> {
        val memberId = memberService.getMemberIdByNickname(userDetails.username)
        return ResponseEntity.ok(memberService.updateMemberProfile(memberId, request))
    }
}