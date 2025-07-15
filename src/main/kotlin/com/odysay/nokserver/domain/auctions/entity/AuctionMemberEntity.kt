package com.odysay.nokserver.domain.auctions.entity

import com.odysay.nokserver.domain.common.entity.BaseTimeEntity
import com.odysay.nokserver.domain.members.entity.MemberEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.Fetch

@Entity
@Table(name = "auction_members")
class AuctionMemberEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_auction_id")
    val auction: AuctionEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_member_id")
    val member: MemberEntity,

    val price: Long,
): BaseTimeEntity() {
}