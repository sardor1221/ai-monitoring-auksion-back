package net.sardor.manitoring_ai_auksion.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sardor.manitoring_ai_auksion.model.entity.Auction;
import net.sardor.manitoring_ai_auksion.model.entity.Bid;
import net.sardor.manitoring_ai_auksion.model.enums.AuctionStatus;
import net.sardor.manitoring_ai_auksion.repository.AuctionRepository;
import net.sardor.manitoring_ai_auksion.repository.BidRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuctionScheduler {

    private final AuctionRepository auctionRepository;
    private final BidRepository bidRepository;

    @Scheduled(fixedRate = 60000) // Every minute
    @Transactional
    public void updateAuctionStatuses() {
        LocalDateTime now = LocalDateTime.now();

        // 1. CREATED -> ACTIVE
        List<Auction> toActivate = auctionRepository.findByStatus(AuctionStatus.CREATED);
        for (Auction auction : toActivate) {
            if (now.isAfter(auction.getStartTime())) {
                auction.setStatus(AuctionStatus.ACTIVE);
                auctionRepository.save(auction);
                log.info("Auction {} status updated to ACTIVE", auction.getId());
            }
        }

        // 2. ACTIVE -> FINISHED
        List<Auction> toFinish = auctionRepository.findByStatus(AuctionStatus.ACTIVE);
        for (Auction auction : toFinish) {
            if (now.isAfter(auction.getEndTime())) {
                auction.setStatus(AuctionStatus.FINISHED);
                
                // Determine winner
                List<Bid> bids = bidRepository.findByAuctionIdOrderByTimestampDesc(auction.getId());
                if (!bids.isEmpty()) {
                    auction.setWinner(bids.get(0).getUser());
                    log.info("Auction {} finished. Winner is user {}", auction.getId(), auction.getWinner().getId());
                } else {
                    log.info("Auction {} finished with no bids", auction.getId());
                }
                
                auctionRepository.save(auction);
            }
        }
    }
}
