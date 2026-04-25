package net.sardor.manitoring_ai_auksion.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sardor.manitoring_ai_auksion.model.enums.AuctionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuctionResponse {
    private Long id;
    private String title;
    private String description;
    private BigDecimal startPrice;
    private BigDecimal currentPrice;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private AuctionStatus status;
    private Long sellerId;
    private Long winnerId;
}
