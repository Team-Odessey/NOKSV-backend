package com.odysay.nokserver.domain.moneys.entity

import com.odysay.nokserver.domain.member.Member
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "moneys")
class MoneyEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    val amount: Long = 0,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_member_id")
    val member: Member,
) {
}