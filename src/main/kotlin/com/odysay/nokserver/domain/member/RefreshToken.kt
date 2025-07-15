package com.odysay.nokserver.domain.member

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "refresh_tokens")
class RefreshToken(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    var token: String,

    @OneToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    val member: Member,

    @Column(nullable = false)
    var expiryDate: Instant
)
