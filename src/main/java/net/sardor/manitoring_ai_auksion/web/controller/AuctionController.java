package net.sardor.manitoring_ai_auksion.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.sardor.manitoring_ai_auksion.service.AuctionService;
import net.sardor.manitoring_ai_auksion.web.dto.AuctionRequest;
import net.sardor.manitoring_ai_auksion.web.dto.AuctionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auctions")
@RequiredArgsConstructor
public class AuctionController {

    private final AuctionService auctionService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public ResponseEntity<AuctionResponse> createAuction(@Valid @RequestBody AuctionRequest request) {
        return ResponseEntity.ok(auctionService.createAuction(request));
    }

    @GetMapping
    public ResponseEntity<List<AuctionResponse>> getAllAuctions() {
        return ResponseEntity.ok(auctionService.getAllAuctions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuctionResponse> getAuctionById(@PathVariable Long id) {
        return ResponseEntity.ok(auctionService.getAuctionById(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAuction(@PathVariable Long id) {
        auctionService.deleteAuction(id);
        return ResponseEntity.noContent().build();
    }
}
