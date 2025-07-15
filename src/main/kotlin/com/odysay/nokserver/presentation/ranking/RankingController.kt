package com.odysay.nokserver.presentation.ranking

import com.odysay.nokserver.application.ranking.RankingService
import com.odysay.nokserver.application.ranking.dto.RankingResponse
import com.odysay.nokserver.domain.stats.enumeration.StatCategoryType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/rankings")
class RankingController(
    private val rankingService: RankingService
) {

    @GetMapping("/{category}")
    fun getRankingByCategory(@PathVariable category: StatCategoryType, @RequestParam(defaultValue = "100") limit: Int): ResponseEntity<List<RankingResponse>> {
        return ResponseEntity.ok(rankingService.getRankingByCategory(category, limit))
    }
}