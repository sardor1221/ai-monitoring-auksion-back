package net.sardor.manitoring_ai_auksion.service;

import lombok.RequiredArgsConstructor;
import net.sardor.manitoring_ai_auksion.model.entity.Auction;
import net.sardor.manitoring_ai_auksion.model.entity.Bid;
import net.sardor.manitoring_ai_auksion.model.entity.User;
import net.sardor.manitoring_ai_auksion.model.enums.AuctionStatus;
import net.sardor.manitoring_ai_auksion.repository.AuctionRepository;
import net.sardor.manitoring_ai_auksion.repository.BidRepository;
import net.sardor.manitoring_ai_auksion.repository.UserRepository;
import net.sardor.manitoring_ai_auksion.web.dto.BidRequest;
import net.sardor.manitoring_ai_auksion.web.dto.BidResponse;
import net.sardor.manitoring_ai_auksion.web.mapper.BidMapper;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BidService {

    private final BidRepository bidRepository;
    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;
    private final BidMapper bidMapper;
    private final SimpMessagingTemplate messagingTemplate;

    @Transactional
    public BidResponse placeBid(BidRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Auction auction = auctionRepository.findById(request.getAuctionId())
                .orElseThrow(() -> new RuntimeException("Auction not found"));

        // Logic checks
        if (auction.getStatus() != AuctionStatus.ACTIVE) {
            throw new RuntimeException("Auction is not active");
        }

        if (LocalDateTime.now().isAfter(auction.getEndTime())) {
            throw new RuntimeException("Auction has already ended");
        }

        if (request.getAmount().compareTo(auction.getCurrentPrice()) <= 0) {
            throw new RuntimeException("Bid amount must be higher than current price");
        }

        Bid bid = Bid.builder()
                .amount(request.getAmount())
                .user(user)
                .auction(auction)
                .timestamp(LocalDateTime.now())
                .build();

        auction.setCurrentPrice(request.getAmount());
        
        // Anti-sniping logic: extend by 5 minutes if bid is placed in the last 5 minutes
        if (LocalDateTime.now().plusMinutes(5).isAfter(auction.getEndTime())) {
            auction.setEndTime(auction.getEndTime().plusMinutes(5));
        }
        
        auctionRepository.save(auction);

        Bid savedBid = bidRepository.save(bid);
        BidResponse response = bidMapper.toResponse(savedBid);
        
        messagingTemplate.convertAndSend("/topic/auction/" + auction.getId(), response);
        
        return response;
    }

    public List<BidResponse> getBidsByAuction(Long auctionId) {
        return bidRepository.findByAuctionIdOrderByTimestampDesc(auctionId).stream()
                .map(bidMapper::toResponse)
                .collect(Collectors.toList());
    }
}
