package net.sardor.manitoring_ai_auksion.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BidResponse {
    private Long id;
    private BigDecimal amount;
    private Long userId;
    private Long auctionId;
    private LocalDateTime timestamp;
}
