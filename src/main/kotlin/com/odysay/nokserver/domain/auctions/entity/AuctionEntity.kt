package com.odysay.nokserver.domain.auctions.entity

import com.odysay.nokserver.domain.auctions.enumeration.AuctionStatusType
import com.odysay.nokserver.domain.common.entity.BaseTimeEntity
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "auctions")
class AuctionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    val name: String,
    val endTime: LocalDateTime,
    @Enumerated(value = EnumType.STRING)
    val status: AuctionStatusType,
): BaseTimeEntity() {
}