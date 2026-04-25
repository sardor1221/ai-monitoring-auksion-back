package net.sardor.manitoring_ai_auksion.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.sardor.manitoring_ai_auksion.service.BidService;
import net.sardor.manitoring_ai_auksion.web.dto.BidRequest;
import net.sardor.manitoring_ai_auksion.web.dto.BidResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bids")
@RequiredArgsConstructor
public class BidController {

    private final BidService bidService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'BIDDER')")
    public ResponseEntity<BidResponse> placeBid(@Valid @RequestBody BidRequest request) {
        return ResponseEntity.ok(bidService.placeBid(request));
    }

    @GetMapping("/auction/{auctionId}")
    public ResponseEntity<List<BidResponse>> getBidsByAuction(@PathVariable Long auctionId) {
        return ResponseEntity.ok(bidService.getBidsByAuction(auctionId));
    }
}
